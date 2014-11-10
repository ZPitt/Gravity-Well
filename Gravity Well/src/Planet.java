
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Planet extends SpaceMatter
{
	ArrayList<Moon> moons = new ArrayList<Moon>();

	public Planet(Float xLoc, Float yLoc,Float mass, Float radius,BufferedImage img) {
		super(xLoc, yLoc,mass,radius,img);
		
	}
	public void addMoon(Moon newMoon)
	{
		moons.add(newMoon);
	}
	public ArrayList<Moon> getMoons()
	{
		return moons;
	}
	
	public Moon makeMoon()
	{
		float orbitDistance = getRadius()+gen.nextFloat()*MAX_ORBIT;
		Moon moon = new Moon(getLocX()+orbitDistance,getLocY(),.00001f,6f,(Planet)this,Level.moonImages.get(0));
		//moon.setInitialVelocity(0f,calculateOrbitVelocity(this,orbitDistance));
		addMoon(moon);
		return moon;
	}
	public void updateForces()
	{
		//however I decide to update planets
	}
}
