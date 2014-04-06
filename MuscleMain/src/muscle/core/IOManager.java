package muscle.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class IOManager {

	private File save;
	private File items;
	private final String delim = ",";
	private ItemManager im;
	private ProfileManager pm;

	public IOManager(String saveLoc, String itemLoc) {
		save = new File(saveLoc);
		if(!save.exists()) {
			File f = new File(saveLoc);
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		items = new File(itemLoc);
		this.im = new ItemManager();
		readItems();
		
		this.pm = new ProfileManager();
		readProfile();
	}
	
	private void readItems() {
		if(items.exists() && !items.isDirectory() && items.canRead()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(items));
				String line;
				while ((line = br.readLine()) != null) {
					String[] content = line.split(delim);
					//We can improve this later on to save space
					//Not too much of a difference though (still O(n))
					if (content.length == 2) {
						im.addMuscleAct(content[0], content[1]);
					} else {
						//if the line cannot be parsed in the above format
						//it is ignored
						System.err.println("Line corrupted - " + line);
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println("Item file does not exist.");
		}
	}
	
	private void readProfile() {
		if(save.exists() && !save.isDirectory() && save.canRead()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(save));
				String line;
				while ((line = br.readLine()) != null) {
					String[] content = line.split(delim);
					try {
						Date d;
						d = MuscleMain.SHORT_DATE.parse(content[0]);
						pm.addLog(d, new Muscle(content[1]));
					}  catch (ParseException e) {
						//if the line cannot be parsed, then it is ignored
						//we can impl. file integrity correction later (minor speed up)
						System.err.println("File corrupted - " + e.getMessage());
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void updateProfile(Muscle m) {		
		if(save.exists() && !save.isDirectory() && save.canRead()) {
			try {
				String line;
				//possible borderline case, thus get time one every call
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				
				Date d = cal.getTime();
				
				if (!pm.addLog(d, m)) {
					return;
				}
				
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);

				line = df.format(d)+delim+m.toString();
				
				Writer out = new BufferedWriter(new FileWriter(save, true));
				out.append(line+"\n");
				
				out.close();
				//Use this here, but add refresh GUI on call 
				refreshProfile();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//TODO: I don't know if these getters are good or not for security
	public ItemManager getIm() {
		return im;
	}

	public ProfileManager getPm() {
		return pm;
	}
	
	//TODO: redundant function, deprecated
	/*
	public int timesMuscleUsed(int dayConst, Muscle m) {
		return this.pm.timesMuscleUsed(dayConst, m);
	}
	
	//TODO: redundant function, deprecated
	public List<Muscle> getMuscleList() {
		return this.im.getMuscleList();
	}
	*/
	
	public void refreshProfile() {
		this.pm = new ProfileManager();
		readProfile();
	}
}
