import java.awt.image.BufferedImage;


public class Asteroid extends SpaceMatter
{
	public float duration, direction,speed,timeLeft,initialDelay,flyTime,endX,endY,distance,startX,startY;

	
	public Asteroid(Float xLoc, Float yLoc,Float mass, Float radius,BufferedImage img) {
		super(xLoc, yLoc,mass,radius,img);
		
		timeLeft=0;
		startX=xLoc;
		startY=yLoc;
	}
	public void setTFDS(float time,float xEnd, float yEnd,float spd) //time is time between asteroids, flying is time asteroid is active (in update steps)
	{
		duration = time;
		Velocity vel = new Velocity(xEnd,yEnd);
		distance = vel.getMag();
		direction = vel.getAngle();
		vel.changeMag(spd);
		speed=spd;
		flyTime=distance/(spd)*20;//multiplied by 20 because thats the speed conversion for good speeds
		setVelocity(vel);

	}
	public float getDuration()
	{
		return duration;
	}
	public float getDirection()
	{
		return direction;
	}
	public float getSpeed()
	{
		return speed;
	} 
	public void decrementDuration()
	{
		timeLeft-=1;
		if(timeLeft==0);
			
	}
	public void setInitialDelay(float time)
	{
		initialDelay=(float)time;
		timeLeft=time;
	}
	public float getDelay()
	{
		return initialDelay;
	}
	public void removeAsteroid()
	{
		for(int i=0;i<SpaceMatter.SpaceObjects.size();i++)
		{
			if(SpaceMatter.SpaceObjects.get(i).equals(this))
				SpaceMatter.SpaceObjects.remove(i);
		}
	}
	public void update()
	{
		if(timeLeft<=0){
			setActive(true);
			setPosition(startX,startY);
			setStill();
			SpaceMatter.SpaceObjects.add(this);
			timeLeft = flyTime;
		}
		if(getActive()) //this will be where it deals with all of the spawning, adding, removing, redrawing...
		{
			this.updateMatter();
			timeLeft-=1;;
			if(timeLeft<=0){
				removeAsteroid(); //removes it from the spaceObjects array thus removing it from all the collision check and drawing calculations
				timeLeft=duration;
				setActive(false);
			}
				
		}
		else{
			timeLeft-=1;
		}
	}
}
