// File:   animation/bb/BouncingBall.java
// Description: This Graphics panel simulates a ball bouncing in a box.
//         Animation is done by changing instance variables
//         in the timer's actionListener, then calling repaint().
//         * Flicker can be reduced by drawing into a BufferedImage, 
//           and/or using a clip region.
//         * The edge of the oval could be antialiased (using Graphics2).
// Author: Fred Swartz, Mik Seljamaa
// Date:   February 2013

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

import java.util.*;

/////////////////////////////////////////////////////////////// BouncingBall
public class BallInBox extends JPanel {
	// ============================================== fields
	// ... creating the Gun
	private Gun m_gun = new Gun();
	// ... Instance variables for the animation
	private int m_interval = 35; // Milliseconds between updates.
	private Timer m_timer; // Timer fires to animate one step.

	// ========================================================== constructor
	/** Create the attacking balls. Set panel size and creates timer. */
	
	// TODO lock panel size
	public BallInBox() {
		// ... creating the Balls
		for (int i = 0; i < Data.nofAttackers; i++)
			Data.m_balls.add(new Ball((40 + (40 * i)), 0, 0, -5));

		setSize(new Dimension(400, 300));

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		m_timer = new Timer(m_interval, new TimerAction());
	}

	// ========================================================= setAnimation
	/**
	 * Turn animation on or off.
	 * 
	 * @param turnOnOff
	 *            Specifies state of animation.
	 */
	public void setAnimation(boolean turnOnOff) {
		if (turnOnOff) {
			m_timer.start(); // start animation by starting the timer.
		} else {
			m_timer.stop(); // stop timer
		}
	}

