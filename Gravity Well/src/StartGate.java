import java.awt.image.BufferedImage;



public class StartGate extends Gate{

	public StartGate(Float xLoc, Float yLoc, Float mass, Float radius,BufferedImage img) {
		super(xLoc, yLoc, mass, radius,img);
		finalX = xLoc;
		finalY= yLoc;
		initialX=xLoc;
		initialY=yLoc;
	}
	public void update()
	{
		super.update();
		setInitialVelLoc(getLocX(),getLocY());
		
	}


}
