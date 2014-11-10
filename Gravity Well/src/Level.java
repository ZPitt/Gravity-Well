import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Level 
{
	ArrayList<float[][]> levels = new ArrayList<float[][]>();
	
	public static ArrayList<BufferedImage> planetImages = new ArrayList<BufferedImage>();
	public static ArrayList<BufferedImage> shipImages = new ArrayList<BufferedImage>();
	public static ArrayList<BufferedImage> moonImages = new ArrayList<BufferedImage>();
	public static ArrayList<BufferedImage> gateImages = new ArrayList<BufferedImage>();
	
	public ArrayList<BufferedImage> backGroundImages = new ArrayList<BufferedImage>();
	
	public float[][] tempLevel= {{1f,0f,0f,30000f,40f,0},{10}};
	public int currentLevelNum;
	BufferedImage img;
	public final String imgDir = "Images/";
	public BufferedImage gravityWellImg,spaceShipImg,tempSpaceShipImg,currentSpaceShipImg,spaceShipMovingImg,spaceBackground1,planet1,currentPlanet1Img,tempPlanet1Img;
	public String[] planetNames = {"Planet1"};
	public String[] shipNames = {"SpaceShip","SpaceShip2"};
	public String[] moonNames = {};
	public String[] gateNames = {"EndGate1","StartGate1"};
	public String[] backGroundNames = {"potentialBackGround_1"};
	public BufferedImage currentBackground;
	public static float[][] currentLevel;
	
	public Level()
	{
		loadLevels();
		loadImages();
	}
	public void makeLevel(int levelNum)
	{
		currentLevelNum = levelNum;
		SpaceMatter.getSpaceObjects().clear();
		currentLevel= levels.get(levelNum);
		
		for(int i=0;i<currentLevel.length;i++)
		{
			if(currentLevel[i][0]==0.0){			//ships
				Spaceship ship = new Spaceship(currentLevel[i][1],currentLevel[i][2],currentLevel[i][3],currentLevel[i][4],shipImages.get((int) currentLevel[i][5]));
				SpaceMatter.SpaceObjects.add(ship);
				ArrayList<BufferedImage> tempImgs = new ArrayList<BufferedImage>();
				for(int k=0;k<4;k++)
				{
					tempImgs.add(shipImages.get((int) currentLevel[i][5]+k));
				}
				ship.setShipImages(tempImgs);
			}
			if(currentLevel[i][0]==1.0){			//planets
				Planet planet = new Planet(currentLevel[i][1],currentLevel[i][2],currentLevel[i][3],currentLevel[i][4],planetImages.get((int) currentLevel[i][5]));
				SpaceMatter.SpaceObjects.add(planet);
				
			}
			if(currentLevel[i][0]==2.0){			//endgates
				EndGate endGate = new EndGate(currentLevel[i][1],currentLevel[i][2],currentLevel[i][3],currentLevel[i][4],gateImages.get((int) currentLevel[i][5]));
				SpaceMatter.SpaceObjects.add(endGate);
			}
			if(currentLevel[i][0]==3.0){			//startgates
				StartGate startGate = new StartGate(currentLevel[i][1],currentLevel[i][2],currentLevel[i][3],currentLevel[i][4],gateImages.get((int) currentLevel[i][5]));
				SpaceMatter.SpaceObjects.add(startGate);
			}
		}
	}
	public static void makePlayerShip(float xLoc, float yLoc)
	{
		Spaceship ship =new Spaceship(xLoc,yLoc,1f,10f,shipImages.get(0));
		SpaceMatter.getSpaceObjects().add(0,ship); //has to be at index 0 for some reason, I dunno yet
		ArrayList<BufferedImage> tempImgs = new ArrayList<BufferedImage>();
		for(int k=0;k<4;k++)
		{
			tempImgs.add(shipImages.get((int) 0+k));
		}
		ship.setShipImages(tempImgs);
		ship.setActive(true);
	}
	public void loadImages()
	{
		 try{
			 for(int i=0;i<planetNames.length;i++)
			 {
				 BufferedImage planetImage = ImageIO.read(new File(imgDir+planetNames[i]+".png"));
				 planetImages.add(planetImage);
			 }
			 for(int i=0;i<moonNames.length;i++)
			 {
				 BufferedImage moonImage = ImageIO.read(new File(imgDir+moonNames[i]+".png"));
				 moonImages.add(moonImage);
			 }
			 for(int i=0;i<shipNames.length;i++)
			 {
				 BufferedImage shipImage = ImageIO.read(new File(imgDir+shipNames[i]+".png"));
				 BufferedImage shipRightImage = ImageIO.read(new File(imgDir+shipNames[i]+"Right.png"));
				 BufferedImage shipLeftImage = ImageIO.read(new File(imgDir+shipNames[i]+"Left.png"));
				 BufferedImage shipMovingImage = ImageIO.read(new File(imgDir+shipNames[i]+"Moving.png"));
				 shipImages.add(shipImage);
				 shipImages.add(shipRightImage);
				 shipImages.add(shipLeftImage);
				 shipImages.add(shipMovingImage);
			 }
			 for(int i=0;i<backGroundNames.length;i++)
			 {
				 BufferedImage backGroundImage = ImageIO.read(new File(imgDir+backGroundNames[i]+".png"));
				 backGroundImages.add(backGroundImage);
			 }
			 for(int i=0;i<gateNames.length;i++)
			 {
				 BufferedImage gateImage = ImageIO.read(new File(imgDir+gateNames[i]+".png"));
				 gateImages.add(gateImage);
			 }
		 }
		 catch(IOException e){
			 e.printStackTrace();
		 }
	}
	public int getCurrentLevel()
	{
		return currentLevelNum;
	}
	public void makeTempLevel()
	{
		
	}
	public void loadLevels()
	{
		levels.add(tempLevel);
		float[][] level1 = {//{0,500,500,1,10,0},
						  	{1,200,300,900000,20,0},
							{1,400,500,90000,20,0},
							{2,100,100,1,20,0},
							{3,500,500,1,20,1}};
		levels.add(level1);
		
	}
	public static BufferedImage getNewImage(int SpaceObjectType,int imgNum)
	{
		return planetImages.get(imgNum);
	}
	public BufferedImage getBackground(){
		return currentBackground;
	}
}
