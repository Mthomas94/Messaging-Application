package Client;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class LoginGUI implements ActionListener{

	static JFrame frmLogin;
	private JTextField usernameField;
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginBtn = new JButton("Login");
	private String salt = BCrypt.gensalt();
	private JButton registerBtn = new JButton("Register");
	private static JLabel errorLabel;
	
	public LoginGUI() {
		initialize();
		frmLogin.setVisible(true);
	}

	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.getContentPane().setBackground(new Color(255, 250, 250));
		frmLogin.setBounds(100, 100, 521, 482);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		loginBtn.addActionListener(this);
		loginBtn.setBounds(275, 316, 89, 23);
		frmLogin.getContentPane().add(loginBtn);

		registerBtn.setBounds(125, 316, 89, 23);
		registerBtn.addActionListener(this);
		frmLogin.getContentPane().add(registerBtn);
		
		JLabel lblUsername = new JLabel("USERNAME:");
		lblUsername.setBounds(124, 187, 76, 14);
		frmLogin.getContentPane().add(lblUsername);
		
		usernameField = new JTextField();
		usernameField.setBounds(124, 202, 240, 28);
		frmLogin.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("PASSWORD:");
		lblNewLabel.setBounds(124, 241, 76, 22);
		frmLogin.getContentPane().add(lblNewLabel);
		
		
		passwordField.setBounds(124, 261, 240, 28);
		frmLogin.getContentPane().add(passwordField);
		
		JPanel panel = new JPanel();
		panel.setBounds(107, 31, 270, 129);
		frmLogin.getContentPane().add(panel);
		
		errorLabel = new JLabel("");
		errorLabel.setBounds(218, 350, 46, 14);
		frmLogin.getContentPane().add(errorLabel);
		frmLogin.setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		frmLogin.setJMenuBar(menuBar);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==loginBtn) {
			Communication.send(new Message("LOGIN",usernameField.getText(),BCrypt.hashpw(passwordField.getText(), salt)));
		}
		if(e.getSource()==registerBtn) {
			RegisterGUI r = new RegisterGUI();
		}
		
	}
}
