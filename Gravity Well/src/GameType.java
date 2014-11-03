import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;


public class GameType {
	public final static String VIEWER = "VIEWER";
	public final static String PLAYING = "PLAYING";
	public final static String PAUSED = "PAUSED";
	public final static String LOST = "LOST";
	public final static String WON = "WON";
	
	public final static String DEEPSPACETYPE ="DEEPSPACE";
	public final static String SIMULATORTYPE ="SIMULATOR";
	public final static String PUZZLETYPE = "PUZZLE";
	
	public int currentType;
	public String currentState;
	public boolean isActive;
	
	private int xMouseLast,yMouseLast,xMouse,yMouse;
	private float diffX,diffY;
	
	private float[][] clickedOnPVA;
	private boolean draggingSpaceMatter,draggingScreen;
	private int clickedOnIndex;
	
	ArrayList<SpaceMatter> SpaceObjects = new ArrayList<SpaceMatter>();
	
	public GameType()
	{
		currentState = VIEWER;
	}
	public float[] findMouseDifference(Float zoomFactor)
	   {
		   PointerInfo a = MouseInfo.getPointerInfo();
		   Point b = a.getLocation();
		   xMouseLast = xMouse;
		   yMouseLast = yMouse;
		   xMouse = (int) b.getX()-World.screenLocW-2;
		   yMouse = (int) b.getY()-World.screenLocH-26;
		   diffX=(xMouse-xMouseLast)*zoomFactor;
		   diffY=(yMouse-yMouseLast)*zoomFactor;
		   return new float[] {diffX,diffY};
	   }
	public String getGameState()
	{
		return currentState;
	}
	public void setGameState(String newState)
	{
		if(newState.equals(VIEWER))
			currentState=VIEWER;
		else if(newState.equals(PLAYING))
			currentState=PLAYING;
		else if(newState.equals(PAUSED))
			currentState=PAUSED;
		else if(newState.equals(LOST))
			currentState=LOST;
		else if(newState.equals(WON))
			currentState=WON;
	}
	public void setGameType(int gameTypeNum)
	{
		currentType = gameTypeNum;
	}
	public int getGameType(){
		return currentType;
	}
	public boolean isActive()
	{
		return isActive;
	}
	public void setActive(boolean status)
	{
		isActive = status;
	}
	public void setMouseLoc(int xPoint,int yPoint)
	{
		xMouse=xPoint;
		yMouse=yPoint;
	}
	public void update(ViewPort viewPort)
	{
		if(!getGameState().equals(GameType.LOST)&&!getGameState().equals(GameType.WON)&&SpaceMatter.SpaceObjects.size()>0){
			SpaceMatter.SpaceObjects.get(0).updateMatter();
		}
		
		if(currentState.equals(VIEWER)){
			if(currentType==1){
				if(draggingSpaceMatter){
					float[] diff = findMouseDifference(viewPort.getZoom());
					clickedOnPVA = SpaceMatter.SpaceObjects.get(clickedOnIndex).getPosVelAccel();
					SpaceMatter.SpaceObjects.get(clickedOnIndex).setPosition(clickedOnPVA[0][0]+diff[0],clickedOnPVA[1][0]+diff[1]);
					SpaceMatter.SpaceObjects.get(clickedOnIndex).updateMatter();
					viewPort.calculateDimensions();
				}
				projectingPath();
			}
			if(currentType==0)
			{
				for(int i=0;i<SpaceMatter.SpaceObjects.size();i++) 
				   {
					   SpaceMatter spaceThing = SpaceMatter.SpaceObjects.get(i);
						   if((spaceThing instanceof Planet)){
							   ((Planet) spaceThing).updateForces();
						   }
						   if(spaceThing instanceof StartGate && spaceThing.checkSelected())
						   {
							   spaceThing.setSelection(false);
							   PointerInfo a = MouseInfo.getPointerInfo();
							   Point b = a.getLocation();
							   ((StartGate)spaceThing).setFinalVelLoc(viewPort.translateToWorldCooridinatesX((float)b.getX()-World.screenLocW-2),
									   viewPort.translateToWorldCooridinatesY((float)b.getY()-World.screenLocH-26));
						   }
						   if(spaceThing instanceof Spaceship) // this counteracts the couple of updates that happen after the gameState has been switched
						   {
//							   spaceThing.setAccel(0, 0);
//							   spaceThing.setInitialVelocity(0, 0);
						   }
				   }
		   }
		   if(draggingScreen)
		   {
			   float[] diff = findMouseDifference(viewPort.getZoom());
			   viewPort.addViewPortOrigin(diff[0],diff[1]);
			   viewPort.calculateDimensions();
		   	}
			
		}
		else if(currentState.equals(PLAYING)){
			for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++){
				SpaceMatter spaceThing = SpaceMatter.SpaceObjects.get(i);
			 if((spaceThing instanceof Planet)){
				   ((Planet) spaceThing).updateForces();
			   }
			   if(spaceThing instanceof Spaceship){
				   ((Spaceship) spaceThing).updateForces(); 
			   }
			}
		}
		
	}
	public void projectingPath()
	{
		for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++){
			SpaceMatter spaceThing = SpaceMatter.SpaceObjects.get(i);
	
		   if(spaceThing instanceof Spaceship){
			   ((Spaceship) spaceThing).updateForces(); 
		   }
		}
	}
	public void updatePlaying(){
		
	}
	
	public void setClickedOnIndex(int index)
	{
		clickedOnIndex=index;
		draggingSpaceMatter=true;
	}
	public int getClickedOnIndex()
	{
		return clickedOnIndex;
	}
	public void setDraggingMatter(boolean status)
	{
		draggingSpaceMatter=status;
	}
	public void setDraggingScreen()
	{
		draggingScreen = !draggingSpaceMatter;
	}
	public void setDragging(boolean status)
	{
		draggingScreen=false;
		draggingSpaceMatter=false;
	}
	public boolean getDragging()
	{
		return draggingSpaceMatter;
	}
}	
