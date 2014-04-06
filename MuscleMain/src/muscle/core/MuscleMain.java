package muscle.core;

public class MuscleMain {
	  
	public static void main(String[] args) {
		//Wrapper 
		//GUI contains I/O which contains IM and PM
		//iomanager parses profile and (muscle, activity) pairs
		IOManager io = new IOManager("save.txt", "items.txt");
		Muscle m1 = new Muscle("ead");
		Muscle m2 = new Muscle("d");
		Muscle m3 = new Muscle("g");
		Muscle m4 = new Muscle("ead");
		io.updateProfile(m1);
		io.updateProfile(m2);
		io.updateProfile(m3);
		io.updateProfile(m4);
		io.refreshProfile();
		System.out.println(io.getPm().timesMuscleUsed(5, m4));
		System.out.println(io.getIm().getActivityList());
		System.out.println(io.getIm().getMusToActList(new Muscle("ea")));
		//this gets sent to gui, which displays all the info
		new GUIManager(io);
	}
}