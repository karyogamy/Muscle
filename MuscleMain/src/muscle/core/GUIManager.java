package muscle.core;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Sample code for understanding, working on this later
public class GUIManager {

	private IOManager io;
	private JFrame guiFrame;
	private String currAct;
	private final JTextArea textArea2 = new JTextArea (10,35);
	private JComboBox<String> dates;
	
	public GUIManager(IOManager io)
	{
		this.io = io;
		guiFrame = new JFrame();

		//make sure the program exits when the frame closes
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Muscle Prototype");
		guiFrame.setSize(600,280);
		guiFrame.setResizable(false);
		//This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);

		List<String> actList = io.getIm().getActivityList();
		String[] actArr = new String[actList.size()];
		final String[] actArray = (String[]) actList.toArray(actArr);

		List<String> opts = dateListToString(io.getPm().getListOfDate());
		String[] dateArr = new String[opts.size()];
		dateArr = (String[]) opts.toArray(dateArr);
		
		//The first JPanel contains a JLabel and JCombobox
		final JPanel comboPanel = new JPanel();
		JLabel comboLbl = new JLabel("Workout History:");
		dates = new JComboBox<String>(dateArr);
		final JTextArea textArea = new JTextArea (10,50); 
		
		dates.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox<String> c = (JComboBox<String>)e.getSource();
				List<Muscle> mus = listInvolvedMuscle(c.getSelectedItem().toString());
				if (mus != null) {
					textArea.setText(null);
					textArea.append("On this day, you have exercised the following muscles:" + MuscleMain.LINE_BREAK);
					for (Muscle m: mus) {
						textArea.append(m.toString() + MuscleMain.LINE_BREAK);
					}
				}
			}
		});
		
		comboPanel.add(comboLbl);
		comboPanel.add(dates);
		comboPanel.add(textArea);



		//Create the second JPanel. Add a JLabel and JList and
		//make use the JPanel is not visible.
		final JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		listPanel.setVisible(false);
		JList<String> vegs = new JList<String>(actArr);
		vegs.setLayoutOrientation(JList.VERTICAL);

		ListSelectionModel listSelectionModel = vegs.getSelectionModel();

		listSelectionModel.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if (lsm.isSelectionEmpty() || lsm == null) {
					textArea2.setText("Please select an activity.");
				} else {
					// Find out which indexes are selected.
					int minIndex = lsm.getMinSelectionIndex();
					int maxIndex = lsm.getMaxSelectionIndex();
					for (int i = minIndex; i <= maxIndex; i++) {
						if (lsm.isSelectedIndex(i)) {
							String selectedAct = actArray[i];
							textArea2.setText(getMusInfo(selectedAct));
							currAct = selectedAct;
						}
					}
				}
			}

		});

		JScrollPane listScroller = new JScrollPane(vegs);
		listScroller.setPreferredSize(new Dimension(100, 200));


		listPanel.add(listScroller);
		JLabel listLbl = new JLabel("Activities:");
		listScroller.setColumnHeaderView(listLbl);

		listPanel.add(textArea2);

		JButton confirmBut = new JButton("CONFIRM");
		confirmBut.setPreferredSize(new Dimension(90, 200));
		confirmBut.addActionListener(new ConfirmActionListener());

		listPanel.add(confirmBut);

		JButton changeViewBut = new JButton("CHANGE VIEW");

		//The ActionListener class is used to handle the
		//event that happens when the user clicks the button.
		//As there is not a lot that needs to happen we can 
		//define an anonymous inner class to make the code simpler.
		changeViewBut.addActionListener(new ActionListener()
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
		guiFrame.getContentPane().add(comboPanel, BorderLayout.NORTH);
		guiFrame.getContentPane().add(listPanel, BorderLayout.CENTER);
		guiFrame.getContentPane().add(changeViewBut,BorderLayout.SOUTH);

		//make sure the JFrame is visible
		guiFrame.setVisible(true);
	}


	private String getMusInfo(String el) {
		String ret = "";
		List<Muscle> mus = io.getIm().getActToMusList(el);

		if (mus.isEmpty()) {
			return "No muscles were used; WTH did you do?";
		}

		for (Muscle m : mus) {
			ret = ret.concat("In the last " + MuscleMain.SAFE_DAY + " days: " + MuscleMain.LINE_BREAK);
			int timesUsed = io.getPm().timesMuscleUsed(MuscleMain.SAFE_DAY, m);
			if (timesUsed > 1) 
				ret = ret.concat("WARNING: ");

			ret = ret.concat(m.toString() + " has been used " + timesUsed + " times." + MuscleMain.LINE_BREAK);
		}

		//System.out.println(ret);
		return ret;
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

	private class ConfirmActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Set<String> dateSet = new HashSet<String>();
			for (Muscle mus : io.getIm().getActToMusList(currAct)) {
				dateSet.add(io.updateProfile(mus));
			}
			
			textArea2.setText(getMusInfo(currAct));
			DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) dates.getModel();

			for (String s: dateSet) {
				model.removeElement(s);
				model.addElement(s);
			}
		}

	}
}