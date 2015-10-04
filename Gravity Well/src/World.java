
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.imgscalr.Scalr;


public class World extends JFrame implements ActionListener, KeyListener, MouseListener, MouseWheelListener, ComponentListener
{
	static Toolkit tk = Toolkit.getDefaultToolkit();  
	private final static int HEIGHT  =800;//(int)tk.getScreenSize().getHeight();
	private final static int WIDTH = 800;//(int) tk.getScreenSize().getWidth();
	final double GAME_HERTZ = 30.0; 	
	private final int X_MOUSE_OFFSET = 3;
	private final int Y_MOUSE_OFFSET = 25;
	public int stepCount=500;
    int updateCountPerSecond=0;
    
	public GameType currentGameType = new GameType();
	
	public static int screenLocX = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-WIDTH/2;//centers the height
	public static int screenLocY = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-HEIGHT/2;//centers the width
	private int fps = 60;
	private int frameCount = 0;
	public int x,y,z,rad,mass,currentLevel;
	public int movedOffsetX=0;
	public int movedOffsetY=0;
	
	private boolean up,down,right,left,somethingSelected,running,paused,isPathing,simulationStarted;
	
	public float worldX,worldY,zoomFactor,xf,yf;
	
	public ArrayList<JButton> buttonArray = new ArrayList<JButton>();
	public ArrayList<ImageIcon> buttonIcons = new ArrayList<ImageIcon>();
	public ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	
	JButton optionsButton = new JButton();
    JButton simulatorButton = new JButton();
    JButton backButton1 = new JButton();
    JButton backButton2 = new JButton();
    JButton backButton3 = new JButton();
    JButton addPlanetButton = new JButton();
    JButton addMoonButton = new JButton();
    JButton addStartGateButton = new JButton();
    JButton addEndGateButton = new JButton();
    JButton runSimButton = new JButton();
    JButton deleteButton = new JButton();
    JButton launchButton = new JButton();
    JButton retryButton = new JButton();
    JButton stopButton = new JButton();
    JButton resetButton = new JButton();
    JButton tempLevelButton = new JButton();
    JButton addObjectiveButton = new JButton();
    JButton addAsteroidButton = new JButton();
    
    JPanel cards;

	private JTextField massText = new JTextField(10);
	private JTextField radiusText = new JTextField(10);
	private JTextField xText = new JTextField(10);
	private JTextField yText = new JTextField(10);
	private JTextField typeText = new JTextField(10);
	
	public InputManager IM = new InputManager();
    public ComponentManager CM = new ComponentManager();
    public AnimationManager AM = new AnimationManager();
    public Manager M = new Manager();
  
    
    public int[] pathX= new int[stepCount];
    public int[] pathY= new int[stepCount];
    public float[] tempX= new float[stepCount];
    public float[] tempY= new float[stepCount];
    GridBagLayout gblCenter = new GridBagLayout();
    public ViewPort viewPort = new ViewPort(0,0,WIDTH,HEIGHT);
    
    public BufferedImage gravityWellImg,spaceShipImg,tempSpaceShipImg,currentSpaceShipImg,spaceShipMovingImg,spaceBackground1,planet1,currentPlanet1Img,
    					 tempPlanet1Img,matterImage,selectedCircle,tempSelectedCircle;
    ImageIcon startButtonI,startButtonIRO,menuButtonI,menuButtonIRO,simulatorButtonI,simulatorButtonIRO,optionsButtonI,optionsButtonIRO;
    public final String imgDir = "Images/";
    public String[] imgNames = {"AddAsteroid","AddEndGate","AddMoon","AddObjective","AddPlanet","AddStartGate","Back","Back","Back","Delete","Launch","Menu","Options","Reset","Retry","Run","Simulator","Start","Start","Stop","Temp"};//"Pause","Help","AddOribit","Delete"};
    							  
    Level levels = new Level();
    
    ArrayList<SpaceMatter> spaceStuff;
    
