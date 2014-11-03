
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class World extends JFrame implements ActionListener, KeyListener, MouseListener, MouseWheelListener
{
	static Toolkit tk = Toolkit.getDefaultToolkit();  
	private final static int HEIGHT  =800;//(int)tk.getScreenSize().getHeight();
	private final static int WIDTH = 800;//(int) tk.getScreenSize().getWidth();
	final double GAME_HERTZ = 30.0; 	
	private final int MENU_BUTTON_X = 350;
	private final int MENU_BUTTON_Y = 550;
	private final int SIMULATOR_BUTTON_X = 5;
	private final int SIMULATOR_BUTTON_Y = 390;
	private final int BUTTON_Y_GAP = 5;
	private final int BUTTON_HEIGHT = 50;
	private final int X_MOUSE_OFFSET = 2;
	private final int Y_MOUSE_OFFSET = 26;
	
	public GameType currentGameType = new GameType();
	
	public static final int screenLocW = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-WIDTH/2;//centers the height
	public static final int screenLocH = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-HEIGHT/2;//centers the width
	private int fps = 60;
	private int frameCount = 0;
	public int x,y,z,rad,mass;
	
	private boolean up,down,right,left,somethingSelected,running,paused;
	
	public float worldX,worldY,zoomFactor,xf,yf;
	
	public ArrayList<JButton> buttonArray = new ArrayList<JButton>();
	public ArrayList<ImageIcon> buttonIcons = new ArrayList<ImageIcon>();
	public ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	
	
	private JButton startButton1= new JButton();
	private JButton startButton2 = new JButton();
	private JButton menuButton = new JButton();
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
//	private JButton startButton1=new JButton();
//	private JButton startButton2,menuButton,optionsButton,simulatorButton,backButton1,backButton2,backButton3,planetButton,
//			moonButton, startGateButton,endGateButton,runSimButton;
    
    JPanel cards;
    private GamePanel currentCard = new GamePanel();
	private GamePanel menuCard = new GamePanel();
    private GamePanel gameCard = new GamePanel();
    private GamePanel optionsCard = new GamePanel();
    private GamePanel simulatorCard = new GamePanel();
    private GamePanel levelSelectCard = new GamePanel();
    private GamePanel previousCard = new GamePanel();

	private JTextField massText = new JTextField(10);
	private JTextField radiusText = new JTextField(10);
	private JTextField xText = new JTextField(10);
	private JTextField yText = new JTextField(10);
	private JTextField typeText = new JTextField(10);
    
    final String MENU= "MENU";
    final String SIMULATOR = "SIMULATOR";
    final String OPTIONS = "OPTIONS";
    final String GAME = "GAME";
    final String LEVELSELECT = "LEVELSELECT";
    final String BACK = "BACK";
    public String currentName = "MENU";
    public String previousName = "MENU";
    
    public int[] pathX={};
    public int[] pathY={};
    
    public ViewPort viewPort = new ViewPort(0,0,WIDTH,HEIGHT);
    
    public BufferedImage gravityWellImg,spaceShipImg,tempSpaceShipImg,currentSpaceShipImg,spaceShipMovingImg,spaceBackground1,planet1,currentPlanet1Img,tempPlanet1Img,matterImage;
    ImageIcon startButtonI,startButtonIRO,menuButtonI,menuButtonIRO,simulatorButtonI,simulatorButtonIRO,optionsButtonI,optionsButtonIRO;
    public final String imgDir = "/Users/Zachariah/CodingStuff/Gravity Well/src/Pictures/";
    public String[] imgNames = {"AddEndGate","AddMoon","AddPlanet","AddStartGate","Back","Back","Back","Delete","Launch","Menu","Options","Retry","Simulator","Start","Start","Run","Stop","Pause","Help","AddOribit","Delete"};
    							  
    Level levels = new Level();
    
    ArrayList<SpaceMatter> spaceStuff;
    
    
	
	public static void main(String args[])
	{
		World startUp = new World();
		startUp.setVisible(true);
	}
	
	public World()
	{
		super("Gravity Well");
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setBounds(screenLocW,screenLocH,WIDTH, HEIGHT); //center it on the screen
	    
		setResizable(false);
	    setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		
        addComponentToPane(this.getContentPane());
        
        running = !running;
	      if (running)
	       {
	           runGameLoop();
	      }
	}
	 public void addComponentToPane(Container pane) {
		 
		 	makeComponents();
		 	
	        menuCard.setLayout(null);
	        simulatorCard.setLayout(null);
	        gameCard.setLayout(null);
	        
	        menuCard.add(startButton1);
	        menuCard.add(optionsButton);
	        menuCard.add(simulatorButton);
	        menuCard.setBackground(Color.BLACK);
	        
	        optionsCard.setBackground(Color.YELLOW);
	        optionsCard.add(backButton1);
	        
	        levelSelectCard.setBackground(Color.GREEN);
	        levelSelectCard.add(backButton2);
	        levelSelectCard.add(startButton2);
	        
	        simulatorCard.setBackground(Color.WHITE);
	        simulatorCard.add(backButton3);
	        simulatorCard.add(addPlanetButton);
	        simulatorCard.add(addMoonButton);
	        simulatorCard.add(addEndGateButton);
	        simulatorCard.add(addStartGateButton);
	        simulatorCard.add(runSimButton);
	        simulatorCard.add(deleteButton);
	   
	       
	        gameCard.add(menuButton);
	        gameCard.add(launchButton);
	        gameCard.add(retryButton);
	        gameCard.setBackground(Color.WHITE);
	         
	        //Create the panel that contains the "cards".
	        cards = new JPanel(new CardLayout());
	        cards.add(menuCard, MENU);
	        cards.add(gameCard, GAME);
	        cards.add(levelSelectCard,LEVELSELECT);
	        cards.add(simulatorCard, SIMULATOR);
	        cards.add(optionsCard, OPTIONS);
	        
	        
	        cards.setSize(500,500);
	         
	        
	        pane.add(cards, BorderLayout.CENTER);
	        pane.setBounds(500, 500, 500, 500);
	        loadImages();
	    } 
	 public void loadImages()
	 {
		 try{
			 gravityWellImg = ImageIO.read(new File(imgDir+"GravityLogo_5.png"));
			 spaceBackground1 =ImageIO.read(new File(imgDir+"potentialBackground_1.png"));
		 }
		 catch(IOException e){
			 e.printStackTrace();
		 }
	 }
	 public void levelStartUp()
		{
			currentGameType.setGameType(0);
			currentGameType.setActive(true);
			levels.makeLevel(1);
			viewPort.setFocals(400,400);
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
	 public void makeComponents()
	 {
		 textFields.add(massText);
		 textFields.add(radiusText);
		 textFields.add(xText);
		 textFields.add(yText);
		 textFields.add(typeText);

		 buttonArray.add(addEndGateButton);
		 buttonArray.add(addMoonButton); //"ADD"
		 buttonArray.add(addPlanetButton);//"add"
		 buttonArray.add(addStartGateButton);
		 buttonArray.add(backButton1);
		 buttonArray.add(backButton2);
	     buttonArray.add(backButton3);
	     buttonArray.add(deleteButton);
	     buttonArray.add(launchButton);
	     buttonArray.add(menuButton);
	     buttonArray.add(optionsButton);
	     buttonArray.add(retryButton);
	     buttonArray.add(simulatorButton);
		 buttonArray.add(startButton1);
	     buttonArray.add(startButton2);
	     buttonArray.add(runSimButton);
	     
	        //buttonArray.add(startGateButton);
	        //buttonArray.add(endGateButton);
	        
	        for(int i=0;i<buttonArray.size();i++){
	        	buttonArray.get(i).addActionListener(this);
	        	buttonArray.get(i).setOpaque(false);
	        	buttonArray.get(i).setContentAreaFilled(false);
	        	buttonArray.get(i).setBorderPainted(false);
	        	buttonArray.get(i).setFocusPainted(false);
	        	buttonArray.get(i).setFocusable(false);
	        	
	        	ImageIcon icon = new ImageIcon(imgDir+imgNames[i]+"Button.png");
	        	ImageIcon iconRO =new ImageIcon(imgDir+imgNames[i]+"ButtonRO.png");
	        	buttonArray.get(i).setIcon(icon);
	        	buttonArray.get(i).setRolloverIcon(iconRO);
	        }
	        
	        startButton1.setBounds(MENU_BUTTON_X,MENU_BUTTON_Y,110,BUTTON_HEIGHT);
	        optionsButton.setBounds(MENU_BUTTON_X-15,MENU_BUTTON_Y+(BUTTON_HEIGHT+BUTTON_Y_GAP),150,BUTTON_HEIGHT);
	        simulatorButton.setBounds(MENU_BUTTON_X-25,MENU_BUTTON_Y+(BUTTON_Y_GAP+BUTTON_HEIGHT)*2,160,BUTTON_HEIGHT);
	        backButton3.setBounds(SIMULATOR_BUTTON_X-10,SIMULATOR_BUTTON_Y,125,BUTTON_HEIGHT);
	        addPlanetButton.setBounds(SIMULATOR_BUTTON_X-30,SIMULATOR_BUTTON_Y+(BUTTON_Y_GAP+BUTTON_HEIGHT),160,BUTTON_HEIGHT);
	        addMoonButton.setBounds(SIMULATOR_BUTTON_X-30,SIMULATOR_BUTTON_Y+(BUTTON_Y_GAP+BUTTON_HEIGHT)*2,160,BUTTON_HEIGHT);
	        addStartGateButton.setBounds(SIMULATOR_BUTTON_X,SIMULATOR_BUTTON_Y+(BUTTON_Y_GAP+BUTTON_HEIGHT)*3,125,BUTTON_HEIGHT);
	        addEndGateButton.setBounds(SIMULATOR_BUTTON_X,SIMULATOR_BUTTON_Y+(BUTTON_Y_GAP+BUTTON_HEIGHT)*4,125,BUTTON_HEIGHT);
	        runSimButton.setBounds(SIMULATOR_BUTTON_X-25,SIMULATOR_BUTTON_Y+(BUTTON_Y_GAP+BUTTON_HEIGHT)*5,125,BUTTON_HEIGHT);
	        deleteButton.setBounds(SIMULATOR_BUTTON_X-10,SIMULATOR_BUTTON_Y+(BUTTON_Y_GAP+BUTTON_HEIGHT)*6,125,BUTTON_HEIGHT);
	        launchButton.setBounds(345,725,125,50);
	        menuButton.setBounds(345, 10,125,50);
	        retryButton.setBounds(345,345,125,50);
	        retryButton.setVisible(false);
	       for(int i=0;i<textFields.size();i++)
	       {
	    	   textFields.get(i).addKeyListener(this);
	    	   textFields.get(i).setBounds(20,20+75*i,100,20);
	    	   textFields.get(i).setVisible(false);
	    	   simulatorCard.add(textFields.get(i));
	    	   
	    	   textFields.get(i).setOpaque(false);
	    	   textFields.get(i).setBorder(javax.swing.BorderFactory.createEmptyBorder());
	    	   Font font = new Font("Gloucester MT Extra Condensed", Font.BOLD,15);
               
               //set font for JTextField
	    	   textFields.get(i).setFont(font);
	    	   textFields.get(i).setForeground(Color.YELLOW);
	       }
	        
	 }
	public void actionPerformed(ActionEvent e) {
		 Object s = e.getSource();
		 if(s==menuButton){
			 setGameModeInactive();
			 previousName = currentName;
			 currentName = MENU;
			  previousCard = currentCard;
	    	  currentCard=menuCard;}
	     if(s==startButton1){
	    	 previousName = currentName;
	    	 currentName = LEVELSELECT;
	    	  previousCard = currentCard;
	    	  currentCard=levelSelectCard; }
	     if(s==simulatorButton){
	    	 simulatorStartUp();
	    	 previousName = currentName;
	    	 currentName = SIMULATOR;
	    	  previousCard = currentCard;
	    	  currentCard=simulatorCard; }
	     if(s==optionsButton){
	    	 previousName = currentName;
	    	 currentName = OPTIONS;
	    	  previousCard = currentCard;
	    	  currentCard=gameCard; }
	     if(s==startButton2){
	    	 levelStartUp();
	    	 previousName = currentName;
	    	 currentName = GAME;
	    	  previousCard = currentCard;
	    	  currentCard=gameCard; }
	     if(s==backButton1||s==backButton2||s==backButton3){
	    	 setGameModeInactive();
	    	 currentName = previousName;
	    	  currentCard=previousCard; }
	      //if(s==startButton2 || s==menuButton || s==simulatorButton || s==backButton3)
	      {
	    	  
	      }
	      if(s==addPlanetButton)
	    	  addPlanet();
	      if(s==addEndGateButton)
	    	  addEndGate();
	      if(s==addStartGateButton)
	    	  addStartGate();
	      if(s==launchButton)
	    	  launchSequence();
	      if(s==retryButton)
	    	  restartLevel();
	      if(s==deleteButton)
	      {
	    	  deleteSelected();
	      }
	      if(s==runSimButton)
	      {
	    	 launchSequence();
	      }
	      CardLayout cl = (CardLayout)(cards.getLayout());
	      cl.show(cards,currentName);
	      currentCard.repaint();
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
		}
	}
	public void rescaleImages()
	{
		for(int i=0;i<SpaceMatter.SpaceObjects.size();i++)
		{
			SpaceMatter.SpaceObjects.get(i).resizeImage(viewPort.getZoom());
		}
	}
	public void setGameModeInactive()
	{
		currentGameType.setActive(false);
	}
	public void launchSequence()
	{
		currentGameType.setGameState(GameType.PLAYING);
		launchButton.setVisible(false);
		
		createPlayerShip();
		getStartGate().launchSpaceShip(getPlayerShip());
		
		getPlayerShip().setActive(true);
		retryButton.setVisible(true);
		retryButton.setBounds(345,700,125,50);
		rescaleImages();
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
			//	if(SpaceMatter.SpaceObjects.get(i) instanceof StartGate)
					//
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
			if(isDouble(radiusText.getText()))
				getSelected().setRadius(Double.parseDouble(radiusText.getText()));
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
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			down = true;
		}
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			if(!up){
				getPlayerShip().setCurrentImage(Spaceship.MOVING);
				rescaleImages();
			}
			up = true;
			
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(!right){
				getPlayerShip().setCurrentImage(Spaceship.RIGHT);
				rescaleImages();
			}
			right = true;
			left=false;
			
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
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
		if(e.getKeyCode()==KeyEvent.VK_D){
			SpaceMatter.getSpaceObjects().get(0).setPosition(10f,10f);
			SpaceMatter.getSpaceObjects().get(0).setInitialVelocity(0f,0f);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			down = false;
			getPlayerShip().setCurrentImage(Spaceship.STATIC);
			getPlayerShip().resizeImage(viewPort.getZoom());
		}
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			up = false;
			getPlayerShip().setCurrentImage(Spaceship.STATIC);
			getPlayerShip().resizeImage(viewPort.getZoom());
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			right = false;
			getPlayerShip().setCurrentImage(Spaceship.STATIC);
			getPlayerShip().resizeImage(viewPort.getZoom());
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
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
		float worldPointX =  viewPort.translateToWorldCooridinatesX((float)xPoint);
		float worldPointY =  viewPort.translateToWorldCooridinatesY((float)yPoint);
		
		if(e.getButton()==MouseEvent.BUTTON1){
			 if(currentGameType.getGameType()==0 && currentGameType.getGameState().equals(GameType.VIEWER)){
					for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
					{
						if(SpaceMatter.getSpaceObjects().get(i) instanceof StartGate){ //checks to see if its clicked on and startGate
							((StartGate)SpaceMatter.getSpaceObjects().get(i)).setFinalVelLoc(worldPointX, worldPointY);
							SpaceMatter.getSpaceObjects().get(i).setSelection(true);
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
					((StartGate)SpaceMatter.getSpaceObjects().get(getSelectedIndex())).setFinalVelLoc(worldPointX, worldPointY);
					System.out.println("hittin the booze and don't know what to choose");
					
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
		
		
			if(e.getButton()==MouseEvent.BUTTON1){
				for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
				{
					if(SpaceMatter.getSpaceObjects().get(i).isClickedOn(new Point(worldPointX,worldPointY))){
						for(int k=0;k<SpaceMatter.getSpaceObjects().size();k++) 
						{
							SpaceMatter.getSpaceObjects().get(k).setSelection(false);
						}
						SpaceMatter.getSpaceObjects().get(i).setSelection(true);
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
	            drawGame(interpolation);
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
		
		levelStartUp();
		lost();
		viewPort.setZoom(1);
		viewPort.calculateDimensions();
		rescaleImages();
		currentGameType.setGameState(GameType.VIEWER);
		retryButton.setVisible(false);
		launchButton.setVisible(true);
		
		for(int i=0;i<SpaceMatter.SpaceObjects.size();i++){
			if(SpaceMatter.SpaceObjects.get(i) instanceof StartGate)
				((StartGate)SpaceMatter.SpaceObjects.get(i)).setFinalVelLoc(xf,yf);
			}
		
	}
	 public void lost()
	 {
		 System.out.println("STOP EVERYTHING I HAVE AN ANNOUNCEMENT");
		 
		 for(int i=0;i<SpaceMatter.SpaceObjects.size();i++){
			 SpaceMatter.SpaceObjects.get(i).setStill();
			 if(SpaceMatter.SpaceObjects.get(i) instanceof Spaceship){
				 SpaceMatter.SpaceObjects.get(i).setActive(false);
				 SpaceMatter.SpaceObjects.get(i).setAccel(0,0);
				 SpaceMatter.SpaceObjects.get(i).setInitialVelocity(0,0);
				 System.out.println(SpaceMatter.SpaceObjects.get(i).getPosVelAccel()[0][2]);
			 }
		 }
		 retryButton.setBounds(345,345,125,50);
		 
	 }
	 public void won()
	 {
		 for(int i=0;i<SpaceMatter.SpaceObjects.size();i++){
			 SpaceMatter.SpaceObjects.get(i).setStill();
		 }
	 }
	 public void checkCollisions() //for now and for computational purposes I will make it so only the ships are checked 
	   {
		 spaceStuff = SpaceMatter.SpaceObjects;
		 
		   for(int i=0;i<spaceStuff.size();i++)
		   {
			   if(spaceStuff.get(i) instanceof Spaceship){
				   Spaceship ship =(Spaceship) SpaceMatter.SpaceObjects.get(i);
				   for(int k=0;k<spaceStuff.size();k++)
				   {
					   SpaceMatter target = spaceStuff.get(k);
					   if(k!=i&& ship.distanceBetween(target)<ship.getRadius()+target.getRadius()){
						   if(target instanceof Planet){
							   currentGameType.setGameState(GameType.LOST);
							   lost();
						   }
						   else if(target instanceof EndGate){
							   currentGameType.setGameState(GameType.WON);
						   		won();
						   }
					   }
				   }
			   }
		   }
	   }
	 public void createPlayerShip()
	 {
			 Level.makePlayerShip(getStartGate().getLocX(),getStartGate().getLocY());
	 }

	 public void projectedPath()
	 {
		 int count=0;
		 createPlayerShip();
		 getStartGate().launchSpaceShip(getPlayerShip());
		 System.out.print(getPlayerShip().getPosVelAccel()[0][0]+", ");
		 System.out.print(getPlayerShip().getPosVelAccel()[1][0]);
		 System.out.println("");
		 while(count<500)
		 {
			 currentGameType.update(viewPort);
			 count++;
		 }
		pathX = new int[count];
		pathX = getPlayerShip().getPathX(viewPort);
		pathY = new int[count];
		pathY = getPlayerShip().getPathY(viewPort);
		SpaceMatter.SpaceObjects.remove(0);
	 }
	   private void updateGame()
	   {
		  if(currentGameType.isActive())
		   {
			  currentGameType.update(viewPort);
			  
			  if(currentGameType.getGameState().equals(GameType.PLAYING)){
					  checkCollisions();
					  updateShip();
					 
			  }
			  if(currentGameType.getGameState().equals(GameType.VIEWER)&&currentGameType.getGameType() == 1&&getStartGate()!=null){
				   projectedPath();
				  }
		   }
		  updateGraphicalComponents(); //this is where we do everything concerning jcomponents and such
	   }
	   public void updateGraphicalComponents()
	   {
		 
			   if(currentGameType.getGameState().equals(GameType.VIEWER)){
				   if(currentGameType.getGameType() == 1 && currentGameType.getDragging())
					   updateTextBoxes();
				   
			   }
			   else if(currentGameType.getGameState().equals(GameType.PLAYING)){
				   
			   }
			   else if(currentGameType.getGameState().equals(GameType.PAUSED)){
				   
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
	   private void drawGame(float interpolation)
	   {
	      currentCard.setInterpolation(interpolation);
	      currentCard.repaint();
	      
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
		for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
		{
			SpaceMatter.getSpaceObjects().get(i).setSelection(false);
		}
		SpaceMatter.getSpaceObjects().add(newPlanet);
		rescaleImages();
		setVisibleStats(true);
	}
	public void addEndGate()
	{
		EndGate newGate = new EndGate((float)viewPort.getFocals().getX(),(float)viewPort.getFocals().getY(),30000f,20f,Level.gateImages.get(0));
		newGate.setSelection(true);
		for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
		{
			SpaceMatter.getSpaceObjects().get(i).setSelection(false);
		}
		SpaceMatter.getSpaceObjects().add(newGate);
		rescaleImages();
		setVisibleStats(true);
	}
	public void addStartGate()
	{
		if(getStartGate()==null){
			StartGate newGate = new StartGate((float)viewPort.getFocals().getX(),(float)viewPort.getFocals().getY(),30000f,20f,Level.gateImages.get(1));
			
			newGate.setSelection(true);
			for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
			{
				SpaceMatter.getSpaceObjects().get(i).setSelection(false);
			}
			SpaceMatter.getSpaceObjects().add(newGate);
			
			setVisibleStats(true);
		}
		rescaleImages();
	}
	private class GamePanel extends JPanel 
	   {
	      float interpolation;
	      public GamePanel()
	      {
	    	  setFocusable( true ); 
	      }
	      public void setInterpolation(float interp)
	      {
	         interpolation = interp;
	      }
	      public void paintComponent(Graphics g)
	      {
	    	 Graphics2D g2D = (Graphics2D)g;
	    	 g.drawImage(spaceBackground1,-1400-Math.round(viewPort.getX()/10),-700-Math.round(viewPort.getY()/10),null);
	    	 
	    	 if(running){
	    		 if((currentGameType.isActive())){
	    			 if(currentGameType.getGameType()==1)
	    				 drawSimulatorValues(g);
	    			 drawViewPort(g2D);}
	    		 else if(currentName.equals(MENU))
	    			 drawMenu(g);
	     	}
	      }
	     public void drawSimulatorValues(Graphics g)
	     {
	    	if(somethingSelected){
	    	 g.setColor(Color.WHITE);
	    	 g.drawString("Mass",30,10);
	    	 g.drawString("Radius",30,85);
	    	 g.drawString("X Location",30,160);
	    	 g.drawString("Y Location",30,235);
	    	 g.drawString("Type",30,310);
	    	}
	     }
	     public void drawMenu(Graphics g)
	     {
	    	 g.drawImage(gravityWellImg, 0, 0, null);
	     }
	     public void drawViewPort(Graphics2D g)
	     {
	    	 
	    	 for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
	 			{
	    		 	SpaceMatter matter = SpaceMatter.getSpaceObjects().get(i);
	    		 	
	    		 	worldX = (matter.differenceX()*interpolation + matter.getLastX());
	    		 	worldY = (matter.differenceY()*interpolation + matter.getLastY());
	    		 	rad =(int)((matter.getRadius())/viewPort.getZoom());
	    		 	
//	    		 
	    		 	if(matter instanceof Spaceship&&currentName.equals(GAME)&&currentGameType.getGameState().equals(GameType.PLAYING))//sets focals onto the ship (400,400)
	    		 		viewPort.setFocals(worldX,worldY);
	    		 	
	    		 	if(viewPort.existInViewPort(worldX, worldY,rad))
	    		 	{
	    		 		x= viewPort.translateToViewPortX(worldX); //translates the world coordinates into screen locations
		 				y= viewPort.translateToViewPortY(worldY);
		 				
		 				matterImage = matter.getImage();
		 				
	    		 		if(matter instanceof Planet){
	    		 			if(matter.checkSelected() && currentGameType.getGameState().equals(GameType.VIEWER)){
	    		 				g.setColor(Color.GRAY);
	    		 				g.fillOval(x-rad-4,y-rad-4,rad*2+8,rad*2+8);
	    		 			}
	    		 			g.drawImage(matterImage,x-rad,y-rad,null);
	    		 			
	    		 		}
	    		 		else if(matter instanceof Spaceship)
	    		 		{
	    		 			 AffineTransform at = new AffineTransform();
				             at.translate(x,y);
				             at.rotate(matter.getRotation());
				             at.translate(-matter.getCurrentImageSize()/2,-matter.getCurrentImageSize()/2);
				             g.drawImage(matterImage, at, null);
	    		 		}
	    		 		else if(matter instanceof EndGate)
	    		 		{
	    		 			g.drawImage(matterImage,x-rad,y-rad,null);
	    		 			
	    		 		}
	    		 		else if(matter instanceof StartGate)
	    		 		{
	    		 			g.drawImage(matterImage,x-rad,y-rad,null);
	    		 		}
	    		 	}
	    		 	drawVelocityLines(g, matter,worldX,worldY);
	 		 }
	    	 if(currentGameType.getGameType()==1&&getStartGate()!=null){
	    		 	g.setColor(Color.GREEN);
	    		 	g.drawPolyline(pathX,pathY,pathX.length);
	    		 }
	    	 if(currentGameType.getGameState().equals(GameType.LOST)){
	    		g.setColor(new Color(192,192,192,200));
	    		g.fillRect(0, 0, World.WIDTH, World.HEIGHT);
	    	 }
	    	 else if(currentGameType.getGameState().equals(GameType.WON)){
	    		 g.setColor(new Color(53,255,153,200));
		    	 g.fillRect(0, 0, World.WIDTH, World.HEIGHT);
	    	 }
	    	 g.rotate(0);
	         frameCount++;
	     }
	   }
	public void drawVelocityLines(Graphics g, SpaceMatter matter,float worldX, float worldY)
	{
		x= viewPort.translateToViewPortX(worldX); //translates the world coordinates into screen locations
		y= viewPort.translateToViewPortY(worldY);
		
		if(matter instanceof StartGate){
			if(currentGameType.getGameState().equals(GameType.VIEWER)){
					g.setColor(Color.WHITE);
					xf=((StartGate)matter).getFinalVelX();
					yf=((StartGate)matter).getFinalVelY();
					g.drawLine(x,y,viewPort.translateToViewPortX(xf),
						viewPort.translateToViewPortY(yf));
			}
		}
	}
}
