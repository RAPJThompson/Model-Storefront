package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableModel;

import exception.ItemException;
import exception.QuantityException;
import exception.ReceiptIdException;
import exception.StoreException;

public class Manager extends Transaction {

	private Connection con;

	public Manager() {
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

	public void updateItem(String upc, String stock, String price) throws SQLException, QuantityException, ItemException {
		PreparedStatement  ps;

		try	{
			con.setAutoCommit(false);

			ps = con.prepareStatement("UPDATE item SET stock=?, price=? WHERE upc=?");

			ps.setString(3, upc);

			int stockNum = Integer.parseInt(stock);			
			if (stockNum<0) {
				throw new QuantityException("Please enter a non-negative stock quantity.");
			}			
			ps.setInt(1, stockNum);

			// if price input is null, check if UPC exists in database
			//   if UPC does not exist, throw exception
			//   otherwise retain old price
			if (price.length() == 0) {
				PreparedStatement psTemp = con.prepareStatement("SELECT COUNT(*) FROM item WHERE upc=?");

				psTemp.setString(1, upc);

				ResultSet rsTemp = psTemp.executeQuery();

				rsTemp.next();
				int rowCount = rsTemp.getInt(1);

				if (rowCount == 0) {
					rsTemp.close();
					psTemp.close();
					ps.close();
					throw new ItemException("UPC does not exist in database.");
				} else { 
					psTemp = con.prepareStatement("SELECT price FROM item WHERE upc=?");

					psTemp.setString(1, upc);

					rsTemp = psTemp.executeQuery();
					rsTemp.next();
					double currPrice = rsTemp.getDouble(1);

					ps.setDouble(2, currPrice);

					rsTemp.close();
					psTemp.close();
				}				
			} else {
				double priceNum = Double.parseDouble(price);
				if (priceNum<=0) {
					throw new QuantityException("Please enter a price greater than 0.");
				}
				ps.setDouble(2, priceNum);
			}

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				ps.close();
				throw new ItemException("UPC does not exist in database.");
			}

			con.commit();			
			ps.close();
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

	public void processDelivery(String receiptId, String deliveredDate) throws SQLException, ParseException, ReceiptIdException, StoreException {
		PreparedStatement  ps;

		try {
			con.setAutoCommit(false);

			// check if receipt id is associated with in-store purchase
			//  if so, throw exception
			ps = con.prepareStatement("SELECT cid FROM purchase WHERE receiptId=?");

			int receiptNum = Integer.parseInt(receiptId);
			ps.setInt(1, receiptNum);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String cid = rs.getString(1);
				if (cid.equals("STOR")) {
					rs.close();
					ps.close();
					throw new StoreException("Receipt ID is associated with in-store purchase.");
				}	
			}

			// set delivery date
			ps = con.prepareStatement("UPDATE purchase SET deliveredDate=? WHERE receiptId=?");

			ps.setInt(2, receiptNum);

			// parse date
			SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
			fm.setLenient(false);
			java.util.Date utilDate = fm.parse(deliveredDate);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			ps.setDate(1, sqlDate);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				ps.close();
				throw new ReceiptIdException("Receipt ID does not exist in database.");
			}

			con.commit();
			ps.close();
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

	public DefaultTableModel getTopSelling(String tdate, String top) throws SQLException, ParseException, QuantityException {
		PreparedStatement  ps;
		ResultSet          rs;
		DefaultTableModel  model;

		con.setAutoCommit(false);

		ps = con.prepareStatement("SELECT * " +
				"FROM (SELECT item.upc, title, company, stock AS currentStock, SUM(quantity) AS sold " +
				"FROM item, purchase, purchaseitem " +
				"WHERE pdate=? AND purchase.receiptId=purchaseitem.receiptId AND item.upc=purchaseitem.upc " +
				"GROUP BY item.upc, title, company, stock " +
				"ORDER BY sold DESC) " +
				"WHERE rownum<=?");

		// parse date
		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
		fm.setLenient(false);
		java.util.Date utilDate = fm.parse(tdate);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		ps.setDate(1, sqlDate);

		int topNum = Integer.parseInt(top);		
		if (topNum<=0) {
			throw new QuantityException("Please enter a number greater than 0.");
		}		
		ps.setInt(2, topNum);

		rs = ps.executeQuery();

		// get info on ResultSet
		ResultSetMetaData rsmd = rs.getMetaData();

		// parse ResultSet into TableModel
		model = new DefaultTableModel();
		String cols[] = new String[rsmd.getColumnCount()];
		for (int i=0; i<cols.length; ++i) {
			cols[i] = rsmd.getColumnLabel(i+1);
		}

		model.setColumnIdentifiers(cols);

		while (rs.next()) {
			Object data[] = new Object[cols.length];
			for (int i=0; i<data.length; ++i) {
				data[i] = rs.getObject(i+1);
			}
			model.addRow(data);
		}

		rs.close();
		ps.close();

		return model;
	}

	public DefaultTableModel getDailySales(String ddate) throws SQLException, ParseException {
		PreparedStatement  ps;
		ResultSet          rs;
		DefaultTableModel  model;

		con.setAutoCommit(false);

		ps = con.prepareStatement("SELECT item.upc, category, price AS unitPrice, SUM(quantity) AS units, " +
				"SUM(quantity*price) AS totalValue " +
				"FROM item, purchase, purchaseitem " +
				"WHERE pdate=? AND purchase.receiptId=purchaseitem.receiptId " +
				"AND item.upc=purchaseitem.upc " +
				"GROUP BY item.upc, category, price " +
				"ORDER BY category");

		// parse date
		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
		fm.setLenient(false);
		java.util.Date utilDate = fm.parse(ddate);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		ps.setDate(1, sqlDate);

		rs = ps.executeQuery();

		// get info on ResultSet
		ResultSetMetaData rsmd = rs.getMetaData();

		// parse ResultSet into TableModel
		model = new DefaultTableModel();
		String cols[] = new String[rsmd.getColumnCount()];
		for (int i=0; i<cols.length; ++i) {
			cols[i] = rsmd.getColumnLabel(i+1);
		}

		model.setColumnIdentifiers(cols);

		while (rs.next()) {
			Object data[] = new Object[cols.length];
			for (int i=0; i<data.length; ++i) {
				data[i] = rs.getObject(i+1);
			}
			model.addRow(data);
		}

		rs.close();
		ps.close();

		return model;
	}

	public DefaultTableModel getDailyCat(String ddate) throws SQLException, ParseException {
		PreparedStatement  ps;
		ResultSet          rs;
		DefaultTableModel  model;

		con.setAutoCommit(false);

		ps = con.prepareStatement("SELECT category, SUM(quantity) as units, " +
				"SUM(quantity*price) AS totalValue " +
				"FROM item, purchase, purchaseitem " +
				"WHERE pdate=? AND purchase.receiptId=purchaseitem.receiptId " +
				"AND item.upc=purchaseitem.upc GROUP BY category ORDER BY category");

		// parse date
		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
		fm.setLenient(false);
		java.util.Date utilDate = fm.parse(ddate);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		ps.setDate(1, sqlDate);

		rs = ps.executeQuery();

		// get info on ResultSet
		ResultSetMetaData rsmd = rs.getMetaData();

		// parse ResultSet into TableModel
		model = new DefaultTableModel();
		String cols[] = new String[rsmd.getColumnCount()];
		for (int i=0; i<cols.length; ++i) {
			cols[i] = rsmd.getColumnLabel(i+1);
		}

		model.setColumnIdentifiers(cols);

		while (rs.next()) {
			Object data[] = new Object[cols.length];
			for (int i=0; i<data.length; ++i) {
				data[i] = rs.getObject(i+1);
			}
			model.addRow(data);
		}

		rs.close();
		ps.close();

		return model;
	}

	public DefaultTableModel getDailyTotal(String ddate) throws SQLException, ParseException {
		PreparedStatement  ps;
		ResultSet          rs;
		DefaultTableModel  model;

		con.setAutoCommit(false);

		ps = con.prepareStatement("SELECT SUM(quantity) as totalUnits, " +
				"SUM(quantity*price) AS totalValue FROM item, purchase, purchaseitem " +
				"WHERE pdate=? AND purchase.receiptId=purchaseitem.receiptId " +
				"AND item.upc=purchaseitem.upc");

		// parse date
		SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");
		fm.setLenient(false);
		java.util.Date utilDate = fm.parse(ddate);
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		ps.setDate(1, sqlDate);

		rs = ps.executeQuery();

		// get info on ResultSet
		ResultSetMetaData rsmd = rs.getMetaData();

		// parse ResultSet into TableModel
		model = new DefaultTableModel();
		String cols[] = new String[rsmd.getColumnCount()];
		for (int i=0; i<cols.length; ++i) {
			cols[i] = rsmd.getColumnLabel(i+1);
		}

		model.setColumnIdentifiers(cols);

		while (rs.next()) {
			Object data[] = new Object[cols.length];
			for (int i=0; i<data.length; ++i) {
				data[i] = rs.getObject(i+1);
			}
			model.addRow(data);
		}

		rs.close();
		ps.close();

		return model;
	}

}
