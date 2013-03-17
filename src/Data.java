import java.util.LinkedList;

public class Data {
	public static int x_adjust_gun = 0;
	public static LinkedList<Pellet> parr = new LinkedList<Pellet>();
	public static LinkedList<Rocket> r_list = new LinkedList<Rocket>();
	public static LinkedList<Ball> m_balls = new LinkedList<Ball>();
	public static LinkedList<Explosion> expls = new LinkedList<Explosion>();
	//nof attackers in each wing
	public static int nofAttackers = 6;
	public static int attackersLevel = 24;
	//pellet rise rate
	public static int velocityY = -5;
	public static int velocityXRocket = -10;
	//
	public static int nofExplosions;
	public static int xplosionTime = 10;
	public static LinkedList<Integer> explosionTimes = new LinkedList<Integer>();
}
