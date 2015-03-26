package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Customer;

import java.awt.Scrollbar;

import javax.swing.table.DefaultTableModel;

import java.awt.Choice;

import javax.swing.JComboBox;

public class CustomerLoginUI extends JFrame {

	private JPanel contentPane;
	private Customer cuObject;
	private JTextField cName;
	private JTextField cAddr;
	private JTextField cphone;
	private JTextField usrname;
	private JTextField cpw;
	private JPasswordField passwordField;
	private JTextField textField;
	private JLabel labelLogin;
	private JLabel labelRegisterMe;
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
					CustomerLoginUI frame = new CustomerLoginUI();
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
	public CustomerLoginUI() {
		cuObject = new Customer();
		setTitle("Customer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUserId = new JLabel("User id");
		lblUserId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUserId.setBounds(80, 182, 54, 41);
		contentPane.add(lblUserId);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(80, 257, 54, 41);
		contentPane.add(lblPassword);

		//NOTE: for this button will need to action to be performed either error msg outs in the Jlabel below or goes to next JFrame
		JButton btnLogIn_1 = new JButton("Log In");
		btnLogIn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (textField.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a username.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (textField.getText().length()!=4) {
					JOptionPane.showMessageDialog(null,
							"Please enter a 4-character username.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (String.valueOf(passwordField.getPassword()).length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a password.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (String.valueOf(passwordField.getPassword()).length()>12) {
					JOptionPane.showMessageDialog(null,
							"Please enter a password between 1 - 12 characters.", "Error",
							JOptionPane.ERROR_MESSAGE);}
				else {
					try{
						int regCust = cuObject.logIn(textField.getText(), String.valueOf(passwordField.getPassword()));

						if (regCust == -1) {
							JOptionPane.showMessageDialog(null,
									"The customer id and password combination is not valid. Please try again.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else
						{ JOptionPane.showMessageDialog(null,
								"Welcome back " + cuObject.getCid()+"!", "Message",
								JOptionPane.PLAIN_MESSAGE);

						PurchaseUI pui= new PurchaseUI(cuObject);
						pui.setVisible(true);}

					}catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e1.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);}
				}
			}
		});
		btnLogIn_1.setBounds(185, 340, 110, 28);
		contentPane.add(btnLogIn_1);

		labelLogin= new JLabel("");
		labelLogin.setBounds(10, 404, 437, 41);
		contentPane.add(labelLogin);

		JLabel lblLogIn = new JLabel("If already a member, please log in:");
		lblLogIn.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLogIn.setBounds(80, 95, 267, 26);
		contentPane.add(lblLogIn);

		JLabel lblIfNotA = new JLabel("If not a member, please register:");
		lblIfNotA.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIfNotA.setBounds(644, 95, 297, 26);
		contentPane.add(lblIfNotA);

		JLabel label_1 = new JLabel("Name");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(644, 188, 54, 28);
		contentPane.add(label_1);

		cName = new JTextField();
		cName.setColumns(10);
		cName.setBounds(731, 197, 277, 20);
		contentPane.add(cName);

		JLabel label_2 = new JLabel("Address");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(644, 266, 59, 23);
		contentPane.add(label_2);

		cAddr = new JTextField();
		cAddr.setColumns(10);
		cAddr.setBounds(731, 267, 277, 20);
		contentPane.add(cAddr);

		JLabel lblPhoneNumber = new JLabel("Phone number");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPhoneNumber.setBounds(644, 340, 70, 28);
		contentPane.add(lblPhoneNumber);

		cphone = new JTextField();
		cphone.setColumns(10);
		cphone.setBounds(731, 344, 277, 20);
		contentPane.add(cphone);

		JLabel label_4 = new JLabel("Pick username");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setBounds(644, 404, 104, 41);
		contentPane.add(label_4);

		usrname = new JTextField();
		usrname.setColumns(10);
		usrname.setBounds(731, 414, 277, 20);
		contentPane.add(usrname);

		cpw = new JTextField();
		cpw.setColumns(10);
		cpw.setBounds(731, 482, 277, 20);
		contentPane.add(cpw);

		JLabel label_5 = new JLabel("Pick password");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_5.setBounds(644, 472, 75, 41);
		contentPane.add(label_5);

		//NOTE: for this button either prints error msg or goes to next msg so two action msgs
		//or we can add a next button that leads to next frame if condition is fulfilled? 
		JButton btnRegisterMe = new JButton("Register Me");
		btnRegisterMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
				Matcher matcher = pattern.matcher(cphone.getText());
				
				if (usrname.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a username.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (usrname.getText().length()!=4) {
					JOptionPane.showMessageDialog(null,
							"Please enter a 4-character username.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (cpw.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a password.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (cpw.getText().length() > 12) {
					JOptionPane.showMessageDialog(null,
							"Please enter a password between 1 - 12 characters.", "Error",
							JOptionPane.ERROR_MESSAGE); 
				} else if (cphone.getText().length() > 0 && !(matcher.matches())){
						JOptionPane.showMessageDialog(null,
								"Please enter a valid phone number xxx-xxx-xxxx", "Error",
								JOptionPane.ERROR_MESSAGE); 
				}
				  else {

					try{
						int regCust = cuObject.registerCustomer(usrname.getText(), 
								cpw.getText(),
								cName.getText(),
								cAddr.getText(),
								cphone.getText());
						if (regCust == -1) {
							JOptionPane.showMessageDialog(null,
									"Unavailable username! Please enter a different username.", "Error",
									JOptionPane.ERROR_MESSAGE);
							usrname.setText("");
							cpw.setText("");

						} else {JOptionPane.showMessageDialog(null,
								"Registration complete! Log in now to begin shopping!", "Message",
								JOptionPane.PLAIN_MESSAGE);
						usrname.setText("");
						cpw.setText("");
						cName.setText("");
						cAddr.setText("");
						cphone.setText("");}

					}catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e1.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);}
				}
			}
		});
		btnRegisterMe.setBounds(799, 539, 134, 28);
		contentPane.add(btnRegisterMe);

		labelRegisterMe= new JLabel ("");
		labelRegisterMe.setBounds (618, 578, 527, 36);
		contentPane.add(labelRegisterMe);

		passwordField = new JPasswordField();
		passwordField.setBounds(144, 267, 202, 23);
		contentPane.add(passwordField);

		JLabel lblNewLabel = new JLabel("Welcome, Customer!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(492, 11, 190, 36);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(144, 192, 203, 20);
		contentPane.add(textField);

		JButton btnNewButton = new JButton("Return Home");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home home=new Home();
				home.setVisible(true);
			}
		});
		btnNewButton.setBounds(1064, 618, 110, 36);
		contentPane.add(btnNewButton);

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
