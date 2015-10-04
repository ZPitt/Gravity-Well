import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimationManager extends Manager {
	public static BufferedImage spaceBackground1,gravityWellImg,selectedCircle; //static for now until I can think of a better solution
	public AnimationManager()
	{
		  try {
    		  gravityWellImg = ImageIO.read(new File(imgDir+"GravityLogo_5.png"));
 			 spaceBackground1 =ImageIO.read(new File(imgDir+"potentialBackground_1.png"));
 			 selectedCircle = ImageIO.read(new File(imgDir+"SelectionCircle.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static BufferedImage getBackgroundImages(int whichOne)
	{
		if(whichOne==0)
			return gravityWellImg;
		else
			return spaceBackground1;
	}

}
