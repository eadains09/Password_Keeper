import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class PassGeneration {

	private JPanel innerPanel, innerPanel1, innerPanel2, outerPanel, row1, row2, row3, row4, row5;
	private JTextField passPrint, box1, box2, box3, box4;
	private JButton savePass, reGen;
	private boolean addRow1Warning = false, addRow2Warning=false, addRow3Warning=false, addRow4Warning=false;
	private Font font = new Font (Font.SERIF, 10, 20);
	private Font headerFont = new Font(Font.SERIF, Font.BOLD, 22);
	private String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()?";
	private char[] possValues = values.toCharArray();
	private Cryption crypto = new Cryption();
	
	PassGeneration()
	{
		//innerPanel 1
		innerPanel1 = new JPanel();
		innerPanel1.setLayout(new BoxLayout(innerPanel1, BoxLayout.LINE_AXIS));
		innerPanel1.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
		innerPanel1.setBackground(new Color(255, 255, 250));

		JLabel pass = new JLabel("Password:");
		pass.setFont(font);

		passPrint = new JTextField();
		passPrint.setMaximumSize(new Dimension(200, 50));
		passPrint.setFont(font);
		
		//innerPanel 1 Layout
		innerPanel1.add(pass);
		innerPanel1.add(Box.createRigidArea(new Dimension(10,0)));
		pass.setHorizontalAlignment(SwingConstants.CENTER);
		innerPanel1.add(passPrint);
		passPrint.setHorizontalAlignment(SwingConstants.CENTER);
		
		//innerPanel 2
		innerPanel2 = new JPanel();
		innerPanel2.setLayout(new BoxLayout(innerPanel2, BoxLayout.LINE_AXIS));
		innerPanel2.setBackground(new Color(255, 255, 250));
		
		reGen = new JButton("Regenerate Password");
		reGen.setFont(font);
		reGen.addActionListener(new ButtonListener1());
		savePass = new JButton("Save Password");
		savePass.setFont(font);
		savePass.addActionListener(new ButtonListener2());
		
		//innerPanel 2 layout
		innerPanel2.add(reGen);
		innerPanel2.add(savePass);
		
		//innerPanel
		innerPanel = new JPanel();
		innerPanel.setBackground(new Color(255, 255, 250));
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));
		
		innerPanel.add(innerPanel1);
		innerPanel.add(Box.createRigidArea(new Dimension(10,10)));
		innerPanel.add(innerPanel2);

		//outerPanel
		outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		outerPanel.setBackground(new Color(255, 255, 250));

		JLabel header = new JLabel("Generate a New Password");
		header.setFont(headerFont);
		
		//outerPanel Layout
		outerPanel.add(header, BorderLayout.NORTH);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		outerPanel.add(innerPanel, BorderLayout.CENTER);
		
		outerPanel.revalidate();
	}
	
	class ButtonListener1 implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			generatePassword();
		}
	}
	
	class ButtonListener2 implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			savePassPanel();
		}
	}
	
	private void savePassPanel()
	{
		String password = passPrint.getText();
		innerPanel.removeAll();
		innerPanel.repaint();
		Dimension boxSize = new Dimension(200, 50);
		
		row1 = new JPanel();
		row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
		row1.setAlignmentX(1);
		row1.setBackground(new Color(255, 255, 250));
		JLabel box1Label = new JLabel ("Name this entry");
		box1Label.setFont(font);
		box1 = new JTextField();
		box1.setFont(font);
		box1.setMaximumSize(boxSize);
		row1.add(box1Label);
		row1.add(Box.createRigidArea(new Dimension(10, 0)));
		row1.add(box1);
		
		row2 = new JPanel();
		row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
		row2.setAlignmentX(1);
		row2.setBackground(new Color(255, 255, 250));
		JLabel box2Label = new JLabel ("Enter the username for your entry");
		box2Label.setFont(font);
		box2 = new JTextField();
		box2.setFont(font);
		box2.setMaximumSize(boxSize);
		row2.add(box2Label);
		row2.add(Box.createRigidArea(new Dimension(10,0)));
		row2.add(box2);
		
		row3 = new JPanel();
		row3.setLayout(new BoxLayout(row3, BoxLayout.LINE_AXIS));
		row3.setAlignmentX(1);
		row3.setBackground(new Color(255, 255, 250));
		JLabel box3Label = new JLabel("Password");
		box3Label.setFont(font);
		box3 = new JTextField();
		box3.setFont(font);
		box3.setText(password);
		box3.setMaximumSize(boxSize);
		row3.add(box3Label);
		row3.add(Box.createRigidArea(new Dimension(10,0)));
		row3.add(box3);
		
		row4 = new JPanel();
		row4.setLayout(new BoxLayout(row4, BoxLayout.LINE_AXIS));
		row4.setAlignmentX(1);
		row4.setBackground(new Color(255, 255, 250));
		JLabel box4Label = new JLabel("Enter the URL for entry");
		box4Label.setFont(font);
		box4 = new JTextField();
		box4.setFont(font);
		box4.setMaximumSize(boxSize);
		row4.add(box4Label);
		row4.add(Box.createRigidArea(new Dimension(10,0)));
		row4.add(box4);
		
		row5 = new JPanel();
		row5.setLayout(new BoxLayout(row5, BoxLayout.LINE_AXIS));
		row5.setBackground(new Color(255, 255, 250));
		JButton save = new JButton("Save Entry");
		save.setFont(font);
		save.addActionListener(new ButtonListener3());
		row5.add(save);
		row5.add(Box.createRigidArea(new Dimension(0, 100)));

		//innerPanel
		innerPanel.add(row1);
		innerPanel.add(row2);
		innerPanel.add(row3);
		innerPanel.add(row4);
		innerPanel.add(row5);
		innerPanel.revalidate();
	}
	

	class ButtonListener3 implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			savePassword();
		}
	}
	
	public void generatePassword()
	{
		int length = 16;
		int ran = 0;
		char[]password = new char[length];
		Random r = new Random();
		
		for (int i=0; i<length; i++)
		{
			ran = r.nextInt(possValues.length);
			password[i] = possValues[ran];
		}
		
		String pass = new String(password);
		passPrint.setText(pass);
	}
	
	private void savePassword()
	{
		String name = null, username=null, password=null, URL=null, outputInfo=null;
		JLabel warning=null;
		
		if(box1.getText().isEmpty())
		{
			warning = new JLabel("Name is a required field.");
			if (!addRow1Warning)
			{
				row1.add(warning);
				addRow1Warning = true;
			}
		}else{
			name = box1.getText();
		}
		if (box2.getText().isEmpty())
		{
			warning = new JLabel("Username is a required field.");
			if (!addRow2Warning)
			{
				row2.add(warning);
				addRow2Warning = true;
			}
		}else
		{
			username = box2.getText();
		}
		
		if (box3.getText().isEmpty())
		{
			warning = new JLabel("Password is a required field.");
			if (!addRow3Warning)
			{
				row3.add(warning);
				addRow3Warning = true;
			}
		}else
		{
			password = box3.getText();
		}
		
		if (box4.getText().isEmpty())
		{
			warning = new JLabel("URL is a required field.");
			if (!addRow4Warning)
			{
				row4.add(warning);
				addRow4Warning = true;
			}
		}else
		{
			URL = box4.getText();
		}
		
		innerPanel.revalidate();
		
		if (name!=null && username!=null && password!=null && URL!=null)
		{
			outputInfo = "\n" + name + " " + username + " " + password + " " + URL;
			String fileSoFar = crypto.readFromFile();
			fileSoFar = fileSoFar + outputInfo;
			crypto.encryptString(fileSoFar);
			
			//clear boxes for new entry
			box1.setText(null);
			box2.setText(null);
			box3.setText(null);
			box4.setText(null);
		}
	}
	
	public JPanel returnPanel()
	{
		return outerPanel;
	}
}
