import java.awt.image.BufferedImage;


public class Moon extends SpaceMatter
{
	public float distance;
	public Planet homePlanet;
	public Moon(float a, float b, float c, float d,Planet hostPlanet, BufferedImage img){
		super(a,b,c,d,img);
		homePlanet = hostPlanet;
		}
	public Planet getHostPlanet()
	{
		return homePlanet;
	}

}
