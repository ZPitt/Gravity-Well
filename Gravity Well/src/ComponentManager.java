import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ComponentManager extends Manager implements ActionListener, KeyListener{
	
	public ArrayList<JButton> buttonArray = new ArrayList<JButton>();
	public ArrayList<ImageIcon> buttonIcons = new ArrayList<ImageIcon>();
	public ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	public ArrayList<GamePanel> panelArray = new ArrayList<GamePanel>();

	JButton startButton1= new JButton();
	JButton startButton2 = new JButton();
    JButton menuButton = new JButton();
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
    public GamePanel currentCard = new GamePanel();
    
    public GamePanel menuCard = new GamePanel();
    public GamePanel gameCard = new GamePanel();
    public GamePanel optionsCard = new GamePanel();
    public GamePanel simulatorCard = new GamePanel();
    public GamePanel levelSelectCard = new GamePanel();
    public GamePanel previousCard = new GamePanel();
    
    public GamePanel panelEastSim = new GamePanel();
    public GamePanel panelNorthSim = new GamePanel();
    public GamePanel panelSouthSim = new GamePanel();
    public GamePanel panelWestSim = new GamePanel();
    public GamePanel panelCenterSim = new GamePanel();
    
    public GamePanel panelEastGame = new GamePanel();
    public GamePanel panelNorthGame = new GamePanel();
    public GamePanel panelSouthGame = new GamePanel();
    public GamePanel panelWestGame = new GamePanel();
    public GamePanel panelCenterGame = new GamePanel();
    
    public GamePanel panelEastLevel = new GamePanel();
    public GamePanel panelNorthLevel = new GamePanel();
    public GamePanel panelSouthLevel = new GamePanel();
    public GamePanel panelWestLevel = new GamePanel();
    public GamePanel panelCenterLevel = new GamePanel();
    
    GridLayout glNorth = new GridLayout(3,0);
 	FlowLayout flWest = new FlowLayout(FlowLayout.RIGHT);
 	FlowLayout flSouthSim = new FlowLayout(FlowLayout.CENTER);
 	FlowLayout flSouthGame = new FlowLayout(FlowLayout.CENTER);
 	FlowLayout flSouthLevel = new FlowLayout(FlowLayout.CENTER);
 	GridBagLayout gblCenter = new GridBagLayout();
    
	public ComponentManager()
	{
		makeButtons();
		formatPanels();
		setUpCards();
	}

	public void setUpCards()
	{
		menuCard.setLayout(gblCenter);
		
		GridBagConstraints c = new GridBagConstraints();
	 		c.gridy = 1;
	 		c.insets = new Insets(200,0,0,0); 
        menuCard.add(startButton1,c);
        	c.gridy = 2;
        	c.insets = new Insets(10,0,0,0);
        menuCard.add(optionsButton,c);
        	c.gridy = 3;
        menuCard.add(simulatorButton,c);
        
        optionsCard.add(backButton1);
        
        panelSouthGame.setLayout(flSouthGame);
        panelSouthGame.add(menuButton);
        panelSouthGame.add(launchButton);
        panelSouthGame.add(retryButton);
        
        panelSouthLevel.setLayout(flSouthLevel);
        panelSouthLevel.add(backButton2);
        panelSouthLevel.add(startButton2);
        panelSouthLevel.add(tempLevelButton);
        panelSouthLevel.setBackground(Color.GREEN);
        
        flWest.setVgap(10);
        panelWestSim.setPreferredSize(new Dimension(150,400));
        panelWestSim.setLayout(flWest);
        panelWestSim.add(addPlanetButton);
        panelWestSim.add(addMoonButton);
        panelWestSim.add(addEndGateButton);
        panelWestSim.add(addStartGateButton);
        panelWestSim.add(addObjectiveButton);
        panelWestSim.add(addAsteroidButton);
        panelWestSim.add(backButton3);
        
        flSouthSim.setHgap(100);
        panelSouthSim.setLayout(flSouthSim);
        panelSouthSim.add(runSimButton);
        panelSouthSim.add(deleteButton);
        panelSouthSim.add(resetButton);
        panelSouthSim.add(stopButton);
        panelSouthSim.setLayout(new BorderLayout());
        
        simulatorCard.setLayout(new BorderLayout());
        simulatorCard.add(panelNorthSim, BorderLayout.NORTH);
        simulatorCard.add(panelEastSim, BorderLayout.LINE_END);
        simulatorCard.add(panelSouthSim, BorderLayout.SOUTH);
        simulatorCard.add(panelWestSim, BorderLayout.LINE_START);
        
        gameCard.setLayout(new BorderLayout());
        gameCard.add(panelNorthGame, BorderLayout.PAGE_START);
        gameCard.add(panelEastGame, BorderLayout.LINE_END);
        gameCard.add(panelSouthGame, BorderLayout.PAGE_END);
        gameCard.add(panelWestGame, BorderLayout.LINE_START);
        
        levelSelectCard.setLayout(new BorderLayout());
        levelSelectCard.add(panelNorthLevel, BorderLayout.PAGE_START);
        levelSelectCard.add(panelEastLevel, BorderLayout.LINE_END);
        levelSelectCard.add(panelSouthLevel, BorderLayout.PAGE_END);
        levelSelectCard.add(panelWestLevel, BorderLayout.LINE_START);
       
       
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(menuCard, MENU);
        cards.add(gameCard, GAME);
        cards.add(levelSelectCard,LEVELSELECT);
        cards.add(simulatorCard, SIMULATOR);
        cards.add(optionsCard, OPTIONS);
        
        levelSelectCard.setMainPanel();
        menuCard.setMainPanel();
        gameCard.setMainPanel();
        optionsCard.setMainPanel();
        simulatorCard.setMainPanel();
        
        cards.setSize(500,500);
         
	}
	public void makeButtons()
	{
		buttonArray.add(addAsteroidButton);
		 buttonArray.add(addEndGateButton);
		 buttonArray.add(addMoonButton); //"ADD"
		 buttonArray.add(addObjectiveButton);
		 buttonArray.add(addPlanetButton);//"add"
		 buttonArray.add(addStartGateButton);
		 buttonArray.add(backButton1);
		 buttonArray.add(backButton2);
	     buttonArray.add(backButton3);
	     buttonArray.add(deleteButton);
	     buttonArray.add(launchButton);
	     buttonArray.add(menuButton);
	     buttonArray.add(optionsButton);
	     buttonArray.add(resetButton);
	     buttonArray.add(retryButton);
	     buttonArray.add(runSimButton);
	     buttonArray.add(simulatorButton);
		 buttonArray.add(startButton1);
	     buttonArray.add(startButton2);
	     buttonArray.add(stopButton);
	     buttonArray.add(tempLevelButton);
	        
	        for(int i=0;i<buttonArray.size();i++){
	        	buttonArray.get(i).addActionListener(this);
	        	buttonArray.get(i).setOpaque(false);
	        	buttonArray.get(i).setContentAreaFilled(false);
	        	buttonArray.get(i).setBorderPainted(false);
	        	buttonArray.get(i).setFocusPainted(false);
	        	buttonArray.get(i).setFocusable(false);
	        	
	        	ImageIcon icon = new ImageIcon(imgDir+"Buttons/"+imgNames[i]+"Button.png");
	        	ImageIcon iconRO =new ImageIcon(imgDir+"Buttons/"+imgNames[i]+"ButtonRO.png");
	        	buttonArray.get(i).setIcon(icon);
	        	buttonArray.get(i).setRolloverIcon(iconRO);
	        }
	 
	        retryButton.setVisible(false);
	        resetButton.setVisible(false);
	        stopButton.setVisible(false);
	        
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
	@Override
	public void actionPerformed(ActionEvent e) {
		 Object s = e.getSource();
		 if(s==menuButton){
			 //setGameModeInactive();
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
	    	 //simulatorStartUp();
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
	    	 //currentLevel = 1;
	    	 //levelStartUp(currentLevel);
	    	 previousName = currentName;
	    	 currentName = GAME;
	    	  previousCard = currentCard;
	    	  currentCard=gameCard; }
	     if(s==backButton1||s==backButton2||s==backButton3){
	    	 //setGameModeInactive();
	    	 currentName = previousName;
	    	  currentCard=previousCard; }
	      //if(s==startButton2 || s==menuButton || s==simulatorButton || s==backButton3)
	      {
	    	  
	      }
//	      if(s==backButton3){
//	    	  currentGameType.setGameState(GameType.VIEWER);
//	    	  updateSimArray();
//	      }
//	      if(s==addPlanetButton)
//	    	  addPlanet();
//	      if(s==addEndGateButton)
//	    	  addEndGate();
//	      if(s==addStartGateButton)
//	    	  addStartGate();
//	      if(s==addObjectiveButton)
//	    	  addObjective();
//	      if(s==addAsteroidButton)
//	    	  addAsteroid();
//	      if(s==launchButton)
//	    	  launchSequence();
//	      if(s==retryButton)
//	    	  restartLevel();
//	      if(s==deleteButton)
//	      {
//	    	  deleteSelected();
//	      }
//	      if(s==runSimButton)
//	      {
//	    	 runSimulation();
//	      }
//	      if(s==stopButton)
//	      {
//	    	  stopPause();
//	      }
//	      if(s==resetButton)
//	      {
//	    	  resetLevel();
//	      }
//	      if(s==tempLevelButton)
//	      {
//	    	  levelStartUp(0);
//	    	  currentLevel=0;
//	    	  previousName = currentName;
//		      currentName = GAME;
//		      previousCard = currentCard;
//		      currentCard=gameCard;
//	      }
	      CardLayout cl = (CardLayout)(cards.getLayout());
	      cl.show(cards,currentName);
	      currentCard.repaint();
		
	}
	public void drawGame(float interpolation)
	{
		 currentCard.setInterpolation(interpolation);
	     currentCard.repaint();
	}
	public void formatPanels()
	{
		 panelArray.add(panelEastSim); 
		 panelArray.add(panelNorthSim);
		 panelArray.add(panelSouthSim);
		 panelArray.add(panelWestSim);
		 panelArray.add(panelCenterSim);
		 panelArray.add(panelEastLevel); 
		 panelArray.add(panelNorthLevel);
		 panelArray.add(panelSouthLevel);
		 panelArray.add(panelWestLevel);
		 panelArray.add(panelCenterLevel);
		 panelArray.add(panelEastGame); 
		 panelArray.add(panelNorthGame);
		 panelArray.add(panelSouthGame);
		 panelArray.add(panelWestGame);
		 panelArray.add(panelCenterGame);
		 for(int i=0;i<panelArray.size();i++){
			 panelArray.get(i).setOpaque(false);
		 }
	}
	public JPanel getCards()
	{
		return cards;
	}
	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	private class GamePanel extends JPanel 
	
	   {
	      float interpolation;
	      public boolean isMainPanel =false;
	      
	      public GamePanel()
	      {
	    	  setFocusable( true ); 
	    	
	    	  
	      }
	      public void setInterpolation(float interp)
	      {
	         interpolation = interp;
	      }
	      public void setMainPanel()
	      {
	    	  isMainPanel=true;
	      }
	      public void paintComponent(Graphics g)
	      {
	    	 if(isMainPanel){
	    	 Graphics2D g2D = (Graphics2D)g;
	    	 if(ComponentManager.currentName.equals("MENU"))
 			 drawMenu(g);
	    	 else
	    		 g.drawImage(AnimationManager.getBackgroundImages(1),0,0,null);
//	    	 g.drawImage(spaceBackground1,-1400-Math.round(viewPort.getX()/10),-700-Math.round(viewPort.getY()/10),null);
//	    	 
//	    	 if(running){
//	    		 if((currentGameType.isActive())){
//	    			 if(currentGameType.getGameType()==1)
//	    				 drawSimulatorValues(g);
//	    			 drawViewPort(g2D);}
//	    		 else if(currentName.equals(MENU))
//	    	 if(ButtonManager.currentName.equals("MENU"))
//	    			 drawMenu(g);
//	     	}
	    	 }
	      }
	     public void drawSimulatorValues(Graphics g)
	     {
//	    	if(somethingSelected){
//	    	 g.setColor(Color.WHITE);
//	    	 g.drawString("Mass",30,10);
//	    	 g.drawString("Radius",30,85);
//	    	 g.drawString("X Location",30,160);
//	    	 g.drawString("Y Location",30,235);
//	    	 g.drawString("Type",30,310);
//	    	}
	     }
	     public void drawMenu(Graphics g)
	     {
	    	 g.drawImage(AnimationManager.getBackgroundImages(0), 0, 0, null);
	     }
	     public void drawViewPort(Graphics2D g)
	     {
	    	 
//	    	 for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
//	 			{
//	    		 	SpaceMatter matter = SpaceMatter.getSpaceObjects().get(i);
//	    		 	
//	    		 	worldX = (matter.differenceX()*interpolation + matter.getLastX());
//	    		 	worldY = (matter.differenceY()*interpolation + matter.getLastY());
//	    		 	rad =(int)((matter.getRadius())/viewPort.getZoom());
//	    		 		    		 
//	    		 	if(matter instanceof Spaceship&&currentGameType.getGameState().equals(GameType.PLAYING))//sets focals onto the ship (400,400)
//	    		 		viewPort.setFocals(worldX,worldY);
//	    		 	
//	    		 	if(viewPort.existInViewPort(worldX, worldY,rad))
//	    		 	{
//	    		 		x= viewPort.translateToViewPortX(worldX); //translates the world coordinates into screen locations
//		 				y= viewPort.translateToViewPortY(worldY);
//		 				
//		 				matterImage = matter.getImage();
//		 				
//		 				if(matter.checkSelected() && currentGameType.getGameState().equals(GameType.VIEWER)){
// 		 				g.drawImage(tempSelectedCircle,x-rad-4,y-rad-4,null);
// 		 			}
//		 				
//	    		 		if(matter instanceof Planet){
//	    		 			g.drawImage(matterImage,x-rad,y-rad,null);
//	    		 		}
//	    		 		else if(matter instanceof Spaceship)
//	    		 		{
//	    		 			if(currentGameType.getGameType()==0){
//				            	 g.setColor(Color.WHITE);
//				            	 g.drawPolyline(((Spaceship) matter).getPathX(viewPort), ((Spaceship) matter).getPathY(viewPort), 
//				            			 ((Spaceship) matter).getPathX(viewPort).length);
//				             }
//	    		 			 AffineTransform at = new AffineTransform();
//				             at.translate(x,y);
//				             at.rotate(matter.getRotation());
//				             at.translate(-matter.getCurrentImageSize()/2,-matter.getCurrentImageSize()/2);
//				             g.drawImage(matterImage, at, null);
//				            
//	    		 		}
//	    		 		else 
//	    		 		{
//	    		 			g.drawImage(matterImage,x-rad,y-rad,null);	
//	    		 		}
//	    		 	
//	    		 	}
//	    		 	drawVelocityLines(g, matter,worldX,worldY);
//	 		 }
//	    	 if(currentGameType.getGameType()==1&&getStartGate()!=null){
//	    		 	g.setColor(Color.GREEN);
//	    		 	g.drawPolyline(pathX,pathY,pathX.length);
//	    		 	
//	    		 }
//	    	 if(currentGameType.getGameState().equals(GameType.LOST)){
//	    		 if(currentGameType.getGameType()==0){
//	    			 g.setColor(new Color(192,192,192,200));
//	    			 g.fillRect(0, 0, World.WIDTH, World.HEIGHT);
//	    		 }
//	    		 else{
//	    			 
//	    		 }
//	    	 }
//	    	 else if(currentGameType.getGameState().equals(GameType.WON)){
//	    		 if(currentGameType.getGameType()==0){
//		    		 g.setColor(new Color(53,255,153,200));
//			    	 g.fillRect(0, 0, World.WIDTH, World.HEIGHT);
//	    		 }
//	    		 else{
//	    			 
//	    		 }
//	    	 }
//	    	 g.rotate(0);
//	         frameCount++;
	     }
	   }
	

}
