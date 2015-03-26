package model;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

import exception.ItemException;
import exception.QuantityException;

public class Transaction {

	protected Connection con;

	public Transaction() {
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

	public int insertPurchase(java.sql.Date date, String cid, String card, String expiryDate, java.sql.Date expectedDate, java.sql.Date deliveredDate) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("INSERT INTO Purchase VALUES (receiptId_counter.nextval,?,?,?,?,?,?)");
		ps.setDate(1, date);
		ps.setString(2, cid);
		ps.setString(3, card);
		ps.setString(4, expiryDate);
		ps.setDate(5, expectedDate);
		ps.setDate(6, deliveredDate);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int insertPurchaseItem(int receiptId, String upc, int quantity) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("INSERT INTO purchaseitem VALUES (?,?,?)");
		ps.setInt(1, receiptId);
		ps.setString(2, upc);
		ps.setInt(3, quantity);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int insertCustomer(String cid, String password, String name, String address, String phone) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?)");
		ps.setString(1, cid);
		ps.setString(2, password);
		ps.setString(3, name);
		ps.setString(4, address);
		ps.setString(5, phone);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int insertReturn(java.sql.Date date, int receiptId) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("INSERT INTO return VALUES (retid_counter.nextval,?,?)");
		ps.setDate(1, date);
		ps.setInt(2, receiptId);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int insertReturnItem(int retId, String upc, int quantity) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("INSERT INTO returnitem VALUES (?,?,?)");
		ps.setInt(1, retId);
		ps.setString(2, upc);
		ps.setInt(3, quantity);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int insertHasSong(String upc, String title) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("INSERT INTO hassong VALUES (?,?)");
		ps.setString(1, upc);
		ps.setString(2, title);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int insertLeadSinger(String upc, String name) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("INSERT INTO leadsinger VALUES (?,?)");
		ps.setString(1, upc);
		ps.setString(2, name);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int insertItem(String upc, String title, String type, String category, String company, String year, double price, int stock) throws SQLException, QuantityException, ItemException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("SELECT COUNT(*) FROM item WHERE upc = ?");
		
		ps.setString(1, upc);
		
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		int rowCount = rs.getInt(1);		
		if (rowCount > 0) {
			throw new ItemException("UPC already exists in the database.");
		}

		if (stock<=0) {
			throw new QuantityException();
		}
		if (price<=0) {
			throw new QuantityException();
		}
		
		ps = con.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?,?,?,?)");

		ps.setString(1, upc);
		ps.setString(2, title);
		ps.setString(3, type);
		ps.setString(4, category);
		ps.setString(5, company);
		ps.setString(6, year);
		ps.setDouble(7, price);
		ps.setInt(8, stock);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int deletePurchase(int receiptId) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("DELETE FROM purchase WHERE receiptId = ?");
		ps.setInt(1, receiptId);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int deletePurchaseItem(int receiptId, String upc) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("DELETE FROM purchaseitem WHERE receiptId = ? AND upc = ?");
		ps.setInt(1, receiptId);
		ps.setString(2, upc);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int deleteCustomer(String cid) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("DELETE FROM customer WHERE cid = ?");
		ps.setString(1, cid);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int deleteReturn(int retId) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("DELETE FROM return WHERE retid = ?");
		ps.setInt(1, retId);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int deleteReturnItem(int retId, String upc) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("INSERT INTO returnitem WHERE retid = ? AND upc = ?");
		ps.setInt(1, retId);
		ps.setString(2, upc);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int deleteHasSong(String upc, String title) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("DELETE FROM hassong WHERE upc = ? AND title = ?");
		ps.setString(1, upc);
		ps.setString(2, title);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public int deleteLeadSinger(String upc, String name) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("DELETE FROM leadsinger WHERE upc = ? AND name = ?");
		ps.setString(1, upc);
		ps.setString(2, name);
		ps.executeUpdate();
		ps.close();
		return 0;
	}	

	public int deleteItem(String upc) throws SQLException {
		PreparedStatement ps = null;
		ps = con.prepareStatement("DELETE FROM item WHERE upc = ?");
		ps.setString(1, upc);
		ps.executeUpdate();
		ps.close();
		return 0;
	}

	public DefaultTableModel getTable(String table) throws SQLException {
		Statement          stmt;
		ResultSet          rs;
		DefaultTableModel  model;

		String query = "SELECT * FROM " + table;

		stmt = con.createStatement();
		rs = stmt.executeQuery(query);

		// get info on ResultSet
		ResultSetMetaData rsmd = rs.getMetaData();

		// parse ResultSet into TableModel
		model = new DefaultTableModel();
		String cols[] = new String[rsmd.getColumnCount()];
		for(int i=0; i<cols.length; ++i) {
			cols[i] = rsmd.getColumnLabel(i+1);
		}

		model.setColumnIdentifiers(cols);

		while(rs.next()) {
			Object data[] = new Object[cols.length];
			for(int i=0; i<data.length; ++i) {
				data[i] = rs.getObject(i+1);
			}
			model.addRow(data);
		}

		stmt.close();

		return model;
	}

	public java.sql.Date getCurrentDate() throws ParseException {		 
		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		java.util.Date utilDate = fm.parse(fm.format(date));
		return new java.sql.Date(utilDate.getTime());
	}

	// helper 3 - total balance of the shopping cart
	public double totalBill(ArrayList<Item> shoppingCart) {
		// calculate bill sum
		double total= 0;
		for (int i = 0; i < shoppingCart.size(); i++) {
			Item item = shoppingCart.get(i);
			total += item.getPrice() * item.getQuantity();
		}
		return total;
	}
}

