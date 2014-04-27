package muscle.core;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Muscle {
	String muscle;
	
	Muscle (String mus) {
		muscle = mus;
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
	private HashMap<String, List<Muscle>> am;
	
	public ItemManager() {
		hm = new HashMap<Muscle, List<String>>();
		am = new HashMap<String, List<Muscle>>();
	}
	
	//if add is successful, return true, else ret false
	public boolean addMuscleAct(String mus, String act) {
//		mus = mus.toLowerCase();
		Muscle m = new Muscle(mus);
		if (!hm.containsKey(m)) {
			List<String> actList = new ArrayList<String>();
			actList.add(act);
			hm.put(m, actList);
			addActivityMus(act, m);
			return true;
		} else if (!hm.get(m).contains(act)) {
			hm.get(m).add(act);
			addActivityMus(act, m);
			return true;
		} else {
			return false;
		}
	}

	//must add corresponding act-mus pair when addMuscleAct is called
	//should not be called publicly
	private void addActivityMus(String act, Muscle mus) {
		//all activities kept in lowercase
//		act = act.toLowerCase();
		if (!am.containsKey(act)) {
			List<Muscle> musList = new ArrayList<Muscle>();
			musList.add(mus);
			am.put(act, musList);
		} else if (!am.get(act).contains(mus)) {
			am.get(act).add(mus);
		}
	}
	
	//TODO: for debugging purposes, delete when done
	public HashMap<Muscle, List<String>> getHm() {
		return hm;
	}	
	
	//TODO: for debugging purposes, delete when done
	public HashMap<String, List<Muscle>> getAm() {
		return am;
	}

	//get all muscle names
	public List<Muscle> getMuscleList() {
		List<Muscle> musList = new ArrayList<Muscle>();
		for (Muscle m : hm.keySet()) {
			musList.add(m);
		}
		return musList;
	}
	
	public List<Muscle> getActToMusList(String act) {
		return this.am.get(act);
	}
	
	//get all activity names
	public List<String> getActivityList() {
		List<String> actList = new ArrayList<String>();
		for (String a : am.keySet()) {
			actList.add(a);
		}
		return actList;
	}
	
	public List<String> getMusToActList(Muscle mus) {
		return this.hm.get(mus);
	}
	
}
