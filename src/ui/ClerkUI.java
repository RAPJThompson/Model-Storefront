package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Clerk;
import model.Item;

import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import exception.ItemException;
import exception.QuantityException;
import exception.ReceiptIdException;
import exception.ReturnException;

import javax.swing.JComboBox;

public class ClerkUI extends JFrame {

	private JPanel contentPane;
	private JTextField returnReceiptNum;
	private JTextField returnUPC;
	private JTable receipt_table;
	private JTable search_table;
	private JComboBox comboBox;
	private JTextField cashTender;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClerkUI frame = new ClerkUI();
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
	public ClerkUI() {
		setTitle("Clerk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1350, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		final Clerk cler = new Clerk();

		JLabel lblPleaseSelectOne = new JLabel("Please select one:");
		lblPleaseSelectOne.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPleaseSelectOne.setBounds(723, 180, 117, 36);
		contentPane.add(lblPleaseSelectOne);

		final JLabel lblTotal3 = new JLabel("0.00");
		lblTotal3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotal3.setBounds(1110, 577, 161, 31);
		contentPane.add(lblTotal3);

		final JLabel lbltxtReceiptNo = new JLabel("");
		lbltxtReceiptNo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbltxtReceiptNo.setBounds(723, 244, 82, 23);
		contentPane.add(lbltxtReceiptNo);

		final JLabel lblReceipt = new JLabel("receiptid");
		lblReceipt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblReceipt.setBounds(815, 244, 121, 23);
		contentPane.add(lblReceipt);

		final JLabel lblDate = new JLabel("0000/00/00");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDate.setBounds(1110, 248, 151, 14);
		contentPane.add(lblDate);	

		final JLabel lblSearchError = new JLabel("");
		lblSearchError.setBounds(369, 99, 231, 23);
		contentPane.add(lblSearchError);

		JLabel lblForReturnPlease = new JLabel("Receipt ID");
		lblForReturnPlease.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblForReturnPlease.setBounds(31, 499, 145, 36);
		contentPane.add(lblForReturnPlease);


		returnUPC = new JTextField();
		returnUPC.setColumns(10);
		returnUPC.setBounds(106, 540, 192, 23);
		contentPane.add(returnUPC);

		returnReceiptNum = new JTextField();
		returnReceiptNum.setColumns(10);
		returnReceiptNum.setBounds(106, 506, 192, 23);
		contentPane.add(returnReceiptNum);

		final JTextField upc = new JTextField();
		upc.setColumns(12);
		upc.setBounds(193, 87, 121, 20);
		contentPane.add(upc);

		final JTextField quantity = new JTextField();
		quantity.setBounds(446, 87, 68, 21);
		contentPane.add(quantity);

		//		final JLabel purchaseTotal = new JLabel("");
		//		purchaseTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		//		purchaseTotal.setBounds(815, 461, 406, 23);
		//		contentPane.add(purchaseTotal);

		JButton submitbutton = new JButton("Submit");
		submitbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				int receiptId = 0;
				String UPC = "";
				String receiptIdString = returnReceiptNum.getText();
				try {
					receiptId = Integer.parseInt(receiptIdString);
					UPC = returnUPC.getText();
					if (receiptId == 0){
						JOptionPane.showMessageDialog(null,
								"Please enter a receipt ID.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					else if (UPC.equals("")){
						JOptionPane.showMessageDialog(null,
								"Please enter a UPC.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					else if (UPC.length() != 12){
						JOptionPane.showMessageDialog(null,
								"Please enter a 12-digit UPC.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

					else if (receiptId != 0 && !UPC.equals("")){
						try {
							cler.receiptidReturn(receiptId, UPC);
							JOptionPane.showMessageDialog(null,
									"Success! Item returned!", "Message",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null,
									"Database error.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (ReceiptIdException e1) {
							JOptionPane.showMessageDialog(null,
									"The receipt ID and UPC combination does not exist in the database.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e1) {
							JOptionPane.showMessageDialog(null,
									"Database error.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (ReturnException e1) {
							JOptionPane.showMessageDialog(null,
									"The item was purchased too long ago to return.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null,
							"A receipt ID contains only numeric digits.", "NumberFormatException",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}
				);
		submitbutton.setBounds(319, 540, 89, 23);
		contentPane.add(submitbutton);

		JLabel lblWelcomeClerk = new JLabel("Welcome, Clerk!");
		lblWelcomeClerk.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblWelcomeClerk.setBounds(589, 0, 132, 30);
		contentPane.add(lblWelcomeClerk);

		JLabel lblForProcessingA = new JLabel("Select items to purchase:");
		lblForProcessingA.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblForProcessingA.setBounds(31, 42, 349, 30);
		contentPane.add(lblForProcessingA);

		JLabel lblReturningItems = new JLabel("Return an item:");
		lblReturningItems.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblReturningItems.setBounds(31, 471, 145, 30);
		contentPane.add(lblReturningItems);

		JButton btnReturnHome = new JButton("Return Home");
		btnReturnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home home=new Home();
				home.setVisible(true);
			}
		});
		btnReturnHome.setBounds(1126, 620, 145, 36);
		contentPane.add(btnReturnHome);

		JLabel lblEnterCreditCard_1 = new JLabel("Expiry date:");
		lblEnterCreditCard_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEnterCreditCard_1.setBounds(980, 121, 68, 36);
		contentPane.add(lblEnterCreditCard_1);

		final JTextField expiryDateMonth = new JTextField();
		expiryDateMonth.setBounds(1079, 121, 42, 23);
		contentPane.add(expiryDateMonth);

		final JTextField expiryDateYear = new JTextField();
		expiryDateYear.setBounds(1133, 121, 42, 23);
		contentPane.add(expiryDateYear);


		JLabel lblEnterUpc = new JLabel("Enter upc of item to purchase:");
		lblEnterUpc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEnterUpc.setBounds(31, 79, 179, 36);
		contentPane.add(lblEnterUpc);

		JLabel lblEnterQualityOf = new JLabel("Enter quantity:");
		lblEnterQualityOf.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEnterQualityOf.setBounds(354, 88, 74, 19);
		contentPane.add(lblEnterQualityOf);

		final JLabel total = new JLabel("0.00");
		total.setFont(new Font("Tahoma", Font.BOLD, 13));
		total.setBounds(440, 451, 117, 23);
		contentPane.add(total);


		final JLabel expiryError = new JLabel("");
		expiryError.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));
		expiryError.setBounds(980, 7, 231, 23);
		contentPane.add(expiryError);



		JLabel lblUpc = new JLabel("UPC");
		lblUpc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUpc.setBounds(31, 533, 42, 36);
		contentPane.add(lblUpc);

		JLabel lblEnterCreditCard = new JLabel("Card #:");
		lblEnterCreditCard.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEnterCreditCard.setBounds(999, 80, 42, 36);
		contentPane.add(lblEnterCreditCard);

		final JTextField cardNumber = new JTextField();
		cardNumber.setBounds(1053, 85, 173, 23);
		contentPane.add(cardNumber);

		final JLabel cardNumberUsed = new JLabel("xxxx-xxxx-xxxx-xxxx");
		cardNumberUsed.setFont(new Font("Tahoma", Font.BOLD, 13));
		cardNumberUsed.setBounds(723, 579, 267, 23);
		contentPane.add(cardNumberUsed);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(60, 155, 526, 284);
		contentPane.add(scrollPane_1);
		
		final DefaultTableModel emptyTable = new DefaultTableModel(
				new Object[][] {
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
						{null, null, null, null, null},
					},
					new String[] {
						"UPC", "TITLE", "STOCK", "QUANTITY","UNIT PRICE($)"
					});
		final JTable table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setModel(emptyTable);
		
		JButton btnCancelTransaction = new JButton("Cancel Transaction");
		btnCancelTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cler.clearCart();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
			    model.setRowCount(0);
				JOptionPane.showMessageDialog(null,
						"Transaction cancelled.", "Cancelled",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		btnCancelTransaction.setBounds(1050, 183, 145, 30);
		contentPane.add(btnCancelTransaction);
		
		JButton btnSearch = new JButton("Add Item");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				if (upc.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a UPC.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (upc.getText().length()!=12) {
					upc.setText("");
					quantity.setText("");
					JOptionPane.showMessageDialog(null,
							"Please enter a 12-digit UPC.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (quantity.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a quantity.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (Integer.parseInt(quantity.getText()) < 0) {
					quantity.setText("");
					JOptionPane.showMessageDialog(null,
							"Please enter a quantity greater than 0", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						int quantAdded = cler.searchItem(upc.getText(), quantity.getText());
						JOptionPane.showMessageDialog(null,
								quantAdded + " of " + "your item has been added to the shopping cart!", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						upc.setText("");
						quantity.setText("");

						DefaultTableModel model = cler.printShoppingCart();
						//JOptionPane.showMessageDialog(null,new JScrollPane(new JTable(model)))
						table.setModel(model);
						String ptotal = String.format("%.2f", cler.totalBill(cler.getCart()));
						total.setText("Total:  $" + ptotal);
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid number.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"There was an error communicating with the database.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (ItemException e1) {
						JOptionPane.showMessageDialog(null,
								"The UPC does not exist in the database.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} catch (QuantityException e1) {
						JOptionPane.showMessageDialog(null,
								"The item does not have sufficient stock.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} 
				}catch (NumberFormatException e3){
					upc.setText("");
					quantity.setText("");
					JOptionPane.showMessageDialog(null,
							"Please enter a valid quantity.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}

		});
		btnSearch.setBounds(539, 86, 89, 23);
		contentPane.add(btnSearch);

		JLabel lblProcessingTheTransaction = new JLabel("Pay by Cash:");
		lblProcessingTheTransaction.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProcessingTheTransaction.setBounds(723, 48, 117, 30);
		contentPane.add(lblProcessingTheTransaction);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(723, 279, 526, 284);
		contentPane.add(scrollPane_2);
		
		final JTable bill = new JTable();
		scrollPane_2.setViewportView(bill);		
		bill.setModel(emptyTable);

		try {
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");  
			String text;
			text = df.format(cler.getCurrentDate());
			lblDate.setText("Date: "+ text);
		} catch (ParseException e1) {
			// Couldn't retrieve the date
			e1.printStackTrace();
		}
		
		JButton btnNewButton = new JButton("Approve Transaction");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				int recieptid;
				String displayCC = "";
				String cNum = cardNumber.getText();
				String ccmonth = expiryDateMonth.getText();
				String ccyear = expiryDateYear.getText();
				String expDate = ccmonth + ccyear;
				
				if (cashTender.getText().length()==0 && cNum.length() ==0){
					JOptionPane.showMessageDialog(null,
							"Please enter either cash payment or credit card number.", "Error",
							JOptionPane.ERROR_MESSAGE);
		
				}else if(cashTender.getText().length()!=0 && cNum.length()!=0){
					JOptionPane.showMessageDialog(null,
							"Please choose either cash payment or credit card payment.", "Error",
							JOptionPane.ERROR_MESSAGE);
					cardNumber.setText("");
					cashTender.setText("");
					
				}else if(cashTender.getText().length()!=0){
					try{
						
						DefaultTableModel model = cler.printShoppingCart();
						String ptotal = String.format("%.2f", cler.totalBill(cler.getCart()));
						Double billTotal = cler.totalBill(cler.getCart());
						
						recieptid = cler.purchaseShoppingCart(cashTender.getText());
						
						double cashIn = Double.parseDouble(cashTender.getText());
						double remainder = cashIn - billTotal;
						
						String change = String.format("%.2f", remainder);
						JOptionPane.showMessageDialog(null,		
								"Purchase has been approved! Please give customer $" + change + " in change.", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						
						bill.setModel(model);
						displayCC = "Paid by Cash.";
						lblReceipt.setText(Integer.toString(recieptid));
						cardNumberUsed.setText(displayCC);
						
						lblTotal3.setText("Total:  $" + ptotal);
						total.setText("Total: $0.00");
						
						cashTender.setText("");
						table.setModel(emptyTable);
						
					}catch (ItemException e){
						JOptionPane.showMessageDialog(null,
								e.getMessage(), "ItemException",
								JOptionPane.ERROR_MESSAGE);
					}catch (SQLException e){
						JOptionPane.showMessageDialog(null,
								e.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);
					}catch (ParseException e){
						JOptionPane.showMessageDialog(null,
								e.getMessage(), "ParseException",
								JOptionPane.ERROR_MESSAGE);
					}
				}else if (cNum.length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a credit card number.", "Error",
							JOptionPane.ERROR_MESSAGE);
					cardNumber.setText("");
					expiryDateMonth.setText("");
					expiryDateYear.setText("");

				} else if (cNum.length()!=16) {
					JOptionPane.showMessageDialog(null,
							"Please enter a 16-digit credit card number.", "Error",
							JOptionPane.ERROR_MESSAGE);
					cardNumber.setText("");
					expiryDateMonth.setText("");
					expiryDateYear.setText("");

				} else if (ccmonth.length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter card expiry month.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (ccmonth.length()!=2) {
					JOptionPane.showMessageDialog(null,
							"Please enter a 2-digit month.", "Error",
							JOptionPane.ERROR_MESSAGE);
					expiryDateMonth.setText("");
				} else if (ccyear.length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter card expiry year.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (ccyear.length()!=2) {
					JOptionPane.showMessageDialog(null,
							"Please enter card 2-digit year.", "Error",
							JOptionPane.ERROR_MESSAGE);
					expiryDateYear.setText("");
				}
				else {
					try{
						
						Long.parseLong(cNum);
						int ccMonth = Integer.parseInt(ccmonth);
						int ccYear = Integer.parseInt(ccyear);
						
						if (ccYear < 13) {
							expiryDateYear.setText("");
							throw new ItemException("Please enter a year after 2012.");
							
						}else if (ccYear == 13 && ccMonth < 8) {
							expiryDateMonth.setText("");
							throw new ItemException("Please enter a month on or after the current month.");
						}else if(ccMonth < 1 || ccMonth > 12){
							expiryDateMonth.setText("");
							throw new ItemException("Please enter a month between 01 - 12.");
						}
						
						DefaultTableModel model = cler.printShoppingCart();
						String ptotal = String.format("%.2f", cler.totalBill(cler.getCart()));
						recieptid = cler.purchaseShoppingCart(cNum, expDate);
						
						JOptionPane.showMessageDialog(null,		
								"Your purchase has been approved!", "Information",
								JOptionPane.INFORMATION_MESSAGE);

						bill.setModel(model);
						
						
						lblTotal3.setText("Total:  $" + ptotal);
						table.setModel(emptyTable);
						total.setText("Total: $0.00");
						
						displayCC = "xxxx-xxxx-xxx" + cNum.substring(cNum.length() - 5);
						lblReceipt.setText(Integer.toString(recieptid));
						cardNumberUsed.setText(displayCC);
						cardNumber.setText("");
						expiryDateMonth.setText("");
						expiryDateYear.setText("");
						
					}catch (SQLException e){
						JOptionPane.showMessageDialog(null,
								e.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);
					}catch (ParseException e){
						JOptionPane.showMessageDialog(null,
								e.getMessage(), "ParseException",
								JOptionPane.ERROR_MESSAGE);
					}catch (NumberFormatException e){
						JOptionPane.showMessageDialog(null,
								e.getMessage(), "ParseException",
								JOptionPane.ERROR_MESSAGE);
					}catch (ItemException e){
						JOptionPane.showMessageDialog(null,
								e.getMessage(), "ItemException",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnNewButton.setBounds(859, 183, 161, 30);
		contentPane.add(btnNewButton);


		String[] tableStrings = { "Item", "LeadSinger", "HasSong", "Purchase", "PurchaseItem", 
				"Return", "ReturnItem", "Customer" };
		comboBox = new JComboBox(tableStrings);
		comboBox.setBounds(31, 627, 157, 23);
		contentPane.add(comboBox);

		JButton button = new JButton("View Table");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					DefaultTableModel model = cler.getTable((String)comboBox.getSelectedItem());
					JOptionPane.showMessageDialog(null,new JScrollPane(new JTable(model)),
							"Viewing " + (String)comboBox.getSelectedItem(),JOptionPane.PLAIN_MESSAGE);	
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,
							"Message: " + e1.getMessage(), "SQLException",
							JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		button.setBounds(198, 626, 97, 23);
		contentPane.add(button);
		
		JLabel lblMm = new JLabel("MM");
		lblMm.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMm.setBounds(1091, 138, 24, 36);
		contentPane.add(lblMm);
		
		JLabel lblYy = new JLabel("YY");
		lblYy.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblYy.setBounds(1147, 138, 24, 36);
		contentPane.add(lblYy);
		
		JLabel lblPayByCredit = new JLabel("Pay by Credit Card:");
		lblPayByCredit.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPayByCredit.setBounds(980, 48, 171, 30);
		contentPane.add(lblPayByCredit);
		
		JLabel lblCashTendered = new JLabel("Cash tendered:");
		lblCashTendered.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCashTendered.setBounds(723, 87, 82, 36);
		contentPane.add(lblCashTendered);
		
		cashTender = new JTextField();
		cashTender.setBounds(818, 92, 82, 23);
		contentPane.add(cashTender);
		
		JLabel lblOr = new JLabel("OR");
		lblOr.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblOr.setBounds(928, 81, 34, 30);
		contentPane.add(lblOr);
	}
}
