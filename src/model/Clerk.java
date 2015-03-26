package model;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.table.DefaultTableModel;

import exception.ItemException;
import exception.QuantityException;
import exception.ReceiptIdException;
import exception.ReturnException;

public class Clerk extends Transaction {
	
	private Connection con;
	private ArrayList<Item> shoppingCart;
	
	public Clerk() {
		
		shoppingCart = new ArrayList<Item>();
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
		try {
			con = DriverManager.getConnection(connectURL,"ora_w5p8","a99160079");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addToCart(Item i) {
		shoppingCart.add(i);
		return 0;
	}

	public int RemoveFromCart(Item i) {
		shoppingCart.remove(i);
		return 0;
	}

	//Accept upc and quantity from the UI, search the database for it, check the Quantity vs. Stock, if all is good return the item as an Item object
	public int searchItem(String upc, String quantity) throws SQLException, ItemException, QuantityException {
		
		PreparedStatement ps;
		con.setAutoCommit(false);
		
		ps = con.prepareStatement("SELECT * FROM item WHERE upc = ? ");
		ps.setString(1, upc);

		ResultSet rs = ps.executeQuery();

		
		if (rs.next()) {

			Item item = new Item(upc);

			item.setTitle(rs.getString(2));
			item.setType(rs.getString(3));
			item.setCategory(rs.getString(4));
			item.setCompany(rs.getString(5));
			item.setYear(rs.getString(6));
			item.setPrice(rs.getDouble(7));
			item.setStock(rs.getInt(8));

			int quant;
			
			try{
				quant = Integer.parseInt(quantity);
				item.setQuantity(quant);
			}
			catch (NumberFormatException e){
				throw new QuantityException("Please enter a valid quantity.");}
			
			if (quant <=0)
				throw new QuantityException("Please enter a valid quantity.");
		
			if (quant > item.getStock())
				throw new QuantityException("There is not enough of this item in stock.");
			
			if (getCart().contains(item)){

				for(int i =0; i < getCart().size(); i++){
					Item temp = getCart().get(i);

					if (temp.getUPC().equals(item.getUPC())){
						int stock = temp.getStock();

						if (stock == 0){
							throw new QuantityException("This quantity of item: " + item.getTitle() + " cannot be updated!");
						}
						else if (quant <= stock){
							System.out.println(quant + " of " + item.getTitle() + " has been updated to your shopping cart!");
							temp.setQuantity(temp.getQuantity() + quant);
							stock -= quant;
							temp.setStock(stock);
							getCart().remove(i);
							getCart().add(temp);
							return quant;
						}
						else{
							System.out.println("Only " + stock + " of " + item.getTitle() + " can be updated to your shopping cart!");
							temp.setQuantity(temp.getQuantity() + stock);
							temp.setStock(0);
							getCart().remove(i);
							getCart().add(temp);
							return stock;
						}		
					}
				}
			}

			else{

				int stock = item.getStock();

				if (stock == 0){
					throw new QuantityException("This item: " + item.getTitle() + " is not in stock!");
				}
				else if (quant <= stock){
					System.out.println(quant + " of " + item.getTitle() + " has been added to your shopping cart!");
					stock -= quant;
					item.setStock(stock);
					getCart().add(item);
					return quant;
				}
				else{
					System.out.println("Only " + stock + " of " + item.getTitle() + " can be added to your shopping cart!");
					item.setQuantity(stock);
					item.setStock(0);
					getCart().add(item);
					return stock;
				}		
			}
		}

		else{
			throw new ItemException ("This upc does not exist in the store! Please enter a new upc.");}

		con.commit();
		con.setAutoCommit(true);
		ps.close();
		return 0;
	}
	
	public DefaultTableModel printShoppingCart(){
		// print the shoppingcart

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {"UPC", "TITLE", "STOCK", "QUANTITY", "UNIT PRICE($)"});

		for (Item i : getCart())
		{
			model.addRow(new String[] {i.getUPC(), i.getTitle(),
					Integer.toString(i.getStock()), Integer.toString(i.getQuantity()),Double.toString(i.getPrice())});
		}
		return model;
	}

	// take in Arraylist of Items, iterate through the list and call purchase item on each one. 
	public int purchaseShoppingCart(String cash) throws ParseException, SQLException, ItemException{
		
		double cashPay = 0.00;
		double total = totalBill(getCart());
				
		try{
			cashPay = Double.parseDouble(cash);
		}
		catch(NumberFormatException e){
			throw new ItemException("Invalid cash amount entered.");
		}
		
		if (cashPay < total){
			throw new ItemException ("You are still owing: $" + (total-cashPay));
		}
		
		PreparedStatement ps;
		con.setAutoCommit(false);

		java.sql.Date date = this.getCurrentDate();
		this.insertPurchase(date, "STOR", null, null, null, null);

		ps = con.prepareStatement("SELECT receiptid FROM purchase WHERE cid=? ORDER BY receiptid desc");
		ps.setString(1,"STOR");
		ResultSet rs = ps.executeQuery();
		rs.next();

		int receiptid = rs.getInt(1);
		this.updatePurchaseItem(receiptid);
		getCart().removeAll(getCart());

		con.commit();
		con.setAutoCommit(true);
		ps.close();

		return receiptid;
	}

	public int purchaseShoppingCart(String card, String expiryDate) throws ParseException, SQLException{
		PreparedStatement ps;
		con.setAutoCommit(false);

		java.sql.Date date = this.getCurrentDate();
		this.insertPurchase(date, "STOR", card, expiryDate, null, null);

		ps = con.prepareStatement("SELECT receiptid FROM purchase WHERE cid=? ORDER BY receiptid desc");
		ps.setString(1,"STOR");
		ResultSet rs = ps.executeQuery();
		rs.next();

		int receiptid = rs.getInt(1);
		this.updatePurchaseItem(receiptid);
		getCart().removeAll(getCart());

		con.commit();
		con.setAutoCommit(true);
		ps.close();

		return receiptid;
	}

	private void updatePurchaseItem(int receiptid){

		PreparedStatement ps;

		try{
			con.setAutoCommit(false);
			for(int i = 0; i < getCart().size(); i++){
				Item item = getCart().get(i);
				this.insertPurchaseItem(receiptid, item.getUPC(), item.getQuantity());

				ps = con.prepareStatement("UPDATE item SET stock =? WHERE upc=?");
				ps.setInt(1, item.stock);
				ps.setString(2, item.getUPC());

				ps.executeUpdate();
				ps.close();
			}
			getCart().removeAll(getCart());
			con.commit();
			con.setAutoCommit(true);
		}
		catch (SQLException ex){
			//
		}
	}

	// accept receiptid and upc from the UI, search the database for the receipt, verify that the receipt exists and the item is on it, check whether it is within 15 days 
	//     if all is good turn the item into an Item object, then call return item with the Item's values.
	public int receiptidReturn(int receiptid, String upc) throws SQLException, ReceiptIdException, ParseException, ReturnException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("SELECT * FROM purchaseitem WHERE receiptId = ? AND upc = ?");
		ps.setInt(1, receiptid);
		ps.setString(2, upc);
		ResultSet rs = ps.executeQuery();
		if (!rs.next()) {
			throw new ReceiptIdException();
		}
		ps.close();
		ps = con.prepareStatement("SELECT pdate FROM purchase WHERE receiptId = ?");
		ps.setInt(1, receiptid);
		ResultSet rs1 = ps.executeQuery();
		rs1.next();
		Date purchaseDate = rs1.getDate(1);
		GregorianCalendar greg = new GregorianCalendar();  
		greg.setTime(purchaseDate);  
		int purchaseDayOfYear = greg.get(Calendar.DAY_OF_YEAR);  

		Date today = getCurrentDate();
		GregorianCalendar greg1 = new GregorianCalendar();  
		greg1.setTime(today);  
		int returnDayOfYear = greg1.get(Calendar.DAY_OF_YEAR);
		if (returnDayOfYear - purchaseDayOfYear > 15){
			throw new ReturnException();
		}

		returnitem(today, receiptid, upc, 1);
		return 0;
	}