    Velocity shipVelocity =new Velocity(0,0);
	
	public static void main(String args[])
	{
		World startUp = new World();
		startUp.setVisible(true);
	}
	
	public World()
	{
		super("Gravity Well");
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(screenLocX,screenLocY,WIDTH, HEIGHT); //center it on the screen
	    
		setResizable(true);
	    setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		addComponentListener(this);
		
        addComponentToPane(this.getContentPane());
        
        currentLevel=1;
        
        running = !running;
	      if (running)
	       {
	           runGameLoop();
	      }
	}
	 public void addComponentToPane(Container pane) {
	        
	        pane.add(CM.getCards(), BorderLayout.CENTER);
	    } 
	 public void levelStartUp(int whichLevel)
		{
			currentGameType.setGameType(0);
			currentGameType.setActive(true);
			levels.makeLevel(whichLevel);
			getStartGate().setVelocity(new Velocity(0,0));
			viewPort.setFocals(getStartGate().getLocX(),getStartGate().getLocY());
			rescaleImages();
		}
	public void simulatorStartUp()
		{
			currentGameType.setGameType(1);
			viewPort.setFocals(0,0);
			currentGameType.setActive(true);
			levels.makeLevel(0);
			rescaleImages();
		}
	public void mouseReleased(MouseEvent e) {
		if(currentGameType.isActive())
			 currentGameType.setDragging(false);
	}
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(currentGameType.isActive()){
			zoomFactor = viewPort.getZoom();
			zoomFactor+=.05*(e.getWheelRotation()); 
			if(zoomFactor>3.0)
				zoomFactor=3.0f;
			else if(zoomFactor<0.1)
				zoomFactor=0.1f;
			else{
			viewPort.setZoom(zoomFactor);
			viewPort.calculateDimensions();
			}
			rescaleImages();
			rescaleIcons();
		}
	}
	public void resetLevel()
	{
		if(!isPathing){
			simulationStarted=false;
			levels.makeLevel(0);
			stopButton.setVisible(false);
			resetButton.setVisible(false);
			currentGameType.setGameState(GameType.VIEWER);
			runSimButton.setVisible(true);
			viewPort.setFocals(0,0);
			rescaleImages();
		}
		
	}
	public void stopPause()
	{
		for(int i=0;i<SpaceMatter.SpaceObjects.size();i++){
			SpaceMatter.SpaceObjects.get(i).setStill();
		}
		stopButton.setVisible(false);
		runSimButton.setVisible(true);
		currentGameType.setGameState(GameType.PAUSED);
		activateSpace(false);
	}
	public void rescaleImages()
	{
		for(int i=0;i<SpaceMatter.SpaceObjects.size();i++)
		{
			SpaceMatter.SpaceObjects.get(i).resizeImage(viewPort.getZoom());
		}
	}
	public void rescaleIcons()
	{
		if(currentGameType.getGameType()==1 && getSelected()!=null)
			tempSelectedCircle = Scalr.resize(selectedCircle,(int)(getSelected().getRadius()*2/viewPort.getZoom())+8);
	}

	public void setGameModeInactive()
	{
		currentGameType.setActive(false);
		currentGameType.setGameState(GameType.VIEWER);
	}
	public void launchSequence()
	{
		currentGameType.setGameState(GameType.PLAYING);

		createPlayerShip();
		getStartGate().launchSpaceShip(getPlayerShip());
		shipVelocity = getStartGate().getVelocity();
		getPlayerShip().setActive(true);
		activateSpace(true);
		if(currentGameType.getGameType()==0){
			retryButton.setVisible(true);
			launchButton.setVisible(false);
		}
		retryButton.setBounds(345,700,125,50);
		rescaleImages();
	}
	public void activateSpace(boolean status){
		for(int i=1;i<SpaceMatter.SpaceObjects.size();i++)
		{
			SpaceMatter.SpaceObjects.get(i).setActive(status);
		}
	}
	public void deleteSelected()
	{
		if(getSelectedIndex()!=-1){
			SpaceMatter.SpaceObjects.remove(getSelectedIndex());
			setVisibleStats(false);
		}
	}
	public static boolean isDouble(String s) {
	    try { 
	        Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	public SpaceMatter getSelected()
	{
		for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
		{
			if(SpaceMatter.getSpaceObjects().get(i).checkSelected()){
				return SpaceMatter.getSpaceObjects().get(i);
				}
			
		}
		return null;
	}
	public int getSelectedIndex()
	{
		for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
		{
			if(SpaceMatter.getSpaceObjects().get(i).checkSelected()){
				return i;}
		}
		return -1;
	}
	public void updateTextBoxes()
	{
		massText.setText(Float.toString(getSelected().getMass()));
		 radiusText.setText(Float.toString(getSelected().getRadius()));
		 xText.setText(Float.toString(getSelected().getLocX()));
		 yText.setText(Float.toString(getSelected().getLocY()));
	}
	public void keyPressed(KeyEvent e) {
		Object s = e.getSource();
		if(s==massText&&e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(isDouble(massText.getText()))
				getSelected().setMass(Double.parseDouble(massText.getText()));
		}
		if(s==radiusText&&e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(isDouble(radiusText.getText())){
				getSelected().setRadius(Double.parseDouble(radiusText.getText()));
				rescaleImages();
			}
		}
		if(s==xText&&e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(isDouble(xText.getText()))
				getSelected().setX(Double.parseDouble(xText.getText()));
		}
		if(s==yText&&e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(isDouble(yText.getText()))
				getSelected().setY(Double.parseDouble(yText.getText()));
			System.out.println(yText.getText());
		}
		if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_S)
		{
			down = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)
		{
			if(!up){
				getPlayerShip().setCurrentImage(Spaceship.MOVING);
				rescaleImages();
			}
			up = true;
			
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D)
		{
			if(!right){
				getPlayerShip().setCurrentImage(Spaceship.RIGHT);
				rescaleImages();
			}
			right = true;
			left=false;
			
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A)
		{
			if(!left){
				getPlayerShip().setCurrentImage(Spaceship.LEFT);
				rescaleImages();
			}
			left = true;
			right=false;
			
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){
			launchSequence();
		}
		if(e.getKeyCode()==KeyEvent.VK_T){
			SpaceMatter.getSpaceObjects().get(0).setPosition(10f,10f);
			SpaceMatter.getSpaceObjects().get(0).setVelocity(new Velocity(0,0));
		}
		if(e.getKeyCode()==KeyEvent.VK_V)
		{
			for(int i=0;i<stepCount;i++){
				System.out.print(pathX[i]+", ");
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_DOWN || e.getKeyCode()==KeyEvent.VK_S)
		{
			down = false;
			getPlayerShip().setCurrentImage(Spaceship.STATIC);
			getPlayerShip().resizeImage(viewPort.getZoom());
		}
		if(e.getKeyCode()==KeyEvent.VK_UP || e.getKeyCode()==KeyEvent.VK_W)
		{
			up = false;
			getPlayerShip().setCurrentImage(Spaceship.STATIC);
			getPlayerShip().resizeImage(viewPort.getZoom());
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT || e.getKeyCode()==KeyEvent.VK_D)
		{
			right = false;
			getPlayerShip().setCurrentImage(Spaceship.STATIC);
			getPlayerShip().resizeImage(viewPort.getZoom());
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_A)
		{
			left = false;
			getPlayerShip().setCurrentImage(Spaceship.STATIC);
			getPlayerShip().resizeImage(viewPort.getZoom());
		}
		if(up)
		{
			getPlayerShip().setCurrentImage(Spaceship.MOVING);
			getPlayerShip().resizeImage(viewPort.getZoom());
		}
	}
	public void mouseClicked(MouseEvent e) {
		int xPoint = (int) ((e.getX()-X_MOUSE_OFFSET));
		int yPoint = (int) ((e.getY()-Y_MOUSE_OFFSET));
		System.out.println(xPoint+", "+X_MOUSE_OFFSET);
		System.out.println(yPoint+", "+Y_MOUSE_OFFSET);
		float worldPointX =  viewPort.translateToWorldCooridinatesX((float)xPoint);
		float worldPointY =  viewPort.translateToWorldCooridinatesY((float)yPoint);
		
		if(e.getButton()==MouseEvent.BUTTON1){
			 if(currentGameType.getGameType()==0 && currentGameType.getGameState().equals(GameType.VIEWER)){
					for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
					{
						if(SpaceMatter.getSpaceObjects().get(i) instanceof StartGate){ //checks to see if its clicked on and startGate
							StartGate sGate =((StartGate)SpaceMatter.getSpaceObjects().get(i));
							sGate.getVelocity().setVelocity(worldPointX-sGate.getLocX(), worldPointY-sGate.getLocY());
							
						}
					}
			 }
		}
		if(e.getButton()==MouseEvent.BUTTON3)
		{
			
			if(currentGameType.getGameType()==1 &&currentGameType.getGameState().equals(GameType.VIEWER))
			{
				
				if(getSelected() instanceof StartGate)
				{
					StartGate sGate = getStartGate();
					sGate.getVelocity().setVelocity(worldPointX-sGate.getLocX(), worldPointY-sGate.getLocY());
					
				}
			}
		}
	}
	public void mouseEntered(MouseEvent arg0) {
		
	}
	public void mouseExited(MouseEvent arg0) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		int xPoint = (int) ((e.getX()-X_MOUSE_OFFSET));
		int yPoint = (int) ((e.getY()-Y_MOUSE_OFFSET));
		
		currentGameType.setMouseLoc(xPoint,yPoint);
		
		int worldPointX = (int) viewPort.translateToWorldCooridinatesX(xPoint);
		int worldPointY = (int) viewPort.translateToWorldCooridinatesY(yPoint);
		
		
			if(e.getButton()==MouseEvent.BUTTON1 && !isPathing){
				for(int i=SpaceMatter.getSpaceObjects().size()-1;i>=0;i--)
				{
					if(SpaceMatter.getSpaceObjects().get(i).isClickedOn(new Point(worldPointX,worldPointY))&&currentGameType.getGameType()!=0){
						SpaceMatter.getSpaceObjects().get(i).setSelection(true);
						rescaleIcons();
						if(isPathing){ // sometimes when you click it will happen in the middle of the pathing calculations and the index will be thrown off, this compensates for that
							System.out.println("INTERRUPTED PATHING");
							i=i-1;
						}
						currentGameType.setClickedOnIndex(i);
						setVisibleStats(true);
						currentGameType.setDraggingMatter(true);
						break;
						
					}
					currentGameType.setDraggingScreen();
				}
				
			}
			
			
				

	}
	public void keyTyped(KeyEvent arg0) {
	}
	public void runSimulation()
	{
		if(currentGameType.getGameState().equals(GameType.PAUSED))
		{
			runSimButton.setVisible(false);
			stopButton.setVisible(true);
			currentGameType.setGameState(GameType.PLAYING);
		}
		else if(getStartGate()!=null){
			updateSimArray();//this is what the reset function will refer to and also implement the transformation of SpaceObjects data into float arrays.
			runSimButton.setVisible(false);
			stopButton.setVisible(true);
			resetButton.setVisible(true);
			launchSequence();
			
			for(int i=0;i<stepCount;i++){
				tempX[i]=viewPort.translateToWorldCooridinatesX((float)pathX[i]);
				tempY[i]=viewPort.translateToWorldCooridinatesY((float)pathY[i]);
			}
			simulationStarted=true;
		}
	}
	public Spaceship getPlayerShip()
	{
		for(int i=0;i<SpaceMatter.SpaceObjects.size();i++)
		{
			if(SpaceMatter.SpaceObjects.get(i) instanceof Spaceship)
				return (Spaceship)SpaceMatter.SpaceObjects.get(i);
		}
		return null;
	}
	public int getPlayerShipIndex()
	{
		for(int i=0;i<SpaceMatter.SpaceObjects.size();i++)
		{
			if(SpaceMatter.SpaceObjects.get(i) instanceof Spaceship)
				return i;
		}
		return -1;
	}
	public StartGate getStartGate()
	{
		for(int i=0;i<SpaceMatter.SpaceObjects.size();i++)
		{
			if(SpaceMatter.SpaceObjects.get(i) instanceof StartGate)
				return (StartGate)SpaceMatter.SpaceObjects.get(i);
		}
		return null;
	}
	public void runGameLoop()
	   {
	      Thread loop = new Thread()
	      {
	         public void run()
	         {
	            gameLoop();
	         }
	      };
	      loop.start();
	   }
	private void gameLoop()
	   {				
	      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;  //Calculate how many ns each frame should take for our target game hertz.
	      final int MAX_UPDATES_BEFORE_RENDER = 5;						//At the very most we will update the game this many times before a new render.
	      																//If you're worried about visual hitches more than perfect timing, set this to 1.
	      double lastUpdateTime = System.nanoTime();				    //We will need the last update time.
	      double lastRenderTime = System.nanoTime();					//Store the last time we rendered.
	      
	      final double TARGET_FPS = 60;									//If we are able to get as high as this FPS, don't render again.
	      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
	     
	      int lastSecondTime = (int) (lastUpdateTime / 1000000000);		 //Simple way of finding FPS.
	      
	      while (running)
	      {
	         double now = System.nanoTime();
	         int updateCount = 0;
	     
	         
	         if (!paused)
	         {
	            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )  //Do as many game updates as we need to, potentially playing catchup.
	            {
	               updateGame();
	        
	               lastUpdateTime += TIME_BETWEEN_UPDATES;
	               updateCount++;
	            }
	            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)   //If for some reason an update takes forever, we don't want to do an insane number of catchups.
	            {													//If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
	               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
	            }
	            float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) ); //Render. To do so, we need to calculate interpolation for a smooth render.
	            CM.drawGame(interpolation);
	            lastRenderTime = now;

	            int thisSecond = (int) (lastUpdateTime / 1000000000);  //Update the frames we got.
	            if (thisSecond > lastSecondTime)
	            {
	               System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
	               
	               fps = frameCount;
	               frameCount = 0;
	               lastSecondTime = thisSecond;
	            }
	         
	            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
	            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
	            {
	               Thread.yield();
	               try {Thread.sleep(1);} catch(Exception e) {} 
	            
	               now = System.nanoTime();
	            }
	         }
	      }
	   }
	public void restartLevel()
	{
		currentGameType.setGameState(GameType.LOST);
		levelStartUp(currentLevel);
		getStartGate().setJustVelocity(shipVelocity);
		viewPort.setZoom(1);
		viewPort.calculateDimensions();
		rescaleImages();
		currentGameType.setGameState(GameType.VIEWER);
		retryButton.setVisible(false);
		launchButton.setVisible(true);
		
		
	}
	 public void lost()
	 {
		 for(int i=0;i<SpaceMatter.SpaceObjects.size();i++){
			 SpaceMatter matter = SpaceMatter.SpaceObjects.get(i); 
			 matter.setStill();
			 if(matter instanceof Spaceship){
				 matter.setActive(false);
				 matter.setAccel(0,0);
				 matter.setVelocity(new Velocity(0,0));
			 }
			 if(matter instanceof StartGate)
				 shipVelocity = matter.getVelocity();
		 }
		 retryButton.setBounds(345,345,125,50);
		 
	 }
	 public void won()
	 {
		 for(int i=0;i<SpaceMatter.SpaceObjects.size();i++){
			 SpaceMatter.SpaceObjects.get(i).setStill();
		 }
	 }
	 public void checkCollisions() //for now and for computational purposes I will make it so only the ship(s) are checked 
	   {
				   Spaceship ship = getPlayerShip();
				   for(int k=1;k<SpaceMatter.SpaceObjects.size();k++) //because the ship is always at index 0, it can start at 1 and avoid the check later
				   {
					   SpaceMatter target = SpaceMatter.SpaceObjects.get(k);
					   if(ship.distanceBetween(target)<ship.getRadius()+target.getRadius()){
						   
						   if(target instanceof Planet || target instanceof Asteroid){
							   currentGameType.setGameState(GameType.LOST);
							   //System.out.println(interpolation);
							   lost();
						   }
						   else if(target instanceof EndGate){
							   checkWin();
						   }
						   else if(target instanceof Objective)
						   {
							   hitObjective(k);
						   }
					   }
			
		   }
	   }
	 public void checkWin()
	 {
		 if(levels.getObjectiveCount()==getPlayerShip().getObjectiveCount()){
			  currentGameType.setGameState(GameType.WON);
	   		  won();
		 }
	 }
	 public void hitObjective(int index)
	 {
		 SpaceMatter.SpaceObjects.remove(index);
		 getPlayerShip().recievedObjective();
	 }
	 public void createPlayerShip()
	 {
		 
			 Level.makePlayerShip(getStartGate().getLocX(),getStartGate().getLocY());
	 }

	 public void projectedPath()
	 {
		 isPathing=true;
		 int count=0;
		 
		 createPlayerShip();
		 getStartGate().launchSpaceShip(getPlayerShip());

		 while(count<stepCount) //how many steps it takes, and might add additional constraint of collision detection too if not too expensive
		 {
			 currentGameType.projectPath();
			 count++;
		 }
		if(getPlayerShip() !=null){
			pathX = getPlayerShip().getPathX(viewPort);
			pathY = getPlayerShip().getPathY(viewPort);
		}
		SpaceMatter.SpaceObjects.remove(0);
		
		isPathing=false;
		
	 }
	   private void updateGame()
	   {
		  if(currentGameType.isActive())
		   {
			  currentGameType.update(viewPort); //this is what does the most of the actual updating of game components
			  
			  if(currentGameType.getGameState().equals(GameType.PLAYING)){
					  checkCollisions();
					  updateShip();
					 
			  }
			  if(currentGameType.getGameType() == 1 ){
				 if(currentGameType.getGameState().equals(GameType.VIEWER)&& getStartGate()!=null)
					 projectedPath();
				}
			  
		   }
		  updateGraphicalComponents(); //this is where we do everything concerning jcomponents and such
	   }
	   public void updateSimArray()//here we go *finger crossed*
	   {
		   if(getPlayerShip()!=null)
			   SpaceMatter.SpaceObjects.remove(0);
		   int index=0;
		   float[][] tempArray = new float[SpaceMatter.SpaceObjects.size()+SpaceMatter.Asteroids.size()][5];
		   
		   for(int i=0;i<SpaceMatter.SpaceObjects.size();i++)
		   {
			   SpaceMatter matter = SpaceMatter.SpaceObjects.get(i);
			   tempArray[i]= matter.getArray();
			   index=i;
		   }
		   
		   for(int i=0;i<SpaceMatter.Asteroids.size();i++)
		   {
			   Asteroid ast = SpaceMatter.Asteroids.get(i);
			   tempArray[index+i+1] = ast.getArray();
		   }
		   
		   levels.makeSimLevel(tempArray);
	   }
	   public void updateProjectedPath()
	   {
		   for(int i=0;i<stepCount;i++)
		   {
			   pathX[i]=viewPort.translateToViewPortX(tempX[i]);
			   pathY[i]=viewPort.translateToViewPortY(tempY[i]);
			   
		   }
	   }
	   public void updateGraphicalComponents()
	   {
		 
			   if(currentGameType.getGameState().equals(GameType.VIEWER)){
				   if(currentGameType.getGameType() == 1 && currentGameType.getDragging())
					   updateTextBoxes();
				   
			   }
			   else if(currentGameType.getGameState().equals(GameType.PLAYING)){
				   if(simulationStarted)
					   updateProjectedPath(); 
			   }
			   else if(currentGameType.getGameState().equals(GameType.PAUSED)){
				   if(simulationStarted)
					   updateProjectedPath(); 
			   }
			   
			   else if(currentGameType.getGameState().equals(GameType.LOST)){
				   
			   }
			   else if(currentGameType.getGameState().equals(GameType.WON)){
				   
			   }
		  
	   }
	   private void updateShip()
	   {
		   SpaceMatter spaceship = getPlayerShip();
	    	  
	    	  if(up)
	    		  spaceship.moveForward();
	          if(right){
	        	  spaceship.incrementRotation(1);}
	          if(left){
	        	  spaceship.incrementRotation(-1);}
	   }
	   
	public void setVisibleStats(boolean b)
	{
		somethingSelected=b;
		for(int i=0;i<textFields.size();i++)
		{
			textFields.get(i).setVisible(b);
		}
	}
	public void addPlanet()
	{
		Planet newPlanet = new Planet((float)viewPort.getFocals().getX(),(float)viewPort.getFocals().getY(),30000f,40f,Level.getNewImage(0,0));
		newPlanet.setSelection(true);
		SpaceMatter.getSpaceObjects().add(newPlanet);
		rescaleImages();
		setVisibleStats(true);
		rescaleIcons();
	}
	public void addEndGate()
	{
		EndGate newGate = new EndGate((float)viewPort.getFocals().getX(),(float)viewPort.getFocals().getY(),30000f,20f,Level.gateImages.get(0));
		newGate.setSelection(true);
		SpaceMatter.getSpaceObjects().add(newGate);
		rescaleImages();
		setVisibleStats(true);
		rescaleIcons();
	}
	public void addAsteroid()
	{
		Asteroid asteroid = new Asteroid((float)viewPort.getFocals().getX(),(float)viewPort.getFocals().getY(),1f,20f,Level.asteroidImages.get(0));
		asteroid.setSelection(true);
		SpaceMatter.Asteroids.add(asteroid);
		rescaleImages();
		setVisibleStats(true);
		rescaleIcons();
	}
	public void addObjective()
	{
		Objective objective = new Objective((float)viewPort.getFocals().getX(),(float)viewPort.getFocals().getY(),30000f,20f,Level.objectiveImages.get(0));
		objective.setSelection(true);
		SpaceMatter.getSpaceObjects().add(objective);
		rescaleImages();
		setVisibleStats(true);
		rescaleIcons();
	}
	public void addStartGate()
	{
		if(getStartGate()==null){
			StartGate newGate = new StartGate((float)viewPort.getFocals().getX(),(float)viewPort.getFocals().getY(),30000f,20f,Level.gateImages.get(1));
			newGate.setSelection(true);
			SpaceMatter.getSpaceObjects().add(newGate);
			rescaleImages();
			setVisibleStats(true);
		}
		rescaleImages();
		rescaleIcons();
	}
	
	   
	public void drawVelocityLines(Graphics g, SpaceMatter matter,float worldX, float worldY)
	{
		x= viewPort.translateToViewPortX(worldX); //translates the world coordinates into screen locations
		y= viewPort.translateToViewPortY(worldY);
		
		if(matter instanceof StartGate){
			if(currentGameType.getGameState().equals(GameType.VIEWER)){
					g.setColor(Color.WHITE);
					xf=((StartGate)matter).getVelocity().getX()+matter.getLocX();
					yf=((StartGate)matter).getVelocity().getY()+matter.getLocY();
					g.drawLine(x,y,viewPort.translateToViewPortX(xf),
						viewPort.translateToViewPortY(yf));
			}
		}
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		
		//screenLocX=(int)e.getComponent().getLocationOnScreen().getX();
		//screenLocY=(int)e.getComponent().getLocationOnScreen().getY();
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
