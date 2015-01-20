import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Spaceship extends SpaceMatter
{
	public static String LEFT ="LEFT";
	public static String RIGHT ="RIGHT";
	public static String MOVING ="MOVING";
	public static String STATIC ="STATIC";
	private float[] tempAccel;

	ArrayList<BufferedImage> shipImages = new ArrayList<BufferedImage>();
	ArrayList<Float> xPath = new ArrayList<Float>();
	ArrayList<Float> yPath = new ArrayList<Float>();
	
	private int[] tempPath;
	public int objectiveCount;
	
	public Spaceship(Float xLoc, Float yLoc,Float mass, Float radius,BufferedImage img) {
		super(xLoc, yLoc,mass,radius,img);
		
		objectiveCount=0;
		
	}
	
	
	public void update(){
		if(getActive()){
			tempAccel = calculateForces();
			setAccel(tempAccel[0],tempAccel[1]);
			
			xPath.add(getLocX());
			yPath.add(getLocY());
		}
		
	}
	public int[] getPathX(ViewPort viewPort)
	{
		tempPath= new int[xPath.size()];
		for(int i=0;i<xPath.size();i++){
			tempPath[i]=viewPort.translateToViewPortX(xPath.get(i));
		}
		return tempPath;
	}
	public int[] getPathY(ViewPort viewPort)
	{
		tempPath= new int[xPath.size()];
		for(int i=0;i<yPath.size();i++){
			tempPath[i]=viewPort.translateToViewPortY(yPath.get(i));
		}
		return tempPath;
	}
	
	public void setShipImages(ArrayList<BufferedImage>	shipImgs)
	{
		for(int i=0;i<4;i++){
			shipImages.add(shipImgs.get(i));
		}
	}
	public void setCurrentImage(String movement)
	{
		if(movement.equals(STATIC))
			setMatterImage(shipImages.get(0));
		if(movement.equals(RIGHT))
			setMatterImage(shipImages.get(1));
		if(movement.equals(LEFT))
			setMatterImage(shipImages.get(2));
		if(movement.equals(MOVING))
			setMatterImage(shipImages.get(3));
		
	}
	public void recievedObjective()
	{
		objectiveCount++;
	}
	public int getObjectiveCount()
	{
		return objectiveCount;
	}
}