	private int returnitem(Date date, int receiptId, 
			String upc,int quantity){

		try {
			con.setAutoCommit(false);

			//create the return item and put it in the database
			insertReturn(date, receiptId);

			//get item's stock value, increment it by 'quantity' amount 
			//then send through an UPDATE sql command
			PreparedStatement ps = null;
			ps = con.prepareStatement("SELECT stock FROM item WHERE upc = ?");
			ps.setString(1, upc);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int stock = rs.getInt("stock");
			stock = stock + quantity;
			ps.close();
			ps = con.prepareStatement("UPDATE item SET stock = ? WHERE upc = ?");
			ps.setInt(1, stock);
			ps.setString(2, upc);
			ps.executeUpdate();
			ps.close();

			ps = con.prepareStatement("SELECT retId FROM return ORDER BY retId desc");
			ResultSet rs1 = ps.executeQuery();
			rs1.next();
			int retId = rs1.getInt(1);
			//create the purchase item and store it in the database
			insertReturnItem(retId, upc, quantity);
			con.commit();
			con.setAutoCommit(true);
			ps.close();
			return 0;
		} catch (SQLException e) {
			// the database didn't like that return/returnitem..
			e.printStackTrace();
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// the database didn't like resetting the autocommit
				e1.printStackTrace();
			}
			return -1;
		}
	}

	public ArrayList<Item> getCart() {
		return shoppingCart;
	}

	public void clearCart() {
		shoppingCart.removeAll(shoppingCart);
	}

}
