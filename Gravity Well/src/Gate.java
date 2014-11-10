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
	public void update()
	{
		super.updateMatter();
	}
}
