import java.util.LinkedList;

public class Data {
	public static int x_adjust_gun = 0;
	public static LinkedList<Pellet> p_list = new LinkedList<Pellet>();
	public static LinkedList<Rocket> r_list = new LinkedList<Rocket>();
	public static LinkedList<Ball> m_balls = new LinkedList<Ball>();
	public static LinkedList<TakeOverRocket> tar_list = new LinkedList<TakeOverRocket>();
	public static LinkedList<Explosion> exp_list = new LinkedList<Explosion>();
	//nof attackers in each wing
	public static int nofAttackers = 8;
	//nof attackers per level
	public static int attackersLevel = 32;
	public static int nofWingsInArmada = 4; 
	//pellet rise rate
	public static int velocityY = -5;
	//
	public static int velocityX = 3;
	public static int velocityXRocket = -15;
	//
	//GAME INFO
	public static int level = 0;
	public static int nofLevels = 12;
	
	//ammo and explosions
	public final static int nofPellets = 48;
	public static int ammo = nofPellets;
	public final static int nofRockets = 16;
	public static int ammoRockets = nofRockets;
	
	public static int nofExplosions;
	public static int xplosionTime = 10;
	public static LinkedList<Integer> explosionTimes = new LinkedList<Integer>();
	public static int winDamageLevel = attackersLevel * 3;
}
