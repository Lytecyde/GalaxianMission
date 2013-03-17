import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Gun {
	// ... gun size
	private final int DIAMETER = 21;

	public static int x_gunpos = 200;
	public static int y_gunpos = 200;
	public static int gunRoll = 1;
	// ========================constructor
	public Gun() {

	}

	// ================================ draw the gun
	// draw Turret
	public void drawTurret(Graphics g) {
		g.fillOval(x_gunpos, y_gunpos, DIAMETER, DIAMETER);

	}

	// draw Gun left, right
	public void drawGun(Graphics g) {

		g.fillOval(x_gunpos + Data.x_adjust_gun, y_gunpos - DIAMETER, DIAMETER,
				DIAMETER);
	}

	public void draw(Graphics g) {

		drawTurret(g);
		drawGun(g);
	}

	public static void shoot() {
		// pellet created
		if (gunRoll == 1){
			Data.p_list.add(new Pellet(x_gunpos + Data.x_adjust_gun, y_gunpos, 0, -5));
		} else if (gunRoll == 2){
			shootDouble();
		}
		if (gunRoll == 3){
			shootRocket();
		}
		
	}
	//==============================
	public static void shootRocket() {
		
		// pellet created
		Data.r_list.add(new Rocket(x_gunpos + Data.x_adjust_gun, y_gunpos, 0, Data.velocityXRocket));					
	}
	public static void shootDouble(){
		Data.p_list.add(new Pellet(x_gunpos + Data.x_adjust_gun, y_gunpos, 0, -5));
		Data.p_list.add(new Pellet(x_gunpos + Data.x_adjust_gun + 21, y_gunpos, 0, -5));
	}
	
	public static void aimLeft() {
		Data.x_adjust_gun += -2;

	}

	public static void aimRight() {
		Data.x_adjust_gun += 2;

	}
}// endclass
