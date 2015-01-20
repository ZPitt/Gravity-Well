import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.imgscalr.Scalr;


public class Objective extends SpaceMatter
{
	ArrayList<BufferedImage> objectiveImages = new ArrayList<BufferedImage>();
	ArrayList<BufferedImage> referenceImages = new ArrayList<BufferedImage>();
	public int imgStatus =0;
	public int counter;
	public boolean isGrowing=true;
	public int animationSpeed = 10;
	
	public Objective(Float xLoc, Float yLoc,Float mass, Float radius,BufferedImage img) {
		super(xLoc, yLoc,mass,radius,img);
		isActive=false;
	}
	public void setObjectiveImages(ArrayList<BufferedImage>	objImgs)
	{
		for(int i=0;i<4;i++)
		{
			objectiveImages.add(objImgs.get(i));
			referenceImages.add(objImgs.get(i));
		}
	}
	public void update()
	{
		
		if(isGrowing){
			counter++;
			if(counter%(animationSpeed)==0)
				changeImg();
			if(counter==animationSpeed*(objectiveImages.size()-1))
				isGrowing=false;
		}
		else
		{
			counter--;
			if(counter%(animationSpeed)==0)
				changeImg();
			if(counter==0)
				isGrowing=true;
		}
	}
	public void changeImg()
	{
		if(isGrowing)
			imgStatus++;
		else
			imgStatus--;
		setMatterImage(objectiveImages.get(imgStatus));
		System.out.println(imgStatus);
	}
	public void resizeImage(float zoom)
	{
		super.resizeImage(zoom);
		if(isActive)
			for(int i=0;i<objectiveImages.size();i++)
			{
				objectiveImages.set(i,Scalr.resize(referenceImages.get(i),currentImageSize));
			}
	}
}
