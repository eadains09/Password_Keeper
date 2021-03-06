import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class Driver extends JPanel{
	private JButton generate, retrieve, delete;
	private JTextField programPass;
	private JPanel outerPanel, innerPanel, row1, row2;
	private final String enterHash = "31435008693ce6976f45dedc5532e2c1";
	private Cryption crypto = new Cryption();
	//Fonts
	private Font headerFont = new Font(Font.SERIF, Font.BOLD, 25);
	private Font buttonFont = new Font (Font.SERIF, 10, 20);
	private	Font bodyFont = new Font (Font.SERIF, 10, 20);
	
	Driver()
	{	
		//Construct panels
		setBackground(Color.lightGray);
		outerPanel = new JPanel();
		add(outerPanel);
		outerPanel.setLayout(new BorderLayout());
		outerPanel.setPreferredSize(new Dimension(800, 800));
		innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));
		innerPanel.setBackground(new Color(255, 255, 250));
		JPanel navPanel = new JPanel();
		navPanel.setLayout(new GridLayout(5, 1));
		
		
		
		//Build outerPanel
		JLabel header = new JLabel("What would you like to do?");

		outerPanel.add(header, BorderLayout.NORTH);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(headerFont);
		outerPanel.add(innerPanel, BorderLayout.CENTER);
		outerPanel.add(navPanel, BorderLayout.WEST);
		
		//Build navPanel
		generate = new JButton("Generate new password");
		generate.addActionListener(new PassGenButtonListener());
		generate.setFont(buttonFont);
		retrieve = new JButton("Retrieve a password");
		retrieve.addActionListener(new PassRetButtonListener());
		retrieve.setFont(buttonFont);
		delete = new JButton("Delete a password");
		delete.addActionListener(new PassDelButtonListener());
		delete.setFont(buttonFont);
		
		
		navPanel.add(generate);
		navPanel.add(retrieve);
		navPanel.add(delete);
		
		//Build innerPanel
		JLabel unlock = new JLabel("Enter your password.");
		unlock.setBackground(new Color(255, 255, 250));
		unlock.setFont(bodyFont);
		programPass = new JTextField();
		programPass.setMaximumSize(new Dimension(200, 50));
		programPass.setFont(bodyFont);
		JButton enter = new JButton("Enter");
		enter.setFont(bodyFont);
		enter.addActionListener(new EnterButtonListener());
		
		row1 = new JPanel();
		row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
		row1.setBackground(new Color(255, 255, 250));
		row1.add(unlock);
		row1.add(Box.createRigidArea(new Dimension(10,0)));
		row1.add(programPass);
		
		row2 = new JPanel();
		row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
		row2.setBackground(new Color(255, 255, 250));
		row2.add(enter);
		row2.add(Box.createRigidArea(new Dimension(0, 100)));

		innerPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
		innerPanel.add(row1);
		innerPanel.add(row2);
		generate.setEnabled(false);
		retrieve.setEnabled(false);
		delete.setEnabled(false);
	}
	
	class EnterButtonListener implements ActionListener
	{
	
		
		public void actionPerformed(ActionEvent arg0)
		{
			String checkPass = programPass.getText();
			String checkPassHash = crypto.toMD5(checkPass);
		
			if (checkPassHash.equals(enterHash))
			{
				generate.setEnabled(true);
				retrieve.setEnabled(true);
				delete.setEnabled(true);
				innerPanel.revalidate();
				crypto.toSHA(checkPass);
			}
			else{
				JLabel incorrect = new JLabel("Incorrect password entered. Try again.");
				incorrect.setFont(bodyFont);
				innerPanel.add(incorrect);
				innerPanel.revalidate();
			}
			
		}
	}
	
	class PassGenButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			innerPanel.removeAll();
			PassGeneration pg = new PassGeneration();
			innerPanel = pg.returnPanel();
			outerPanel.add(innerPanel, BorderLayout.CENTER);
			revalidate();
			pg.generatePassword();
		}	
	}
	
	class PassRetButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			innerPanel.removeAll();
			PassRetrieval pr = new PassRetrieval();
			innerPanel = pr.returnPanel();
			outerPanel.add(innerPanel, BorderLayout.CENTER);
			revalidate();
		}
	}
	
	class PassDelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			innerPanel.removeAll();
			PassDeletion pd = new PassDeletion();
			innerPanel = pd.returnPanel();
			outerPanel.add(innerPanel, BorderLayout.CENTER);
			revalidate();
		}
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Password Keeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Driver panel = new Driver();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
