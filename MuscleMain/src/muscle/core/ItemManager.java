package muscle.core;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Muscle {
	String muscle;
	
	Muscle (String act) {
		muscle = act;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((muscle == null) ? 0 : muscle.hashCode());
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
		Muscle other = (Muscle) obj;
		if (muscle == null) {
			if (other.muscle != null)
				return false;
		} else if (!muscle.equals(other.muscle))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return muscle;
	}
}

public class ItemManager {

	private HashMap<Muscle, List<String>> hm;
	
	public ItemManager() {
		hm = new HashMap<Muscle, List<String>>();
	}
	
	//if add is successful, return true, else ret false
	public boolean addMuscleAct(String mus, String act) {
		mus = mus.toLowerCase();
		Muscle m = new Muscle(mus);
		if (!hm.containsKey(m)) {
			List<String> actList = new ArrayList<String>();
			actList.add(act);
			hm.put(m, actList);
			return true;
		} else if (!hm.get(m).contains(act)) {
			hm.get(m).add(act);
			return true;
		} else {
			return false;
		}
	}

	public HashMap<Muscle, List<String>> getHm() {
		return hm;
	}	
	
}
