package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import model.Customer;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;

import exception.ItemException;
import exception.QuantityException;
import exception.ReceiptIdException;
import exception.ReturnException;

public class ConfirmationUI extends JFrame {

	private JPanel contentPane;
	private Customer cuObject;
	private int receiptid;
	private String expDate;
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
					int rid = 0;
					String st ="";
					ConfirmationUI frame = new ConfirmationUI(cu,rid,st);
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
	public ConfirmationUI(Customer cus, int rid, String st) {
		setTitle("Confirmation");
		cuObject = cus;
		receiptid = rid;
		expDate = st;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1350, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblreceptid = new JLabel("");
		lblreceptid.setText(Integer.toString(receiptid));
		lblreceptid.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblreceptid.setBounds(688, 235, 499, 56);
		contentPane.add(lblreceptid);

		JButton btnNewButton = new JButton("Make another purchase");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				PurchaseUI pui= new PurchaseUI(cuObject);
				pui.setVisible(true);

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(398, 395, 199, 46);
		contentPane.add(btnNewButton);

		JButton btnGoToHome = new JButton("Return home");
		btnGoToHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Home home=new Home();
				home.setVisible(true);
			}
		});
		btnGoToHome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnGoToHome.setBounds(688, 395, 199, 46);
		contentPane.add(btnGoToHome);

		JLabel lblexpdate = new JLabel("The expected delivery date is: ");
		lblexpdate.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblexpdate.setBounds(394, 310, 257, 42);
		contentPane.add(lblexpdate);

		JLabel expdatetxt = new JLabel("\"\"");
		expdatetxt.setText(expDate);
		expdatetxt.setFont(new Font("Tahoma", Font.BOLD, 16));
		expdatetxt.setBounds(688, 310, 257, 42);
		contentPane.add(expdatetxt);

		JLabel lblRid = new JLabel("Your receipt ID is: ");
		lblRid.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRid.setBounds(394, 242, 257, 42);
		contentPane.add(lblRid);

		JLabel lblCongratulationsYourPurchase = new JLabel("Congratulations! Your purchase is complete!");
		lblCongratulationsYourPurchase.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCongratulationsYourPurchase.setBounds(474, 149, 417, 42);
		contentPane.add(lblCongratulationsYourPurchase);
		
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
