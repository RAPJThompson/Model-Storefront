package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;

import exception.ItemException;
import exception.QuantityException;
import exception.ReceiptIdException;
import exception.StoreException;
import model.Manager;

public class ManagerUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUpUPC;
	private JTextField txtUpStock;
	private JTextField txtReceipt;
	private JTextField txtDailyDate;
	private JTextField txtUpPrice;
	private JTextField txtDel;
	private JTextField txtTop;
	private JTextField txtTopDate;
	private JTextField txtAddUPC;
	private JTextField txtAddTitle;
	private JTextField txtAddYear;
	private JTextField txtAddPrice;
	private JTextField txtAddStock;
	private JTextField txtAddComp;
	DefaultTableModel model1;
	DefaultTableModel model2;
	DefaultTableModel model3;
	private JTable tableAllItem;
	private JTable tableTotal;
	private JTable tableCat;
	private JComboBox comboCat;
	private JComboBox comboType;
	private JComboBox comboTable;
	private Manager maObject;
	private JTextField txtSinger;
	private JTextField txtSong;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerUI frame = new ManagerUI();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManagerUI() {
		maObject = new Manager();
		setTitle("Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1350, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPleaseAddItem = new JLabel("Update item:");
		lblPleaseAddItem.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPleaseAddItem.setBounds(40, 312, 133, 23);
		contentPane.add(lblPleaseAddItem);

		JLabel lblUpc = new JLabel("UPC");
		lblUpc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUpc.setBounds(40, 346, 77, 23);
		contentPane.add(lblUpc);

		JLabel lblUpStock = new JLabel("Stock");
		lblUpStock.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUpStock.setBounds(40, 380, 67, 23);
		contentPane.add(lblUpStock);

		txtUpUPC = new JTextField();

		txtUpUPC.setBounds(91, 346, 157, 23);
		contentPane.add(txtUpUPC);
		txtUpUPC.setColumns(10);

		txtUpStock = new JTextField();
		txtUpStock.setColumns(10);
		txtUpStock.setBounds(91, 380, 157, 23);
		contentPane.add(txtUpStock);

		JButton smbtUpdateItem = new JButton("Update Item");
		smbtUpdateItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pattern pattern = Pattern.compile("\\d{12}");
				Matcher matcher = pattern.matcher(txtUpUPC.getText());

				if (txtUpUPC.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a UPC.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtUpUPC.getText().length()!=12) {
					JOptionPane.showMessageDialog(null,
							"Please enter a 12-digit UPC.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtUpStock.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a stock quantity.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtUpUPC.getText().length()==12 && !(matcher.matches())) {
					JOptionPane.showMessageDialog(null,
							"UPC should contain 12 numeric digits.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						maObject.updateItem(txtUpUPC.getText(), txtUpStock.getText(), txtUpPrice.getText());
						JOptionPane.showMessageDialog(null,
								"Success! Item has been updated.", "Message",
								JOptionPane.PLAIN_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e1.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);
					} catch (QuantityException e2) {
						JOptionPane.showMessageDialog(null,
								e2.getMessage(), "QuantityException",
								JOptionPane.ERROR_MESSAGE);
					} catch (ItemException e3) {
						JOptionPane.showMessageDialog(null,
								e3.getMessage(), "ItemException",
								JOptionPane.ERROR_MESSAGE);
					} catch (NumberFormatException e4) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid number.", "NumberFormatException",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});	
		smbtUpdateItem.setBounds(266, 380, 113, 23);
		contentPane.add(smbtUpdateItem);

		JLabel lblReceiptId = new JLabel("Receipt ID");
		lblReceiptId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblReceiptId.setBounds(40, 510, 67, 36);
		contentPane.add(lblReceiptId);

		txtReceipt = new JTextField();
		txtReceipt.setColumns(10);
		txtReceipt.setBounds(145, 517, 157, 23);
		contentPane.add(txtReceipt);

		JLabel lblDailyDateyyyymmdd = new JLabel("Date (YYYY/MM/DD)");
		lblDailyDateyyyymmdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDailyDateyyyymmdd.setBounds(834, 45, 137, 35);
		contentPane.add(lblDailyDateyyyymmdd);

		txtDailyDate = new JTextField();
		txtDailyDate.setColumns(10);
		txtDailyDate.setBounds(941, 51, 144, 23);
		contentPane.add(txtDailyDate);

		JButton btnDaily = new JButton("View Report");
		btnDaily.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model1 = maObject.getDailySales(txtDailyDate.getText());
					tableAllItem.setModel(model1);
					model2 = maObject.getDailyCat(txtDailyDate.getText());
					tableCat.setModel(model2);
					model3 = maObject.getDailyTotal(txtDailyDate.getText());
					tableTotal.setModel(model3);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,
							"Message: " + e1.getMessage(), "SQLException",
							JOptionPane.ERROR_MESSAGE);
				} catch (ParseException e2) {
					JOptionPane.showMessageDialog(null,
							"Please enter a valid date.", "ParseException",
							JOptionPane.ERROR_MESSAGE);
				}
			}});
		btnDaily.setBounds(1106, 51, 124, 23);
		contentPane.add(btnDaily);

		JLabel lblDailyReport = new JLabel("Daily sales report:");
		lblDailyReport.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDailyReport.setBounds(575, 49, 155, 23);
		contentPane.add(lblDailyReport);

		JLabel lblProcessTheDelivery = new JLabel("Process the delivery date of an order:");
		lblProcessTheDelivery.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProcessTheDelivery.setBounds(40, 483, 301, 23);
		contentPane.add(lblProcessTheDelivery);

		JButton btnDel = new JButton("Submit");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				if (txtReceipt.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a receipt ID.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtDel.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a date.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						maObject.processDelivery(txtReceipt.getText(), txtDel.getText());
						JOptionPane.showMessageDialog(null,
								"Success! Delivery date has been updated.", "Message",
								JOptionPane.PLAIN_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e1.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);
					} catch (ParseException e2) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid date.", "ParseException",
								JOptionPane.ERROR_MESSAGE);
					} catch (ReceiptIdException e3) {
						JOptionPane.showMessageDialog(null,
								e3.getMessage(), "ReceiptIdException",
								JOptionPane.ERROR_MESSAGE);
					} catch (NumberFormatException e4) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid receipt ID.", "NumberFormatException",
								JOptionPane.ERROR_MESSAGE);
					} catch (StoreException e5) {
						JOptionPane.showMessageDialog(null,
								e5.getMessage(), "StoreException",
								JOptionPane.ERROR_MESSAGE);
					} 
				}
			}
		});
		btnDel.setBounds(322, 539, 97, 23);
		contentPane.add(btnDel);

		JLabel lblTopSellingItems = new JLabel("Top selling items report:");
		lblTopSellingItems.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTopSellingItems.setBounds(575, 483, 196, 23);
		contentPane.add(lblTopSellingItems);

		JLabel lblNewLabel = new JLabel("Welcome, Manager!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(555, 0, 166, 34);
		contentPane.add(lblNewLabel);

		JButton btnNewButton_1 = new JButton("Return Home");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home home=new Home();
				home.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(1150, 617, 124, 36);
		contentPane.add(btnNewButton_1);

		txtUpPrice = new JTextField();
		txtUpPrice.setColumns(10);
		txtUpPrice.setBounds(91, 414, 157, 23);
		contentPane.add(txtUpPrice);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPrice.setBounds(40, 414, 67, 23);
		contentPane.add(lblPrice);

		txtDel = new JTextField();
		txtDel.setColumns(10);
		txtDel.setBounds(145, 551, 157, 23);
		contentPane.add(txtDel);

		JLabel lblDelivery = new JLabel("Date (YYYY/MM/DD)");
		lblDelivery.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDelivery.setBounds(40, 544, 103, 36);
		contentPane.add(lblDelivery);

		JButton btnTop = new JButton("View Report");
		btnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtTopDate.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a date.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtTop.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a number.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						DefaultTableModel model = maObject.getTopSelling(txtTopDate.getText(), txtTop.getText());
						JOptionPane.showMessageDialog(null,new JScrollPane(new JTable(model)),
								"Top Selling Items for " + txtTopDate.getText(),JOptionPane.PLAIN_MESSAGE);					
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e1.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);
					} catch (ParseException e2) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid date and/or number.", "ParseException",
								JOptionPane.ERROR_MESSAGE);
					} catch (QuantityException e3) {
						JOptionPane.showMessageDialog(null,
								"Please enter a number greater than 0.", "QuantityException",
								JOptionPane.ERROR_MESSAGE);
					} catch (NumberFormatException e4) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid number.", "NumberFormatException",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnTop.setBounds(1106, 497, 124, 23);
		contentPane.add(btnTop);

		txtTop = new JTextField();
		txtTop.setColumns(10);
		txtTop.setBounds(941, 517, 144, 23);
		contentPane.add(txtTop);

		txtTopDate = new JTextField();
		txtTopDate.setColumns(10);
		txtTopDate.setBounds(941, 483, 144, 23);
		contentPane.add(txtTopDate);

		JLabel lblTopDateyyyymmdd = new JLabel("Date (YYYY/MM/DD)");
		lblTopDateyyyymmdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTopDateyyyymmdd.setBounds(834, 478, 97, 36);
		contentPane.add(lblTopDateyyyymmdd);

		JLabel lblNumberOfItems = new JLabel("Number of items");
		lblNumberOfItems.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNumberOfItems.setBounds(834, 510, 86, 36);
		contentPane.add(lblNumberOfItems);

		JLabel lblAddNewItem = new JLabel("Add new item:");
		lblAddNewItem.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddNewItem.setBounds(40, 46, 133, 23);
		contentPane.add(lblAddNewItem);

		JLabel label_1 = new JLabel("UPC");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(40, 80, 77, 23);
		contentPane.add(label_1);

		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTitle.setBounds(40, 114, 67, 23);
		contentPane.add(lblTitle);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblType.setBounds(40, 148, 38, 23);
		contentPane.add(lblType);

		txtAddUPC = new JTextField();
		txtAddUPC.setColumns(10);
		txtAddUPC.setBounds(97, 80, 157, 23);
		contentPane.add(txtAddUPC);

		txtAddTitle = new JTextField();
		txtAddTitle.setColumns(10);
		txtAddTitle.setBounds(97, 114, 157, 23);
		contentPane.add(txtAddTitle);

		String[] typeStrings = { "", "cd", "dvd" };
		comboType = new JComboBox(typeStrings);
		comboType.setBounds(97, 148, 157, 23);
		contentPane.add(comboType);

		String[] catStrings = { "", "classical", "country", "instrumental", "new age", "pop", "rap", "rock" };
		comboCat = new JComboBox(catStrings);
		comboCat.setBounds(97, 182, 157, 23);
		contentPane.add(comboCat);

		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pattern pattern = Pattern.compile("\\d{12}");
				Matcher matcher = pattern.matcher(txtAddUPC.getText());

				if (txtAddUPC.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a UPC.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtAddUPC.getText().length()!=12) {
					JOptionPane.showMessageDialog(null,
							"Please enter a 12-digit UPC.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtAddPrice.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a price.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtAddStock.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a stock quantity.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtSinger.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter at least one lead singer.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtSong.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter at least one song title.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (txtAddUPC.getText().length()==12 && !(matcher.matches())) {
					JOptionPane.showMessageDialog(null,
							"UPC should contain 12 numeric digits.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						maObject.insertItem(txtAddUPC.getText(), txtAddTitle.getText(), 
								(String)comboType.getSelectedItem(), (String)comboCat.getSelectedItem(), 
								txtAddComp.getText(), txtAddYear.getText(), Double.parseDouble(txtAddPrice.getText()), 
								Integer.parseInt(txtAddStock.getText()));
						String singerText = txtSinger.getText();
						String[] singers = singerText.split(",");
						for(int i=0; i<singers.length; i++) {
							maObject.insertLeadSinger(txtAddUPC.getText(), singers[i]);
						}
						String songText = txtSong.getText();
						String[] songs = songText.split(",");
						for(int i=0; i<singers.length; i++) {
							maObject.insertHasSong(txtAddUPC.getText(), songs[i]);
						}
						JOptionPane.showMessageDialog(null,
								"Success! Item has been added.", "Message",
								JOptionPane.PLAIN_MESSAGE);
						txtAddUPC.setText("");
						txtAddTitle.setText("");
						comboType.setSelectedItem("");
						comboCat.setSelectedItem("");
						txtAddComp.setText("");
						txtAddYear.setText("");
						txtAddPrice.setText("");
						txtAddStock.setText("");
						txtSinger.setText("");
						txtSong.setText("");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e1.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);
					} catch (QuantityException e2) {
						JOptionPane.showMessageDialog(null,
								"Please enter a number greater than 0.", "QuantityException",
								JOptionPane.ERROR_MESSAGE);
					} catch (ItemException e3) {
						JOptionPane.showMessageDialog(null,
								e3.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (NumberFormatException e4) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid number.", "NumberFormatException",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnAddItem.setBounds(240, 273, 113, 23);
		contentPane.add(btnAddItem);

		txtAddYear = new JTextField();
		txtAddYear.setColumns(10);
		txtAddYear.setBounds(341, 114, 157, 23);
		contentPane.add(txtAddYear);

		txtAddPrice = new JTextField();
		txtAddPrice.setColumns(10);
		txtAddPrice.setBounds(341, 148, 157, 23);
		contentPane.add(txtAddPrice);

		txtAddStock = new JTextField();
		txtAddStock.setColumns(10);
		txtAddStock.setBounds(341, 182, 157, 23);
		contentPane.add(txtAddStock);

		txtAddComp = new JTextField();
		txtAddComp.setColumns(10);
		txtAddComp.setBounds(341, 80, 157, 23);
		contentPane.add(txtAddComp);

		JLabel lblYear = new JLabel("Year");
		lblYear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblYear.setBounds(286, 114, 77, 23);
		contentPane.add(lblYear);

		JLabel lblPrice_1 = new JLabel("Price");
		lblPrice_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPrice_1.setBounds(286, 148, 67, 23);
		contentPane.add(lblPrice_1);

		JLabel lblStock = new JLabel("Stock");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStock.setBounds(286, 182, 67, 23);
		contentPane.add(lblStock);

		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCategory.setBounds(40, 182, 45, 23);
		contentPane.add(lblCategory);

		JLabel lblCompany = new JLabel("Company");
		lblCompany.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCompany.setBounds(286, 80, 67, 23);
		contentPane.add(lblCompany);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(575, 113, 383, 251);
		contentPane.add(scrollPane);

		tableAllItem = new JTable();
		scrollPane.setViewportView(tableAllItem);
		tableAllItem.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"UPC", "CATEGORY", "UNITPRICE", "UNITS", "TOTALVALUE"
				}
				));

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(575, 403, 196, 39);
		contentPane.add(scrollPane_2);

		tableTotal = new JTable();
		scrollPane_2.setViewportView(tableTotal);
		tableTotal.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"TOTALUNITS", "TOTALVALUE"
				}
				));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(997, 112, 233, 251);
		contentPane.add(scrollPane_1);

		tableCat = new JTable();
		scrollPane_1.setViewportView(tableCat);
		tableCat.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"CATEGORY", "UNITS", "TOTALVALUE"
				}
				));

		JLabel lblNewLabel_1 = new JLabel("Total sales:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(575, 380, 113, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblAllItems = new JLabel("All items sold:");
		lblAllItems.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAllItems.setBounds(575, 91, 113, 14);
		contentPane.add(lblAllItems);

		JLabel lblByCategory = new JLabel("Items sold by category:");
		lblByCategory.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblByCategory.setBounds(996, 91, 192, 14);
		contentPane.add(lblByCategory);

		String[] tableStrings = { "Item", "LeadSinger", "HasSong", "Purchase", "PurchaseItem", 
				"Return", "ReturnItem", "Customer" };		
		comboTable = new JComboBox(tableStrings);
		comboTable.setBounds(30, 625, 157, 23);
		contentPane.add(comboTable);

		JButton btnViewTable = new JButton("View Table");
		btnViewTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel model = maObject.getTable((String)comboTable.getSelectedItem());
					JOptionPane.showMessageDialog(null,new JScrollPane(new JTable(model)),
							"Viewing " + (String)comboTable.getSelectedItem(),JOptionPane.PLAIN_MESSAGE);	
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,
							"Message: " + e1.getMessage(), "SQLException",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnViewTable.setBounds(197, 624, 97, 23);
		contentPane.add(btnViewTable);

		txtSinger = new JTextField();
		txtSinger.setColumns(10);
		txtSinger.setBounds(97, 216, 157, 23);
		contentPane.add(txtSinger);

		txtSong = new JTextField();
		txtSong.setColumns(10);
		txtSong.setBounds(341, 216, 157, 23);
		contentPane.add(txtSong);

		JLabel lblSong = new JLabel("Songs");
		lblSong.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSong.setBounds(286, 216, 67, 23);
		contentPane.add(lblSong);

		JLabel lblSinger = new JLabel("Singers");
		lblSinger.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSinger.setBounds(40, 216, 45, 23);
		contentPane.add(lblSinger);

		JLabel lblExampleSingerAsinger = new JLabel("Example: singer a,singer b");
		lblExampleSingerAsinger.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblExampleSingerAsinger.setBounds(97, 238, 157, 14);
		contentPane.add(lblExampleSingerAsinger);

		JLabel lblExampleSongAsong = new JLabel("Example: song a,song b");
		lblExampleSongAsong.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblExampleSongAsong.setBounds(341, 238, 157, 14);
		contentPane.add(lblExampleSongAsong);
	}
}
