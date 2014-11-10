
public class Velocity {
	public double a;
	public float x,y,m;
	public Velocity(double angle, float magnitude)
	{
		a=angle;
		m=magnitude;
		x=(float) (m*Math.cos(a));
		y=(float) (m*Math.sin(a));
	}
	public Velocity(float xComponent, float yComponent )
	{
		x=xComponent;
		y=yComponent;
		m=(float) Math.sqrt(x*x+y*y);
		a=Math.asin(y);
	
	}
	public float getMag()
	{
		return m;
	}
	public void changeMag(float newMag)
	{
		float factor = newMag/m;
		x=x*factor;
		y=y*factor;
	}
	public void changeAngle(double newAngle)
	{
		a=newAngle;
		x=(float) (m*Math.cos(a));
		y=(float) (m*Math.sin(a));
	}
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	public void setVelocity(float xComponent, float yComponent )
	{
		x=xComponent;
		y=yComponent;
		m=(float) Math.sqrt(x*x+y*y);
		a=Math.asin(y);
	}
	

}
