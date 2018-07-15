package Client;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;

public class RegisterGUI extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnRegister;
	private JTextField usernameField, passwordField, screenNameField, confirmPasswordField;
	private JLabel errorLabel;

	public RegisterGUI() {
		setTitle("Register");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(223, 135, 125, 20);
		contentPane.add(usernameField);
		usernameField.setHorizontalAlignment(SwingConstants.LEFT);
		usernameField.setColumns(10);

		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setBounds(133, 138, 80, 14);
		contentPane.add(lblUsername);
		lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblNewLabel = new JLabel("Password:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(143, 171, 70, 14);
		contentPane.add(lblNewLabel);

		JLabel lblScreenName = new JLabel("Screen Name:");
		lblScreenName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblScreenName.setBounds(123, 105, 90, 14);
		contentPane.add(lblScreenName);

		JLabel lblRegister = new JLabel("Register");
		lblRegister.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegister.setBounds(179, 45, 73, 20);
		contentPane.add(lblRegister);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(179, 268, 93, 20);
		btnRegister.addActionListener(this);
		contentPane.add(btnRegister);

		passwordField = new JTextField();
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setColumns(10);
		passwordField.setBounds(223, 168, 125, 20);
		contentPane.add(passwordField);

		screenNameField = new JTextField();
		screenNameField.setHorizontalAlignment(SwingConstants.LEFT);
		screenNameField.setColumns(10);
		screenNameField.setBounds(223, 102, 125, 20);
		contentPane.add(screenNameField);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPassword.setBounds(68, 202, 145, 14);
		contentPane.add(lblConfirmPassword);

		confirmPasswordField = new JTextField();
		confirmPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
		confirmPasswordField.setColumns(10);
		confirmPasswordField.setBounds(223, 199, 125, 20);
		contentPane.add(confirmPasswordField);

		errorLabel = new JLabel("");
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(123, 243, 225, 14);
		contentPane.add(errorLabel);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnRegister) {
			if(usernameField.getText().isEmpty()||screenNameField.getText().isEmpty()||passwordField.getText().isEmpty()||confirmPasswordField.getText().isEmpty()) {
				errorLabel.setText("Incomplete fields");
			}
			if(usernameField.getText().contains("_")) {
				errorLabel.setText("Invalid \"_\" character entered.");
			}
			else {
				if(!passwordField.getText().equals(confirmPasswordField.getText())) {
					errorLabel.setText("Passwords do not match");
				}
				else {
					Message m = new Message("REGISTER",screenNameField.getText(),usernameField.getText(),confirmPasswordField.getText());
					Communication.send(m);
				}
			}
		}
	}
}