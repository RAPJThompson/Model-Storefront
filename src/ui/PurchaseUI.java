package ui;

import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Customer;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import exception.ItemException;
import exception.QuantityException;

public class PurchaseUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblsearchMain;
	private JLabel lblsearchDesc;
	private JButton butSearch;
	private JLabel lblYourVirtualShopping;
	private JButton butCheckOut;
	private JTable search_table;
	private JScrollPane scrollPane;
	private JTable table;
	private JScrollPane scrollPane_1;
	private JButton checkout;
	private JTextField searchTitle;
	private JTextField searchSinger;
	private Customer cuObject;
	private JTextField selectUpc;
	private JTextField selectQuant;
	private JComboBox comboCat;
	private JComboBox comboType;
	private JComboBox comboTable;
	private JLabel lblPleaseEnterThe;
	private JLabel lblUpc;
	private JLabel lblQuantity;
	private JLabel lblWelcomeCustomer;
	private JLabel lblIsLoggedIn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer cu = new Customer();
					PurchaseUI frame = new PurchaseUI(cu);
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
	public PurchaseUI(Customer cu) {
		cuObject =cu;
		setTitle("Purchase");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Current Total: $");
		lblNewLabel.setBounds(836, 529, 104, 20);
		contentPane.add(lblNewLabel);

		lblsearchMain = new JLabel("Search item:");
		lblsearchMain.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblsearchMain.setBounds(20, 57, 104, 20);
		contentPane.add(lblsearchMain);

		lblsearchDesc = new JLabel("Please enter the category, title, and/or leading singer of the desired item:");
		lblsearchDesc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblsearchDesc.setBounds(20, 70, 439, 32);
		contentPane.add(lblsearchDesc);

		String[] catStrings = { "", "classical", "country", "instrumental", "new age", "pop", "rap", "rock" };
		comboCat = new JComboBox(catStrings);
		comboCat.setBounds(206, 114, 157, 23);
		contentPane.add(comboCat);
		
		/*
		searchCategory = new JTextField();
		searchCategory.setColumns(10);
		searchCategory.setBounds(208, 83, 156, 20);
		contentPane.add(searchCategory);
*/
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 222, 582, 284);
		contentPane.add(scrollPane);

		search_table = new JTable();
		scrollPane.setViewportView(search_table);
		search_table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"UPC", "NAME", "TITLE", "TYPE", "CATEGORY", "COMPANY", "YEAR", "UNIT PRICE($)", "STOCK"
			}
		));

		butSearch = new JButton("Search");
		butSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				String cat = (String)comboCat.getSelectedItem();
				if (cat.length()==0 &
						searchTitle.getText().length()==0 &
						searchSinger.getText().length()==0) {

					JOptionPane.showMessageDialog(null,
							"To search for an item, please select a category or title or lead singer name!", "Error",
							JOptionPane.ERROR_MESSAGE);}
				else {
					try {
						DefaultTableModel model = cuObject.searchItem(cat, searchTitle.getText(), 
								searchSinger.getText());
						search_table.setModel(model);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e1.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);
					} catch (ItemException e2) {
						JOptionPane.showMessageDialog(null,
								"Sorry! No items were found. Please modify your search.", "ItemException",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});      
		butSearch.setBounds(388, 116, 89, 23);
		contentPane.add(butSearch);

		butCheckOut = new JButton("Checkout");
		butCheckOut.setBounds(1239, 253, 121, 23);
		contentPane.add(butCheckOut);

		lblYourVirtualShopping = new JLabel("Your virtual shopping cart contains:");
		lblYourVirtualShopping.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblYourVirtualShopping.setBounds(631, 175, 334, 26);
		contentPane.add(lblYourVirtualShopping);

		JButton btnNewButton = new JButton("Return Home");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Home home=new Home();
				home.setVisible(true);
			}
		});
		btnNewButton.setBounds(1064, 618, 110, 36);
		contentPane.add(btnNewButton);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(631, 222, 526, 284);
		contentPane.add(scrollPane_1);

		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setModel(new DefaultTableModel(
				new Object[][] {
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
						{null, null, null, null, null, null},
				},
				new String[] {
						"UPC", "TITLE", "LEAD SINGER", "CATEGORY", "QUANTITY", "UNIT PRICE($)"
				}
				));

		final JLabel currentTotal = new JLabel("0.00");
		currentTotal.setBounds(952, 531, 61, 16);
		contentPane.add(currentTotal);

		JButton butAdd = new JButton("Add Item");
		butAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)

			{

				if (selectUpc.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a UPC.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (selectUpc.getText().length()!=12) {
					JOptionPane.showMessageDialog(null,
							"Please enter a 12-digit UPC.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (selectQuant.getText().length()==0) {
					JOptionPane.showMessageDialog(null,
							"Please enter a quantity.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {

						int quantAdded = cuObject.selectItem(selectUpc.getText(), selectQuant.getText());

						JOptionPane.showMessageDialog(null,
								quantAdded + " of " + "your item has been added to the shopping cart!", "Information",
								JOptionPane.INFORMATION_MESSAGE);

						DefaultTableModel model = cuObject.printShoppingCart();
						//JOptionPane.showMessageDialog(null,new JScrollPane(new JTable(model)));

						table.setModel(model);
						String total = String.format("%.2f", cuObject.totalBill());
						currentTotal.setText(total);
						selectUpc.setText("");
						selectQuant.setText("");

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e1.getMessage(), "SQLException",
								JOptionPane.ERROR_MESSAGE);
					} catch (ParseException e2) {
						JOptionPane.showMessageDialog(null,
								"Message: " + e2.getMessage(), "ParseException",
								JOptionPane.ERROR_MESSAGE);
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null,
								"Please enter a valid quantity.", "NumberFormatException",
								JOptionPane.ERROR_MESSAGE);
					} catch (QuantityException e1) {
						JOptionPane.showMessageDialog(null,
								e1.getMessage(), "QuantityException",
								JOptionPane.ERROR_MESSAGE);
					} catch (ItemException e1) {
						JOptionPane.showMessageDialog(null,
								e1.getMessage(), "ItemException",
								JOptionPane.ERROR_MESSAGE);
					} 
				}
			}
		});     
		butAdd.setBounds(407, 567, 89, 32);
		contentPane.add(butAdd);

		checkout = new JButton("Checkout");
		checkout.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent arg0) {

				if(cuObject.getShoppingCart().isEmpty()){
					JOptionPane.showMessageDialog(null,
							"Your shopping cart is empty! Please add items before checkout.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
					ReceiptAndPaymentUI bill= new ReceiptAndPaymentUI(cuObject);
					bill.setVisible(true);
				}

			}
		});
		checkout.setBounds(1036, 523, 121, 26);
		contentPane.add(checkout);

		searchTitle = new JTextField();
		searchTitle.setColumns(10);
		searchTitle.setBounds(207, 148, 156, 20);
		contentPane.add(searchTitle);

		searchSinger = new JTextField();
		searchSinger.setColumns(10);
		searchSinger.setBounds(207, 180, 156, 20);
		contentPane.add(searchSinger);

		JLabel lblSearchCat = new JLabel("Category");
		lblSearchCat.setBounds(65, 116, 61, 16);
		contentPane.add(lblSearchCat);

		JLabel lblSearchTitle = new JLabel("Title");
		lblSearchTitle.setBounds(65, 150, 61, 16);
		contentPane.add(lblSearchTitle);

		JLabel lblSearchSinger = new JLabel("Lead Singer");
		lblSearchSinger.setBounds(65, 182, 130, 16);
		contentPane.add(lblSearchSinger);

		selectUpc = new JTextField();
		selectUpc.setBounds(242, 554, 134, 28);
		contentPane.add(selectUpc);
		selectUpc.setColumns(10);

		selectQuant = new JTextField();
		selectQuant.setColumns(10);
		selectQuant.setBounds(242, 584, 134, 28);
		contentPane.add(selectQuant);

		new JButton("Checkout");

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
		
		JLabel lblAddItem = new JLabel("Add item:");
		lblAddItem.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddItem.setBounds(30, 522, 104, 20);
		contentPane.add(lblAddItem);
		
		lblPleaseEnterThe = new JLabel("Please enter the upc and quantity of the item you wish to add to your cart:");
		lblPleaseEnterThe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPleaseEnterThe.setBounds(174, 518, 439, 32);
		contentPane.add(lblPleaseEnterThe);
		
		lblUpc = new JLabel("UPC");
		lblUpc.setBounds(174, 558, 61, 16);
		contentPane.add(lblUpc);
		
		lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(174, 590, 61, 16);
		contentPane.add(lblQuantity);
		
		lblWelcomeCustomer = new JLabel("Welcome, Customer!");
		lblWelcomeCustomer.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblWelcomeCustomer.setBounds(20, 17, 334, 26);
		contentPane.add(lblWelcomeCustomer);
		
		lblIsLoggedIn = new JLabel("is logged In!");
		lblIsLoggedIn.setText(cuObject.getCid() + " is Logged In!");
		lblIsLoggedIn.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblIsLoggedIn.setBounds(964, 17, 193, 26);
		contentPane.add(lblIsLoggedIn);
	}
}
