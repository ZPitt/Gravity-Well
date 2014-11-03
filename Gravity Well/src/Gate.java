import java.awt.image.BufferedImage;
import java.util.Vector;


public class Gate extends SpaceMatter
{
	private int t;
	public float initialX,initialY,finalX,finalY;
	 

	public Gate(Float xLoc, Float yLoc,Float mass, Float radius,BufferedImage img) {
		super(xLoc, yLoc,mass,radius,img);
	}
	public Gate(Float xLoc, Float yLoc,float radius, int type,BufferedImage img)
	{
		super(xLoc,yLoc,1f,radius,img);
		t=type;
	}
	public void setInitialVelLoc(float xLoc, float yLoc)
	{
		initialX=xLoc;
		initialY=yLoc;
	}
	public void setFinalVelLoc(float xLoc,float yLoc)
	{
		finalX=xLoc;
		finalY=yLoc;
	}
	public void launchSpaceShip(Spaceship ship)
	{
		ship.setInitialVelocity((finalX-initialX)/20, (finalY-initialY)/20);
		

	}
	public void setVector(float x, float y)
	{
		
	}
	public float getFinalVelX()
	{
		return finalX;
	}
	public float getFinalVelY()
	{
		return finalY;
	}
	public void update()
	{
		super.updateMatter();
	}
}
