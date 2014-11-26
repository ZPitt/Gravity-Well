import java.awt.image.BufferedImage;



public class StartGate extends Gate{

	public StartGate(Float xLoc, Float yLoc, Float mass, Float radius,BufferedImage img) {
		super(xLoc, yLoc, mass, radius,img);
		finalX = xLoc;
		finalY= yLoc;
		initialX=xLoc;
		initialY=yLoc;
		setType(1);
	}
	public void update()
	{
		super.update();
	}
	public void launchSpaceShip(Spaceship ship)
	{
		ship.setVelocity(this.getVelocity());
	}

}
