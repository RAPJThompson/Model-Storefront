package model;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import exception.ItemException;
import exception.QuantityException;

public class Customer extends Transaction{

	final int MAX_ORDER = 10;
	private ArrayList<Item> shoppingCart;
	private String cid;

	// customer constructor 
	public Customer (){
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
		try {
			con = DriverManager.getConnection(connectURL,"ora_w5p8","a99160079");
			this.setCid("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// CU1 - customer registration
	public int registerCustomer( String cid,
			String name,
			String pw,
			String address,
			String phone) throws SQLException
			{
		PreparedStatement ps;

		try {
			con.setAutoCommit(false);

			// Check if cid already exists in the customer table
			ps = con.prepareStatement("SELECT cid FROM customer WHERE cid = ?");
			ps.setString(1, cid);

			ResultSet rs = ps.executeQuery();

			// if cid exists, prompt customer to enter a different customer id
			if(rs.next()){
				System.out.println("Please choose a different customer id!");
				return -1;
			}
			else {
				// if cid is unique, insert new customer into table
				this.insertCustomer(cid,name,pw,address,phone);
				System.out.println("You are now registered!");
			}
			con.commit();
			ps.close();

			return 0;
		}
		catch (SQLException ex) {
			try {
				con.rollback();	
				throw new SQLException();
			}
			catch (SQLException ex2) {
				throw new SQLException();
			}
		}
			}

	// Step1 - logIn

	public int logIn(String cid, String pw)throws SQLException{

		PreparedStatement ps;

		try {
			con.setAutoCommit(false);

			// Check if cid and pw tuple exists in the customer table
			ps = con.prepareStatement("SELECT cid FROM customer WHERE cid = ? AND password = ?");
			ps.setString(1, cid);
			ps.setString(2, pw);

			ResultSet rs = ps.executeQuery();

			// if login is successful, initialize shoppingCart 
			if(rs.next()){

				this.setShoppingCart(new ArrayList<Item>());
				this.setCid(cid);

				System.out.println("Welcome back " + cid +"!");
				ps.close();
				return 0;
			}
			// if login is unsuccessful, prompt user to try again or register for a new account
			else 
			{System.out.println("The customer id and password combination is not valid. Please try again.");

			con.commit();
			ps.close();
			return -1;}
		}
		catch (SQLException ex) {
			try {
				con.rollback();	
				throw new SQLException();
			}
			catch (SQLException ex2) {
				throw new SQLException();
			}
		}

	}

	// Step 2 - search items

	public DefaultTableModel searchItem(String category, String title, String name)throws SQLException, ItemException{

		PreparedStatement ps;
		ResultSet rs1;
		DefaultTableModel defaultTableModel = new DefaultTableModel();

		con.setAutoCommit(false);

		if (category.length() != 0 & title.length() !=0 & name.length() != 0){
			ps = con.prepareStatement("SELECT *"
					+ "FROM leadsinger NATURAL JOIN item "
					+ "WHERE category = ? AND title = ? AND name = ?");
			ps.setString(1, category);
			ps.setString(2, title);
			ps.setString(3, name);
		}
		else if(category.length() != 0 & title.length() != 0 & name.length() == 0){
			ps = con.prepareStatement("SELECT * "
					+ "FROM leadsinger NATURAL JOIN item "
					+ "WHERE category = ? AND title = ?");

			ps.setString(1, category);
			ps.setString(2, title);
		}
		else if(category.length() != 0 & name.length() != 0 & title.length() == 0){
			ps = con.prepareStatement("SELECT * "
					+ "FROM leadsinger NATURAL JOIN item "
					+ "WHERE category = ? AND name = ?");

			ps.setString(1, category);
			ps.setString(2, name);
		}
		else if(title.length() != 0 & name.length() != 0 & category.length() == 0){
			ps = con.prepareStatement("SELECT * "
					+ "FROM leadsinger NATURAL JOIN item "
					+ "WHERE title = ? AND name = ?");

			ps.setString(1, title);
			ps.setString(2, name);
		}
		else{
			ps = con.prepareStatement("SELECT * "
					+ "FROM leadsinger NATURAL JOIN item "
					+ "WHERE category = ? OR title = ? OR name = ?");

			ps.setString(1, category);
			ps.setString(2, title);
			ps.setString(3, name);
		}

		rs1 = ps.executeQuery();
		resultSetToTableModel(defaultTableModel, rs1);
		int tableSize = defaultTableModel.getRowCount();

		if (tableSize==0){

			ps = con.prepareStatement("SELECT * "
					+ "FROM leadsinger NATURAL JOIN item "
					+ "WHERE category = ? OR title = ? OR name = ?");

			ps.setString(1, category);
			ps.setString(2, title);
			ps.setString(3, name );
			rs1 = ps.executeQuery();
			resultSetToTableModel(defaultTableModel, rs1);

		}

		tableSize = defaultTableModel.getRowCount();
		if (tableSize == 0){
			throw new ItemException();
		}

		con.commit();
		con.setAutoCommit(true);
		ps.close();
		return defaultTableModel;

	}

	// step 3 - select item and check quantity

	public int selectItem(String upc, String quantity) throws SQLException, ParseException, NumberFormatException, QuantityException, ItemException {

		PreparedStatement ps;

		con.setAutoCommit(false);

		ps = con.prepareStatement("SELECT * FROM leadsinger NATURAL JOIN item WHERE upc = ?");
		ps.setString(1, upc);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {

			Item item = new Item(upc);

			item.setName(rs.getString(2));
			item.setTitle(rs.getString(3));
			item.setType(rs.getString(4));
			item.setCategory(rs.getString(5));
			item.setCompany(rs.getString(6));
			item.setYear(rs.getString(7));
			item.setPrice(rs.getDouble(8));
			item.setStock(rs.getInt(9));

			int quant = Integer.parseInt(quantity);
			item.setQuantity(quant);

			if (quant <=0){
				throw new QuantityException("Please enter a valid quantity!");
			}

			if (getShoppingCart().contains(item)){

				for(int i =0; i < getShoppingCart().size(); i++){
					Item temp = getShoppingCart().get(i);

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
							getShoppingCart().remove(i);
							getShoppingCart().add(temp);
							return quant;
						}
						else{
							System.out.println("Only " + stock + " of " + item.getTitle() + " can be updated to your shopping cart!");
							temp.setQuantity(temp.getQuantity() + stock);
							temp.setStock(0);
							getShoppingCart().remove(i);
							getShoppingCart().add(temp);
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
					getShoppingCart().add(item);
					return quant;
				}
				else{
					System.out.println("Only " + stock + " of " + item.getTitle() + " can be added to your shopping cart!");
					item.setQuantity(stock);
					item.setStock(0);
					getShoppingCart().add(item);
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
		model.setColumnIdentifiers(new String[] {"UPC", "Title", "Lead Singer", "Category", "Quantity", "Unit Price ($)"});

		for (Item i : getShoppingCart())
		{
			model.addRow(new String[] {i.getUPC(), i.getTitle(), i.getName(),
					i.getCategory(), Integer.toString(i.getQuantity()),Double.toString(i.getPrice())});
		}
		return model;
	}


	// step 4 - print bill/shopping cart

	public DefaultTableModel printBill(){
		// print the bill

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {"UPC", "TITLE", "UNIT PRICE($)", "QUANTITY"});

		for (Item i : getShoppingCart())
		{
			model.addRow(new String[] {i.getUPC(), i.getTitle(),Double.toString(i.getPrice()), 
					Integer.toString(i.getQuantity())});
		}
		System.out.println("Your purchase total is = "+ this.totalBill());
		return model;
	}


	// step 5 - confirm purchase (update purchase and update purchase items)

	public int makeOnlinePurchase(String card, String expiryDate) throws SQLException, ParseException{

		PreparedStatement ps;
		con.setAutoCommit(false);

		java.sql.Date date = this.getCurrentDate();
		java.sql.Date expectedDate = this.getExpectedDate();

		this.insertPurchase(date, this.getCid(), card, expiryDate, expectedDate, null);

		ps = con.prepareStatement("SELECT receiptid FROM purchase WHERE cid=? ORDER BY receiptid desc");
		ps.setString(1,getCid());
		ResultSet rs = ps.executeQuery();
		rs.next();

		int receiptid = rs.getInt(1);
		this.updatePurchaseItem(receiptid);
		getShoppingCart().removeAll(getShoppingCart());

		con.commit();
		con.setAutoCommit(true);
		ps.close();

		return receiptid;

	}

	// helper 1 - calculate expected date of delivery based on max_orders
	public java.sql.Date getExpectedDate() throws ParseException, SQLException {

		PreparedStatement ps;
		java.sql.Date today = this.getCurrentDate();

		con.setAutoCommit(false);
		ps = con.prepareStatement("SELECT COUNT(receiptid)  FROM purchase WHERE cid != 'stor' AND pdate = ?");
		ps.setDate(1, today);

		ResultSet rs = ps.executeQuery();
		rs.next();
		int purchaseCount = rs.getInt(1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.

		if (purchaseCount > MAX_ORDER){
			c.add(Calendar.DATE, 7); // Adding 7 days
		} 
		else{
			c.add(Calendar.DATE, 3); // Adding 3 days
		}

		con.commit();
		con.setAutoCommit(true);
		ps.close();

		java.util.Date expectedDate = sdf.parse(sdf.format(c.getTime()));
		return new java.sql.Date(expectedDate.getTime());
	}

	// helper 2 - update purchase item and also quantity field in item

	private void updatePurchaseItem(int receiptid){

		PreparedStatement ps;

		try{
			con.setAutoCommit(false);
			for(int i = 0; i < getShoppingCart().size(); i++){
				Item item = getShoppingCart().get(i);
				this.insertPurchaseItem(receiptid, item.getUPC(), item.getQuantity());

				ps = con.prepareStatement("UPDATE item SET stock =? WHERE upc=?");
				ps.setInt(1, item.stock);
				ps.setString(2, item.getUPC());

				ps.executeUpdate();
				ps.close();
			}
			con.commit();
			con.setAutoCommit(true);
		}
		catch (SQLException ex){
			//
		}
	}

	// helper 3 - total balance of the shopping cart
	public double totalBill(){
		// calculate bill sum
		double total= 0;
		for (int i = 0; i < getShoppingCart().size(); i++) {
			Item item = getShoppingCart().get(i);
			total += item.getPrice() * item.getQuantity();
		}
		return total;
	}

	// helper 3
	private DefaultTableModel resultSetToTableModel(
			DefaultTableModel model,
			ResultSet row) throws SQLException
			{
		ResultSetMetaData meta= row.getMetaData();
		if(model==null) model= new DefaultTableModel();
		String cols[]=new String[meta.getColumnCount()];
		for(int i=0;i< cols.length;++i)
		{
			cols[i]= meta.getColumnLabel(i+1);
		}

		model.setColumnIdentifiers(cols);

		while(row.next())
		{
			Object data[]= new Object[cols.length];
			for(int i=0;i< data.length;++i)
			{
				data[i]=row.getObject(i+1);
			}
			model.addRow(data);
		}
		return model;
			}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public ArrayList<Item> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ArrayList<Item> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
}
