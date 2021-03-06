// File: animation/bb/BallModel.java
// Description: The logic / model of a ball.
// Author: Fred Swartz, Mik Seljamaa
// Date:   February 2013

import java.awt.*;

///////////////////////////////////////////////////////////////// BallModel
public class Ball {
	// ... Constants
	final static int DIAMETER = 21;

	// ... Instance variables
	private int m_x; // x and y coordinates upper left
	private int m_y;

	private int m_velocityX; // Pixels to move each time move() is called.
	private int m_velocityY;

	private int m_rightBound; // Maximum permissible x, y values.
	private int m_bottomBound;
	
	private int m_shiftTime = 10;
	
	public  boolean painted = false;
	// ======================================================== constructor
	public Ball(int x, int y, int velocityX, int velocityY) {
		m_x = x;
		m_y = y;
		m_velocityX = velocityX;
		m_velocityY = velocityY;
	}

	// ======================================================== setBounds
	public void setBounds(int width, int height) {
		m_rightBound = width - DIAMETER;
		m_bottomBound = height - DIAMETER;
	}
	public void shift(){
		if(m_shiftTime > 1){
			if(Math.random() > 0.5){
				m_velocityX = 3;
			}else{
				m_velocityX = -3;
			}
			m_x += m_velocityX;
			m_shiftTime--;
		}	
		else{
			m_velocityX = -m_velocityX;
			m_shiftTime = 10;
		}
	}
	// ============================================================== move
	public void move() {
		// ... Move the ball at the given velocity.
		m_x += m_velocityX;
		m_y += m_velocityY;
		// ... Bounce the ball off the walls if necessary.
		if (m_x < 0) { // If at or beyond left side
			m_x = 0; // Place against edge and
			m_velocityX = -m_velocityX; // reverse direction.
		} else if (m_x > m_rightBound) { // If at or beyond right side
			m_x = m_rightBound; // Place against right edge.
			m_velocityX = -m_velocityX; // Reverse direction.
		}
		// if we're at top: bounce and return to damage
		if (m_y < 0) { 
			m_y = 0;
			m_velocityY = -m_velocityY;
			//damageMock();
		} 
		// if we're at bottom: bounce
		else if (m_y > m_bottomBound) { 
			m_y = m_bottomBound;
			m_velocityY = -m_velocityY;
			if(!this.painted)Score.countDamage += 1;
			
		}
	}

	//==========================================================explode
	public void explode(){
		Explosion expl = new Explosion(getX(),getY());
		expl.explode();
	}

	// ============================================================== draw
	public void draw(Graphics g) {
		g.fillOval(m_x, m_y, DIAMETER, DIAMETER);
	}
	public void draw(Graphics g, Color v){
		g.setColor(v);
		g.fillOval(m_x, m_y, DIAMETER, DIAMETER);
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
}
