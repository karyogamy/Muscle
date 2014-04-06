package muscle.core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//Sample code for understanding, working on this later
public class GUIManager {

	private IOManager io;
	
	public GUIManager(IOManager io)
	{
		this.io = io;
		JFrame guiFrame = new JFrame();

		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Example GUI");
		guiFrame.setSize(600,350);

		//This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);

		//Options for the JComboBox 
		List<String> opts = dateListToString(io.getPm().getListOfDate());
		String[] dateArr = new String[opts.size()];
		dateArr = (String[]) opts.toArray(dateArr);
		
		//Options for the JList
		String[] vegOptions = {"Asparagus", "Beans", "Broccoli", "Cabbage"
				, "Carrot", "Celery", "Cucumber", "Leek", "Mushroom"
				, "Pepper", "Radish", "Shallot", "Spinach", "Swede"
				, "Turnip"};

		//The first JPanel contains a JLabel and JCombobox
		final JPanel comboPanel = new JPanel();
		JLabel comboLbl = new JLabel("Dates:");
		JComboBox dates = new JComboBox(dateArr);
		final JTextArea textArea = new JTextArea (10,50); 
		
		comboPanel.add(comboLbl);
		comboPanel.add(dates);
		comboPanel.add(textArea);
		
		dates.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				final String newline = "\n";
				JComboBox c = (JComboBox)e.getSource();
				List<Muscle> mus = listInvolvedMuscle(c.getSelectedItem().toString());
				if (mus != null) {
					textArea.setText(null);
					textArea.append("On this day, you have exercised the following muscles:" + newline);
					for (Muscle m: mus) {
						textArea.append(m.toString() + newline);
					}
				}
			}
			
		});
		
		//Create the second JPanel. Add a JLabel and JList and
		//make use the JPanel is not visible.
		final JPanel listPanel = new JPanel();
		listPanel.setVisible(false);
		JLabel listLbl = new JLabel("Vegetables:");
		JList vegs = new JList(vegOptions);
		vegs.setLayoutOrientation(JList.VERTICAL);

		listPanel.add(listLbl);
		listPanel.add(vegs);

		JButton vegFruitBut = new JButton( "Fruit or Veg");

		//The ActionListener class is used to handle the
		//event that happens when the user clicks the button.
		//As there is not a lot that needs to happen we can 
		//define an anonymous inner class to make the code simpler.
		vegFruitBut.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				//When the fruit of veg button is pressed
				//the setVisible value of the listPanel and
				//comboPanel is switched from true to 
				//value or vice versa.
				listPanel.setVisible(!listPanel.isVisible());
				comboPanel.setVisible(!comboPanel.isVisible());

			}
		});

		//The JFrame uses the BorderLayout layout manager.
		//Put the two JPanels and JButton in different areas.
		guiFrame.add(comboPanel, BorderLayout.NORTH);
		guiFrame.add(listPanel, BorderLayout.CENTER);
		guiFrame.add(vegFruitBut,BorderLayout.SOUTH);

		//make sure the JFrame is visible
		guiFrame.setVisible(true);
	}
	
	private List<Muscle> listInvolvedMuscle(String d) {
		try {
			Date date = MuscleMain.SHORT_DATE.parse(d);
			List<Muscle> musList = io.getPm().getlSet().get(date);
			return musList;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private List<String> dateListToString(List<Date> lDate) {
		List<String> sList = new ArrayList<String>();
		for (Date d: lDate) {
			sList.add(MuscleMain.SHORT_DATE.format(d).toString());
		}
		return sList;
		
	}
}