import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Gun {
	private static final int VELOCITY_Y = -5;

	// ... gun size
	private final int DIAMETER = 21;

	public static int x_gunpos = 200;
	public static int y_gunpos = 500;
	public static int gunRoll = 1;
	// ========================constructor
	public Gun() {

	}

	// ================================ draw the gun
	// draw Turret
	public void drawTurret(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x_gunpos, y_gunpos, DIAMETER, DIAMETER);

	}

	// draw Gun left, right
	public void drawGun(Graphics g) {
		g.setColor(Color.BLACK);
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
			Data.p_list.add(new Pellet(x_gunpos + Data.x_adjust_gun, y_gunpos, 0, VELOCITY_Y));
			Data.ammo -= 1;;
		} else if (gunRoll == 2){
			shootDouble();
			Data.ammo -= 2;
		}
		if (gunRoll == 3){
			shootRocket();
			Data.ammoRockets--;
		}
		if(gunRoll == 4){
			shootTakeOver();
			Data.ammoRockets--;
		}
	}
	public static void shootTakeOver() {
		// TODO
		Data.tar_list.add(new TakeOverRocket(x_gunpos + Data.x_adjust_gun, y_gunpos, 0, Data.velocityXRocket));
	}

	//==============================
	public static void shootRocket() {		
		// pellet created
		if(Data.ammoRockets > 0){
			Data.r_list.add(new Rocket(x_gunpos + Data.x_adjust_gun, y_gunpos, 0, Data.velocityXRocket));
		}else {
			System.out.println("");
		}
	}
	public static void shootDouble(){
		if(Data.ammo > 0){
			Data.p_list.add(new Pellet(x_gunpos + Data.x_adjust_gun, y_gunpos, 0, VELOCITY_Y));
			Data.p_list.add(new Pellet(x_gunpos + Data.x_adjust_gun + 21, y_gunpos, 0, VELOCITY_Y));
		}
		else {
			System.out.println("Out of ammo: Reload!");
		}
	}
	//to change gun position
	public static void aimLeft() {
		Data.x_adjust_gun += -2;
	}

	public static void aimRight() {
		Data.x_adjust_gun += 2;
	}
	public static void reload(){
		//set ammo and rockets
		Data.ammo = Data.nofPellets;
		Data.ammoRockets = Data.nofRockets;
	}
}// endclass
