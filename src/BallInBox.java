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
		//------------ Collision detection
		//Check the pellets
		roundCheck(1);
		//Rocket check
		roundCheck(0);//0 is rockets			
		//------------Drawing
		// Draw the balls.
		for (Ball b : Data.m_balls) {
			b.draw(g);
		}
		// Draw the gun
		m_gun.draw(g);
		// Draw all flying pellets
		for (Pellet p : Data.p_list) {
			p.draw(g);
		}
		//Draw all rockets
		for (Rocket r:Data.r_list) {
			r.draw(g);
		}
		//Draw the explosion particles
		for(Explosion expl : Data.exp_list){
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
		//==================================RocketCollisionDetect
	@SuppressWarnings("unchecked")
	public void roundCheck(int roundType){
		int ballX1;
		int ballX2;
		int ballY1;
		int ballY2;
		int indexOfBall = -1;
		int indexOfRound = -1;
		LinkedList list = new LinkedList();
		Object round = new Object();
		
		if(roundType == 0){
			list = Data.r_list;
		}
		if(roundType == 1){
			list = Data.p_list;
		}
		if(roundType == 2){
			list = Data.p_list;
		}
		//-----------------------
		Rocket r1; 
		Pellet p1;
		try{
			for (Object r: list) {
				//loop each ball checked against each pellet
				for (Ball b : Data.m_balls) {
					//calculate the edges of balls 				
					ballX2 = b.getX() + 21;// rightline for collision bounds
					ballX1 = b.getX() -6;// leftline for collision bounds
					if(r instanceof Rocket){ 
						r1 = (Rocket)r;
						if ((r1.getX() <= ballX2) && (r1.getX() > ballX1)) {
							ballY2 = b.getY() + 11;// bottomline
							ballY1 = b.getY() - 11;// topline
							if ((r1.getY() <= ballY2) && (r1.getY() > ballY1)) {
								// as long as more exist						
								indexOfRound = list.indexOf(r);
								indexOfBall = Data.m_balls.indexOf(b);
							}
						}
					}
					if(r instanceof Pellet){
						p1 = (Pellet)r;
						if ((p1.getX() <= ballX2) && (p1.getX() > ballX1)) {
							ballY2 = b.getY() + 11;// bottomline
							ballY1 = b.getY() - 11;// topline
							if ((p1.getY() <= ballY2) && (p1.getY() > ballY1)) {
								// as long as more exist						
								indexOfRound = list.indexOf(r);
								indexOfBall = Data.m_balls.indexOf(b);
							}
						}
					}
				}// balls loop
				
				// Removal of the shot ball and round
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
				//Remove round
				
					if(indexOfRound >= 0){
						if(!list.isEmpty()){
							list.remove(indexOfRound);
						}
						indexOfRound = -1;
					}
				
			}//rounds loop
		}catch(ConcurrentModificationException e){
			System.out.println("Sama aegne modifikatsioon nimekirjas list.");				
		}
	}
	
	//==================================make the new balls
	//TODO wing size is bigger armada x*y columns and rows
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
			if (!Data.p_list.isEmpty()) {
				for (Pellet p : Data.p_list) {
					p.move();
				}
			}
			
			if (!Data.r_list.isEmpty()) {
				for (Rocket r : Data.r_list) {
					r.move();
				}
			}
			//move each explosion's each pellet
			for(Explosion exp:Data.exp_list){
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
			for(Explosion exp:Data.exp_list){
				exp.removeExplosion();
			}
			// Update the score
			BBPanel.scoreLabelDamages.setText("DAMAGE: "
					+ Integer.toString(Score.countDamage));
			BBPanel.scoreLabelBalls.setText("Attackers: "
					+ Integer.toString(Score.countAttackersLeft));
			BBPanel.scoreLabelTotalAttackersLeft.setText("Total:"+Integer.toString(Data.attackersLevel) );
			BBPanel.ammo.setText("AMMO:"+Integer.toString(Data.ammo) );
			//Victory conditions
			//check victory condition and end game if won 
			//check damage level
			if(Data.attackersLevel < 1 && Score.countDamage < Data.winDamageLevel){
				BBPanel.scoreLabelDamages.setText(" YOU ");
				BBPanel.scoreLabelBalls.setText(" WIN! ");
				BBPanel.scoreLabelTotalAttackersLeft.setText("");
			}
			repaint(); // Repaint indirectly calls paintComponent.
		}
	}
}// endclass
