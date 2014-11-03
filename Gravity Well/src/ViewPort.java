import java.awt.Point;




public class ViewPort
{
	public float x,y, xO,yO,wO,hO,w, h,xf1,yf2,newX,newY,focalX,focalY;
	public float z =1.0f;
	public ViewPort(int xOrigin, int yOrigin, int width, int height)
	{
		x=xOrigin;
		y=yOrigin;
		w=width;
		h=height;
		focalX = x+(w)/2;
		focalY = y+(h)/2;
		xO=xOrigin;
		yO=yOrigin;
		wO=w;
		hO=h;
	}
	public float translateToWorldCooridinatesX(float xCoor)
	{
		return xCoor*(w/wO)+x;
	}
	public float translateToWorldCooridinatesY(float yCoor)
	{
		return yCoor*(h/hO)+y;
	}
	public int translateToViewPortX(float xCoor)
	{
		//System.out.println(xCoor+", "+x+", "+w+", "+wO);
		return(int)(Math.round((xCoor-x)/w*wO));
	}
	public int translateToViewPortY(float yCoor)
	{
		return(int)(Math.round((yCoor-y)/h*hO));
	}
	public boolean existInViewPort(float xCoor, float yCoor,float rad)
	{
		if(xCoor+rad>x && xCoor-rad<x+w && yCoor+rad>y && yCoor-rad<y+h)
			return true;
		return false;
	}
	public void addViewPortOrigin(float dx, float dy)
	{
		focalX-=dx;
		focalY-=dy;
		x=focalX-w/2;
		y=focalY-h/2;
	}
	public float getX()
	{
		return focalX-w/2;
	}
	public float getY()
	{
		return focalY-h/2;
	}
	public float getHeight()
	{
		return h;
	}
	public float getWidth()
	{
		return w;
	}
	public void setZoom(float zoom)
	{
		z=zoom;
	}

	public void calculateDimensions()
	{
		//x=xO+focalX*(1-z);
		w=wO*z;
		//y=yO+focalY*(1-z);
		h=hO*z;
		x=focalX-w/2;
		y=focalY-h/2;
	
	}
	public Point getFocals()
	{
		return new Point((int)focalX,(int)focalY);
	}
	public void setFocals(float xCoor,float yCoor)
	{
		focalX = xCoor;
		focalY= yCoor;
		x=focalX-w/2;
		y=focalY-h/2;
	}
	public float getZoom()
	{
		return z;
	}
}
