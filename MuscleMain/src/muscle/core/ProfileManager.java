package muscle.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ProfileManager {

	private HashMap<Date, List<Muscle>> lSet;

	public ProfileManager() {
		lSet = new HashMap<Date, List<Muscle>>();
	}

	public HashMap<Date, List<Muscle>> getlSet() {
		return lSet;
	}

	//ret true if add is successful
	public boolean addLog(Date d, Muscle m) {
		if (!lSet.containsKey(d)) {
			ArrayList<Muscle> mList = new ArrayList<Muscle>();
			mList.add(m);
			lSet.put(d, mList);
			return true;
		} else if (lSet.containsKey(d) && !lSet.get(d).contains(m)) {
			List<Muscle> mList = lSet.get(d);
			mList.add(m);
			lSet.put(d, mList);
			return true;
		} else return false;
	}

	//check if muscle is used in the number of days prior today
	//we can create a faster impl. by counting the last numbers of lines
	//and check if the date is present in those lines.
	public int timesMuscleUsed(int day, Muscle m) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	    
		int timesUsed = 0;
		for (;day>0;day--) {
			cal.add(Calendar.HOUR, -24);
			if (lSet.containsKey(cal.getTime())){
				if (lSet.get(cal).contains(m))
					timesUsed++;				
			} else break;
		}

		return timesUsed;
	}
	
	
	
}
