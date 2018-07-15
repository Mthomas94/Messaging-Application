package Client;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.ComponentOrientation;
import java.awt.event.*;

public class files extends JFrame {

	private JFileChooser fc;

	public files(String chatName, ArrayList<String>users) {
		setBounds(100, 100, 512, 325);
		fc = new JFileChooser(FileSystemView.getFileSystemView());
		fc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File selectedFile = fc.getSelectedFile();
				if(selectedFile.length()>50) {
					JOptionPane.showMessageDialog(null, "File too large. Maximum size:", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					byte[] f = getFile(selectedFile);
					Communication.send(new Message("FILE", MainActivityGUI.getScreenName() ,users, f, selectedFile.getName(), chatName));
					chats.updatechat(chatName, MainActivityGUI.getScreenName(), "File " +selectedFile.getName()+ " sent.",ComponentOrientation.RIGHT_TO_LEFT);
					dispose();
				}
			}
		});
		fc.setDialogTitle("Send a file");
		setContentPane(fc);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public byte[] getFile(File file) {
		Path f = file.toPath();
		try {
			return Files.readAllBytes(f);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}