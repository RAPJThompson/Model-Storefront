package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.Customer;

public class ReceiptAndPaymentUI extends JFrame {

	private JPanel contentPane;
	private JTextField creditNum;
	private JTextField ccmonth;
	private JTextField ccyear;
	private JTable billTable;
	private Customer cuObject;
	private JComboBox comboCat;
	private JComboBox comboType;
	private JComboBox comboTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer cu = new Customer();
					ReceiptAndPaymentUI frame = new ReceiptAndPaymentUI(cu);
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
	public ReceiptAndPaymentUI(Customer cus) {
		cuObject = cus;
		setTitle("Receipt and Payment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1350, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPleaseProvideYour = new JLabel("Please provide your credit card information:");
		lblPleaseProvideYour.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPleaseProvideYour.setBounds(375, 365, 326, 29);
		contentPane.add(lblPleaseProvideYour);

		JLabel label_1 = new JLabel("Credit card number:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(446, 407, 104, 29);
		contentPane.add(label_1);


		creditNum = new JTextField();
		creditNum.setColumns(10);
		creditNum.setBounds(588, 411, 170, 20);
		contentPane.add(creditNum);

		JLabel label_2 = new JLabel("Expiry date (month, year):");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(446, 447, 129, 29);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("Example");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		label_3.setBounds(506, 478, 56, 14);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("03");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 9));
		label_4.setBounds(612, 478, 20, 14);
		contentPane.add(label_4);

		ccmonth = new JTextField();
		ccmonth.setColumns(10);
		ccmonth.setBounds(588, 445, 61, 29);
		contentPane.add(ccmonth);

		ccyear = new JTextField();
		ccyear.setColumns(10);
		ccyear.setBounds(659, 445, 61, 29);
		contentPane.add(ccyear);

		JLabel label_5 = new JLabel("14");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 9));
		label_5.setBounds(684, 478, 32, 14);
		contentPane.add(label_5);

		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try{	

					long ccnum = Long.parseLong(creditNum.getText());
					int ccMonth = Integer.parseInt(ccmonth.getText());
					int ccYear = Integer.parseInt(ccyear.getText());

					if (creditNum.getText().length()==0) {
						JOptionPane.showMessageDialog(null,
								"Please enter a credit card number.", "Error",
								JOptionPane.ERROR_MESSAGE);
						creditNum.setText("");
						ccmonth.setText("");
						ccyear.setText("");

					} else if (creditNum.getText().length()!=16) {
						JOptionPane.showMessageDialog(null,
								"Please enter a 16-digit credit card number.", "Error",
								JOptionPane.ERROR_MESSAGE);
						creditNum.setText("");
						ccmonth.setText("");
						ccyear.setText("");

					} else if (ccmonth.getText().length()==0) {
						JOptionPane.showMessageDialog(null,
								"Please enter card expiry month.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (ccmonth.getText().length()!=2) {
						JOptionPane.showMessageDialog(null,
								"Please enter a 2-digit month.", "Error",
								JOptionPane.ERROR_MESSAGE);
						ccmonth.setText("");
					} else if (ccyear.getText().length()==0) {
						JOptionPane.showMessageDialog(null,
								"Please enter card expiry year.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (ccyear.getText().length()!=2) {
						JOptionPane.showMessageDialog(null,
								"Please enter card 2-digit year.", "Error",
								JOptionPane.ERROR_MESSAGE);
						ccyear.setText("");
					}else if (ccYear < 13) {
						JOptionPane.showMessageDialog(null,
								"Please enter a year after 2012.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}else if (ccYear == 13 && ccMonth < 8) {
						JOptionPane.showMessageDialog(null,
								"Please enter a month on or after the current month.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}else if(ccMonth < 1 || ccMonth > 12){
						JOptionPane.showMessageDialog(null,		
								"Please enter a month between 01 - 12.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					else {

						int receiptid = cuObject.makeOnlinePurchase(creditNum.getText(), 
								ccmonth.getText()+ccyear.getText());

						creditNum.setText("");
						ccmonth.setText("");
						ccyear.setText("");

						DateFormat df = new SimpleDateFormat("yyyy/MM/dd");  
						String expDate = df.format(cuObject.getExpectedDate());

						ConfirmationUI cui= new ConfirmationUI(cuObject, receiptid, expDate);
						cui.setVisible(true);


						//String confirm = "Your receiptid is: " + receiptid + "\n" ;
						//String expectstring = "The expected delivery date is: " + text + "\n";

						//JOptionPane.showMessageDialog(null,
						//	confirm + expectstring, "Purchase Confirmation",
						//	JOptionPane.INFORMATION_MESSAGE);

					}

				}catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,
							"Message: " + e1.getMessage(), "SQLException",
							JOptionPane.ERROR_MESSAGE);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null,
							"Message: " + e1.getMessage(), "ParseException",
							JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(null,
							"Invalid number entry.", "NumberFormatException",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});
		button.setBounds(747, 439, 121, 40);
		contentPane.add(button);

		JButton btnNewButton = new JButton("Return Home");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home home=new Home();
				home.setVisible(true);
			}
		});
		btnNewButton.setBounds(1164, 618, 110, 36);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(192, 58, 815, 272);
		contentPane.add(scrollPane);

		billTable = new JTable();
		scrollPane.setViewportView(billTable);
		billTable.setModel(cuObject.printBill());

		JLabel lblForReciptNo = new JLabel("");
		lblForReciptNo.setBounds(289, 22, 129, 29);
		contentPane.add(lblForReciptNo);

		JLabel lblDate = new JLabel("Your Bill:");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDate.setBounds(192, 17, 199, 29);
		contentPane.add(lblDate);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(753, 25, 67, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Total: $");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(785, 339, 67, 29);
		contentPane.add(lblNewLabel_2);

		JLabel lblTotal = new JLabel("");
		String total = String.format("%.2f", cuObject.totalBill());
		lblTotal.setText(total);
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotal.setBounds(862, 339, 121, 29);
		contentPane.add(lblTotal);
		
		String[] tableStrings = { "Item", "LeadSinger", "HasSong", "Purchase", "PurchaseItem", 
				"Return", "ReturnItem", "Customer" };		
		comboTable = new JComboBox(tableStrings);
		comboTable.setBounds(30, 625, 157, 23);
		contentPane.add(comboTable);

		JButton btnViewTable = new JButton("View Table");
		btnViewTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel model = cuObject.getTable((String)comboTable.getSelectedItem());
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
	}

}
