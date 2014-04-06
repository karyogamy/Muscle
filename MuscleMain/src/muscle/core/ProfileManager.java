package muscle.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ProfileManager {

	private HashMap<Date, List<Muscle>> pMap;
	
	public ProfileManager() {
		pMap = new HashMap<Date, List<Muscle>>();
	}

	public HashMap<Date, List<Muscle>> getlSet() {
		return pMap;
	}

	//ret true if add is successful
	public boolean addLog(Date d, Muscle m) {
		if (!pMap.containsKey(d)) {
			ArrayList<Muscle> mList = new ArrayList<Muscle>();
			mList.add(m);
			pMap.put(d, mList);
			return true;
		} else if (pMap.containsKey(d) && !pMap.get(d).contains(m)) {
			List<Muscle> mList = pMap.get(d);
			mList.add(m);
			pMap.put(d, mList);
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
			Date d = cal.getTime();
			if (pMap.containsKey(d)){
				if (pMap.get(d).contains(m))
					timesUsed++;				
			} else break;
			cal.add(Calendar.HOUR, -24);
		}

		return timesUsed;
	}
	
	
	
}