	// ======================================================= paintComponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint background, border
		// removeCollisions
		int ballX1;
		int ballX2;
		int ballY1;
		int ballY2;
		int indexOfBall = -1;
		int indexOfPellet = -1;
		//check the pellets
		for (Pellet p : Data.parr) {
			//loop each ball checked against each pellet
			for (Ball b : Data.m_balls) {
				//calculate the edges of balls 				
				ballX2 = b.getX() + 21;// rightline
				System.out.println("x2:" + ballX2);
				ballX1 = b.getX() -6;// leftline
				System.out.println("x1:" + ballX1);
				if ((p.getX() <= ballX2) && (p.getX() > ballX1)) {
					ballY2 = b.getY() + 11;// bottomline
					ballY1 = b.getY() - 11;// topline
					if ((p.getY() <= ballY2) && (p.getY() > ballY1)) {
						// as long as more exist else score winning points
						indexOfPellet = Data.parr.indexOf(p);
						indexOfBall = Data.m_balls.indexOf(b);
					}
				}
			}// balls loop
			// Removal of the shot ball
			if (!Data.m_balls.isEmpty() && (indexOfBall >= 0)) {
				//create the explosion
				Data.m_balls.get(indexOfBall).explode();
				//ball is eliminated
				Data.m_balls.remove(indexOfBall);
				Score.countAttackersLeft--; 
				Data.attackersLevel--;
				// Reinitialize index of ball
				indexOfBall = -1;
			} 
			//produce a new wing of attackers
			else if(Data.m_balls.isEmpty() && !(Data.attackersLevel < 1)){				
				newWing();
			}
		}
		//Rocket check
		for (Rocket r : Data.r_list) {
			//loop each ball checked against each pellet
			for (Ball b : Data.m_balls) {
				//calculate the edges of balls 				
				ballX2 = b.getX() + 21;// rightline
				System.out.println("x2:" + ballX2);
				ballX1 = b.getX() -6;// leftline
				System.out.println("x1:" + ballX1);
				if ((r.getX() <= ballX2) && (r.getX() > ballX1)) {
					ballY2 = b.getY() + 11;// bottomline
					ballY1 = b.getY() - 11;// topline
					if ((r.getY() <= ballY2) && (r.getY() > ballY1)) {
						// as long as more exist else score winning points
						indexOfPellet = Data.r_list.indexOf(r);
						indexOfBall = Data.m_balls.indexOf(b);
					}
				}
			}// balls loop
			// Removal of the shot ball
			if (!Data.m_balls.isEmpty() && (indexOfBall >= 0)) {
				//create the explosion
				Data.m_balls.get(indexOfBall).explode();
				//ball is eliminated
				Data.m_balls.remove(indexOfBall);
				Score.countAttackersLeft--; 
				Data.attackersLevel--;
				// Reinitialize index of ball
				indexOfBall = -1;
			}
			//produce a new wing of attackers
			else if(Data.m_balls.isEmpty() && !(Data.attackersLevel < 1)){				
				newWing();
			}
		}				
		// Draw the balls.
		for (Ball b : Data.m_balls) {
			b.draw(g);
		}
		// Draw the gun
		m_gun.draw(g);
		// Draw all flying pellets
		for (Pellet p : Data.parr) {
			p.draw(g);
		}
		//Draw all rockets
		for (Rocket r:Data.r_list) {
			r.draw(g);
		}
		//draw the explosion particles
		for(Explosion expl : Data.expls){
			for(Pellet pExplosion:expl.explosionParticles){
				 if((int)(Math.random()*10)>5){
					 g.setColor(Color.red);
				 }else{
					 g.setColor(Color.yellow); 
				 }
				 pExplosion.draw(g);	
			}
		}
	}
	
	//==================================make the new balls
	private void newWing() {
		// create more dropping balls
		// ... creating the Balls
		Score.countAttackersLeft = Data.nofAttackers;
		for (int i = 0; i < Data.nofAttackers; i++) {
			Data.m_balls.add(new Ball((40 + (40 * i)), 0, 0, Data.velocityY));
		}
	}

	// ////////////////////////////////// inner listener class ActionListener
	class TimerAction implements ActionListener {
		// ================================================== actionPerformed
		/**
		 * ActionListener of the timer. Each time this is called, the ball's
		 * position is updated, creating the appearance of movement.
		 * 
		 * @param e
		 *            This ActionEvent parameter is unused.
		 */
		public void actionPerformed(ActionEvent e) {
			//set balls fall area
			for (Ball b : Data.m_balls) {
				if (!Data.m_balls.isEmpty())
					b.setBounds(getWidth(), getHeight());
			}

			// Move all Pellets fired by Gun
			if (!Data.parr.isEmpty()) {
				for (Pellet p : Data.parr) {
					p.move();
				}
			}
			
			if (!Data.r_list.isEmpty()) {
				for (Rocket r : Data.r_list) {
					r.move();
				}
			}
			//move each explosion's each pellet
			for(Explosion exp:Data.expls){
				exp.explosionDuration--;
				for (Pellet px : exp.explosionParticles) {
					px.move();
				}
			}
			
			// Move the balls.
			for (Ball b : Data.m_balls) {
				b.move();
			}
			//Fade the explosions
			for(Explosion exp:Data.expls){
				exp.removeExplosion();
			}
			// Update the score
			BBPanel.scoreLabelDamages.setText("DAMAGE: "
					+ Integer.toString(Score.countDamage));
			BBPanel.scoreLabelBalls.setText("Attackers: "
					+ Integer.toString(Score.countAttackersLeft));
			BBPanel.scoreLabelTotalAttackersLeft.setText("Total:"+Integer.toString(Data.attackersLevel) );
			//Victory conditions
			//check victory condition and end game if won 
			//check damage level
			if(Data.attackersLevel < 1 && Score.countDamage < 50){
				BBPanel.scoreLabelDamages.setText(" YOU ");
				BBPanel.scoreLabelBalls.setText(" WIN! ");
				BBPanel.scoreLabelTotalAttackersLeft.setText("");
			}
			repaint(); // Repaint indirectly calls paintComponent.
		}
	}
}// endclass
