package muscle.core;

import java.util.Date;
import java.util.HashSet;

class Log {
	Date date;
	Muscle mus;
	
	Log (Date d, Muscle m) {
		this.date = d;
		this.mus = m;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Log other = (Log) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

	public String toString() {
		return date+","+mus;
	}
}

public class ProfileManager {

	private HashSet<Log> lSet;
	
	public ProfileManager() {
		lSet = new HashSet<Log>();
	}

	public HashSet<Log> getlSet() {
		return lSet;
	}
	
	//ret true if add is successful
	public boolean addLog(Log l) {
		if (!lSet.contains(l)) {
			lSet.add(l);
			return true;
		} else return false;
	}
}
