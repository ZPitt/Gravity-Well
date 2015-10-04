import javax.swing.JFrame;

public class Manager extends JFrame{
	static int randomStat=20;
	static final String MENU= "MENU";
	static final String SIMULATOR = "SIMULATOR";
	static final String OPTIONS = "OPTIONS";
	static final String GAME = "GAME";
	static final String LEVELSELECT = "LEVELSELECT";
	static final String BACK = "BACK";
	static String currentName = "MENU";
	static String previousName = "MENU";
	
	static final String imgDir = "Images/";
    static String[] imgNames = {"AddAsteroid","AddEndGate","AddMoon","AddObjective","AddPlanet","AddStartGate","Back","Back","Back","Delete",
    		"Launch","Menu","Options","Reset","Retry","Run","Simulator","Start","Start","Stop","Temp"};//"Pause","Help","AddOribit","Delete"};
    	
    public static ViewPort vp = new ViewPort(0,0,WIDTH,HEIGHT);
    
	public Manager()
	{
		
	}
	

}
