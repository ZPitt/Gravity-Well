import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import org.imgscalr.Scalr;

public class SpaceMatter 
{
	public float speed = .1f;
	public float turnSpeed = 3f;
	public final int scaleSize = 20;
	private float m,r,t;
	protected float xLast;
	protected float yLast;
	private float distance,force;
	protected float friction =.99999f;
	public float[][] pva = {{0f,0f,0f},
							{0f,0f,0f}};
	public boolean isFriction = true;
	public float MAX_ORBIT=300;
	Random gen = new Random();
	public boolean isSelected,isActive;
	BufferedImage matterImage,currentImage;
	public double rot;
	public int currentImageSize;
	public Velocity velocity;
	
	
	AffineTransform transform = new AffineTransform();
	static ArrayList<SpaceMatter> SpaceObjects = new ArrayList<SpaceMatter>();
	static ArrayList<Asteroid> Asteroids = new ArrayList<Asteroid>();

	
	
	public SpaceMatter(Float xLoc, Float yLoc,Float mass, Float radius,BufferedImage img)
	{
		pva[0][0]=xLoc;
		pva[1][0]=yLoc;
		xLast=xLoc;
		yLast=yLoc;
		m=mass;
		isActive=true;
		r=radius;
		matterImage=img;
		velocity = new Velocity(0,0);
		t=0;
	}
	public float getLocX()
	{
		return pva[0][0];
	}
	public float getLocY()
	{
		return pva[1][0];
	}
	public float getRadius()
	{
		return r;
	}
	public float getMass()
	{
		return m;
	}
	public float[][] getPosVelAccel()
	{
		return pva;
	}
	public void setMass(double mass)
	{
		m=(float) mass;
	}
	public void setRadius(double radius)
	{
		r=(float) radius;
	}
	public void setX(double xPos)
	{
		pva[0][0]=(float) xPos;
		xLast=(float)xPos;
	}
	public void setY(double yPos)
	{
		pva[1][0]=(float) yPos;
		yLast=(float)yPos;
	}
	public void updateMatter()
	{
		xLast=pva[0][0];
		yLast=pva[1][0];
		
		pva[0][1]=pva[0][1]+pva[0][2];  //adds acceleration to velocity, both x and y
		pva[1][1]=pva[1][1]+pva[1][2];
		
		pva[0][0]=pva[0][0]+pva[0][1];   //adds velocity to position, both x and y
		pva[1][0]=pva[1][0]+pva[1][1];
		
		if(isFriction){
			pva[0][1]=pva[0][1]*friction;
			pva[1][1]=pva[1][1]*friction;					
		}
	}
	public Velocity getVelocity()
	{
		return velocity;
		
	}
	public void setVelocity(Velocity v)
	{
		velocity = v;
		pva[0][1]=velocity.getX()/20;
		pva[1][1]=velocity.getY()/20;
	}
	public void setJustVelocity(Velocity v)
	{
		velocity = v;
	}
	public void addVelocity(Float xVelocity, Float yVelocity)
	{
		pva[0][1]=pva[0][1]+xVelocity;
		pva[1][1]=pva[1][1]+yVelocity;

	}
	public int getCurrentImageSize()
	{
		return currentImageSize;
	}
	public static ArrayList<SpaceMatter> getSpaceObjects()
	{
		return SpaceObjects;
	}
	public float calculateOrbitVelocity(Planet host, float orbitDistance)
	{
		//float moonOnCircle = (float) (gen.nextFloat()*Math.PI*2);
		float initialVelocityY = (float) Math.sqrt((host.getMass()/(orbitDistance))*.01f);
		return initialVelocityY;
	}
	public void setPosition(float xPos, float yPos)
	{
		pva[0][0]=xPos;
		pva[1][0]=yPos;
	}
	public float differenceX()
	{
		return pva[0][0]-xLast;
	}
	public float differenceY()
	{
		return pva[1][0]-yLast;
	}
	public float getLastX()
	{
		return xLast;
	}
	public float getLastY()
	{
		return yLast;
	}
	public void setAccel(float xAccel, float yAccel)
	{
		pva[0][2]=xAccel;
		pva[1][2]=yAccel;
	}
	public float distanceBetween(SpaceMatter target)
	{
		return  (float) (Math.sqrt((float)Math.pow((pva[0][0])-target.getLocX(),2)+Math.pow(pva[1][0]-target.getLocY(),2)));//distance formula
	}
	public float[] calculateForces() //for this object it calculates the forces exerted on each other object in the array
	{
		double angle=0;
		float[] accel = {0f,0f};
		
		 for(int k=0;k<SpaceObjects.size();k++)		
   		 {
   			SpaceMatter target = SpaceObjects.get(k);
   			if(target instanceof Planet){
				distance = distanceBetween(target);
				force = target.getMass()/(distance*distance)*.01f;
				angle = (double) Math.atan2( target.getLocY()-pva[1][0],target.getLocX()-pva[0][0]);
				accel[0]+=force*Math.cos(angle);
				accel[1]+=force*Math.sin(angle);
			}
   		 }
		 return accel;
	}
	public boolean isClickedOn(Point loc)
	{
		boolean clickedOn = false;
		if((Math.sqrt((float)Math.pow((pva[0][0])-loc.getX(),2)+Math.pow(pva[1][0]-loc.getY(),2)))<=r)//distance formula less than the radius
			clickedOn = true;
		return clickedOn;
	}
	public void selectFalse()
	{
		isSelected=false;
	}
	public void setSelection(boolean sel)
	{
		for(int i=0;i<SpaceMatter.getSpaceObjects().size();i++)
		{
			SpaceMatter.getSpaceObjects().get(i).selectFalse();
		}
		isSelected=sel;
	}
	public void setType(int type)
	{
		t=(float)type;
	}
	public boolean checkSelected()
	{
		return isSelected;
	}
	public void setStill()
	{
		xLast=pva[0][0];
		yLast=pva[1][0];
	}
	public void setActive(boolean status)
	{
		isActive=status;
	}
	public boolean getActive()
	{
		return isActive;
	}
	public BufferedImage getImage()
	{
		return currentImage;
	}
	public void resizeImage(float zoom)
	{
		currentImageSize=(int)((r)*2/zoom);
		currentImage = Scalr.resize(matterImage,currentImageSize);
	}
	public double getRotation()
	{
		return rot;
	}
	public void setRotation(double rotation)
	{
		rot = rotation;
	}
	public void incrementRotation(int posORNeg)
	{
		if(posORNeg>0)
			rot+=Math.PI/100*turnSpeed;
		if(posORNeg<0)
			rot-=Math.PI/100*turnSpeed;
	}
	public void moveForward()
	{
		pva[0][1]-=Math.cos(rot+Math.PI/2)*speed;
		pva[1][1]-=Math.sin(rot+Math.PI/2)*speed;
	}

	public void setMatterImage(BufferedImage img)
	{
		currentImage = img;
		matterImage=img;
	}
	public float[] getArray()
	{
		int obj = 0;
		if(this instanceof Planet)
			obj=1;
		else if(this instanceof EndGate)
			obj=2;
		else if(this instanceof StartGate)
			obj=3;
		else if(this instanceof Objective)
			obj=4;
		else if(this instanceof Asteroid)
			obj=5;
	
		float[] a = {(float)obj,pva[0][0],pva[1][0],m,r,t,velocity.getAngle(),velocity.getMag()};
		return a;
	}
	public void update()
	{
		System.out.println("Parent Class Update");
	}
}
