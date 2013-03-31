import java.awt.Color;
import java.awt.Graphics;


public class Rocket {
// ... Constants
	final static int DIAMETER = 6;
	final String welcome="";
	// ... Instance variables
	protected int m_x; // x and y coordinates upper left
	protected int m_y;

	protected int m_velocityX; // Pixels to move each time move() is called.
	protected int m_velocityY;

	protected int m_rightBound; // Maximum permissible x, y values.
	protected int m_bottomBound;
	
	protected Rocket(){
		System.out.println(this.welcome);
	}
	// ======================================================== constructor
	public Rocket(int x, int y, int velocityX, int velocityY) {
		m_x = x;
		m_y = y;
		m_velocityX = 0;
		m_velocityY = Data.velocityXRocket;
	}		

	// ======================================================== setBounds
	public void setBounds(int width, int height) {
		m_rightBound = width - DIAMETER;
		m_bottomBound = height - DIAMETER;
	}

	// ============================================================== move
	public void move() {
		// ... Move the ball at the given velocity.
		m_x += m_velocityX;
		m_y += m_velocityY;

		/*
		 * REMOVING BOUNCE off walls //... Bounce the ball off the walls if
		 * necessary. if (m_x < 0) { // If at or beyond left side m_x = 0; //
		 * Place against edge and m_velocityX = -m_velocityX; // reverse
		 * direction.
		 * 
		 * } else if (m_x > m_rightBound) { // If at or beyond right side m_x =
		 * m_rightBound; // Place against right edge. m_velocityX =
		 * -m_velocityX; // Reverse direction. }
		 */

		if (m_y < 0) { // if we're at top
			// remove pellet;
			// int indexOfPellet;
			
		} 
	}

	

	// ============================================================== draw
	public void draw(Graphics g) {		
		g.fillOval(m_x, m_y, DIAMETER, 2*DIAMETER);
	}

	// ============================================= getDiameter, getX, getY
	public int getDiameter() {
		return DIAMETER;
	}

	public int getX() {
		return m_x;
	}

	public int getY() {
		return m_y;
	}

	// ======================================================== setPosition
	public void setPosition(int x, int y) {
		m_x = x;
		m_y = y;
	}
}//end of class