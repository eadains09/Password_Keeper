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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;




public class PassDeletion {
	private JPanel outerPanel, innerPanel2, row1, row2, row3;
	private JTextField URLBox, userBox;
	@SuppressWarnings("rawtypes")
	private JList results;
	private JTextArea resultsArea;
	private JButton delete;
	private ArrayList<PassEntry> entries, entriesFull;
	boolean addPanel2Warning = false, addRow1Warning = false, addRow2Warning=false;
	private Cryption crypto = new Cryption();
	private Font font = new Font (Font.SERIF, 10, 20);
	private Font headerFont = new Font(Font.SERIF, Font.BOLD, 22);
	private Dimension boxSize = new Dimension(200, 50);
	
	PassDeletion()
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
		search.addActionListener(new SearchButtonListener());
				
		row3.add(search);
		
		//InnerPanel 2
		innerPanel2 = new JPanel();
		innerPanel2.setLayout(new BoxLayout(innerPanel2, BoxLayout.PAGE_AXIS));
		innerPanel2.setBackground(new Color(255, 255, 250));
	
		resultsArea = new JTextArea();
		resultsArea.setFont(font);
		resultsArea.setBackground(new Color(255, 250, 250));
		resultsArea.setMaximumSize(new Dimension(600, 300));
		
		delete = new JButton("Delete");
		delete.setFont(font);
		delete.addActionListener(new DeleteButtonListener());
		
		innerPanel2.add(row1);
		innerPanel2.add(row2);
		innerPanel2.add(row3);
		innerPanel2.add(resultsArea);
		innerPanel2.add(delete);
		
		//OuterPanel
		outerPanel = new JPanel();
		outerPanel.setLayout(new BorderLayout());
		outerPanel.setBackground(new Color(255, 255, 250));

		JLabel header = new JLabel("Delete a Password");
		header.setFont(headerFont);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		
		outerPanel.add(header, BorderLayout.NORTH);
		outerPanel.add(innerPanel2, BorderLayout.CENTER);
	}
	
	class DeleteButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			if (results.getSelectedValue()==null)
			{
				JLabel warning = new JLabel("No item selected for deletion.");
				if (!addPanel2Warning)
				{
					innerPanel2.add(warning);
					addPanel2Warning = true;
					innerPanel2.revalidate();
				}
			}else{
				passDelete();
			}
		}
	}
	
	class SearchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			searchPass();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void searchPass()
	{
		entries = new ArrayList<PassEntry>();
		entriesFull = new ArrayList<PassEntry>();
		String searchURL=null;
		String searchUser=null;
		
		String fileString = crypto.readFromFile();
		String[] fileLines = fileString.split("\n");
		
		for (int i=1; i<fileLines.length; i++)
		{
			String[] cc = fileLines[i].split(" ");
			PassEntry currEntry = new PassEntry(cc);
			entries.add(currEntry);
			entriesFull.add(currEntry);
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
		
		innerPanel2.revalidate();
		
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
					resultsArea.append("No matches found.\n");
				}else
				{
					String[] matches = new String[entries.size()];
					for (int i=0; i<entries.size(); i++)
					{
						matches[i] = entries.get(i).toString();
					}
					results = new JList(matches);
					results.setFont(font);
					results.setBackground(new Color(255, 250, 250));
					results.setMaximumSize(new Dimension(600, 300));
					innerPanel2.remove(resultsArea);
					innerPanel2.remove(delete);
					innerPanel2.add(results);
					innerPanel2.add(delete);
					innerPanel2.revalidate();
				}
			}
		}
		
	}
	
	private void passDelete()
	{
		String password = results.getSelectedValue().toString();
		JLabel complete = new JLabel("Successfully deleted.");
		complete.setFont(font);
		
		for (int i=0; i<entriesFull.size(); i++)
		{
			if (entriesFull.get(i).toString().equals(password))
			{
				entriesFull.remove(i);
				i--;
				innerPanel2.add(complete);
				innerPanel2.revalidate();
			}
		}
		
		String entriesComplete = "\n" + entriesFull.get(0).toString();
		for (int i=1; i<entriesFull.size(); i++)
		{
			entriesComplete = entriesComplete + "\n" + entriesFull.get(i).toString();
		}
		
		crypto.encryptString(entriesComplete);
	}
	
	public JPanel returnPanel()
	{
		return outerPanel;
	}
}
