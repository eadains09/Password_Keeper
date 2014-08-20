import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class PassRetrieval {
	private JPanel innerPanel, outerPanel, row1, row2, row3;
	private JTextField URLBox, userBox;
	private JTextArea results;
	private ArrayList<PassEntry> entries;
	private boolean addRow1Warning = false, addRow2Warning=false;
	private Cryption crypto = new Cryption();
	private Font font = new Font (Font.SERIF, 10, 20);
	private Font headerFont = new Font(Font.SERIF, Font.BOLD, 22);
	private Dimension boxSize = new Dimension(200, 50);
	
	PassRetrieval()
	{
		//row 1
		row1 = new JPanel();
		row1.setLayout(new BoxLayout(row1, BoxLayout.LINE_AXIS));
		row1.setBackground(new Color(255, 255, 250));
		
		JLabel desiredURL = new JLabel("Enter the URL for the password:");
		desiredURL.setFont(font);
		URLBox = new JTextField();
		URLBox.setMaximumSize(boxSize);
		URLBox.setFont(font);
		
		row1.add(desiredURL);
		row1.add(Box.createRigidArea(new Dimension(10,0)));
		row1.add(URLBox);
		
		//row 2
		row2 = new JPanel();
		row2.setLayout(new BoxLayout(row2, BoxLayout.LINE_AXIS));
		row2.setBackground(new Color(255, 255, 250));
		
		JLabel desiredUser = new JLabel("Enter the username for the password:");
		desiredUser.setFont(font);
		userBox = new JTextField();
		userBox.setMaximumSize(boxSize);
		userBox.setFont(font);
		
		row2.add(desiredUser);
		row2.add(Box.createRigidArea(new Dimension(10,0)));
		row2.add(userBox);
		
		//row 3
		row3 = new JPanel();
		row3.setLayout(new BoxLayout(row3, BoxLayout.LINE_AXIS));
		row3.setBackground(new Color(255, 255, 250));

		JButton search = new JButton("Search");
		search.setFont(font);
		search.addActionListener(new ButtonListener());
		
		row3.add(search);
		
		//innerPanel
		innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));
		innerPanel.setBackground(new Color(255, 255, 250));
		innerPanel.setFont(font);
		
		innerPanel.add(row1);
		innerPanel.add(row2);
		innerPanel.add(row3);
		
		//outerPanel
		outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		outerPanel.setBackground(new Color(255, 255, 250));
		
		JLabel header = new JLabel("Retrieve a Password");
		header.setFont(headerFont);

		results = new JTextArea(15, 10);
		results.setFont(font);
		results.setBackground(new Color(255, 250, 250));
		results.setEditable(false);
		
		//outerPanel Layout
		outerPanel.add(header, BorderLayout.NORTH);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		outerPanel.add(innerPanel, BorderLayout.CENTER);
		outerPanel.add(results, BorderLayout.SOUTH);
		
		
	}
	class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			searchPass();
		}
	}
	
	public void searchPass()
	{
		entries = new ArrayList<PassEntry>();
		String searchURL=null;
		String searchUser=null;
		
		String fileString = crypto.readFromFile();
		String[] fileLines = fileString.split("\n");
		
		for (int i=1; i<fileLines.length; i++)
		{
			String[] cc = fileLines[i].split(" ");
			PassEntry currEntry = new PassEntry(cc);
			entries.add(currEntry);
		}
		
		if(URLBox.getText().isEmpty())
		{
			JLabel warning = new JLabel("URL is a required field.");
			if (!addRow1Warning)
			{
				row1.add(warning);
				addRow1Warning = true;
			}
		}else{
			searchURL = URLBox.getText();
		}
		if (userBox.getText().isEmpty())
		{
			JLabel warning = new JLabel("Username is a required field.");
			if (!addRow2Warning)
			{
				row2.add(warning);
				addRow2Warning = true;
			}
		}else
		{
			searchUser = userBox.getText();
		}
		
		innerPanel.revalidate();
		
		if (searchURL!=null)
		{
			if (searchUser!=null)
			{
				for (int i=0; i<entries.size(); i++)
				{
					if (!entries.get(i).URL.equals(searchURL))
					{
						entries.remove(i);
						i--;
					}
				}
				for (int i=0; i<entries.size(); i++)
				{
					if (!entries.get(i).username.equals(searchUser))
					{
						entries.remove(i);
						i--;
					}
				}
				if (entries.size()<=0)
				{
					results.append("No matches found.\n");
				}else
				{
					for (int i=0; i<entries.size(); i++)
					{
						results.append(entries.get(i).toString());
						results.append("\n");
					}
				}
			}
		}
		
	}
	
	public ArrayList<String> returnMatches()
	{
		ArrayList<String> matches = new ArrayList<String>();
		for (int i=0; i<entries.size(); i++)
		{
			matches.add(entries.get(i).toString());
		}
		
		return matches;
	}
	
	public JPanel returnPanel()
	{
		return outerPanel;
	}
	
	public JPanel returnInnerPanel()
	{
		return innerPanel;
	}
}
