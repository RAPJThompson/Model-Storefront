package ui;

import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

public class Home extends JFrame {

	private JPanel contentPane;
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
					Home frame = new Home();
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
	public Home() {
		setTitle("Home");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWelcomePleaseSelect = new JLabel("Welcome to the AMS Store!");
		lblWelcomePleaseSelect.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblWelcomePleaseSelect.setBounds(437, 28, 282, 55);
		contentPane.add(lblWelcomePleaseSelect);

		JButton btnNewButton = new JButton("Clerk");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClerkUI cl=new ClerkUI();
				cl.setVisible(true);
			}
			/*
			public void windowClosing (WindowEvent e) {
				if (Home != null)
					Home.dispose();
				if (Clerk !=null)
					Clerk.dispose();
			}
			 */
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(437, 200, 282, 49);
		contentPane.add(btnNewButton);

		JButton btnCustomer = new JButton("Customer");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerLoginUI cus=new CustomerLoginUI();
				cus.setVisible(true);
			}
		});
		btnCustomer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCustomer.setBounds(437, 353, 282, 49);
		contentPane.add(btnCustomer);

		JButton btnManager = new JButton("Manager");
		btnManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerUI m=new ManagerUI();
				m.setVisible(true);
			}
		});
		btnManager.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnManager.setBounds(437, 513, 282, 49);
		contentPane.add(btnManager);

		JLabel lblPleaseSelectA = new JLabel("Please select a view:");
		lblPleaseSelectA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPleaseSelectA.setBounds(482, 94, 195, 55);
		contentPane.add(lblPleaseSelectA);
	}

}
