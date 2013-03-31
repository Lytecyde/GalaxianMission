// File:  
// Description: Panel to layout buttons and graphics area.
// Authors: Fred Swartz, Mik Seljamaa
// Date:   March 2013

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/////////////////////////////////////////////////////////////////// BBPanel
class BBPanel extends JPanel implements KeyListener {
	BallInBox m_bb; // The bouncing ball panel
	static JLabel scoreLabelDamages = new JLabel("");
	static JLabel scoreLabelBalls = new JLabel("");
	static JLabel scoreLabelTotalAttackersLeft = new JLabel("");	
	static JLabel ammo = new JLabel("AMMO:");
	static JLabel rockets = new JLabel("ROCKETS:");
	// ========================================================== constructor
	/** Creates a panel with the controls and bouncing ball display. */
	BBPanel() {
		// ... Create components
		m_bb = new BallInBox();
		JButton startButton = new JButton("Start");
		JButton stopButton = new JButton("Stop");
		// ... Add Listeners
		startButton.addActionListener(new StartAction());
		stopButton.addActionListener(new StopAction());
		startButton.addKeyListener(this);
		// ... Layout inner panel with two buttons horizontally and Data labels
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,5));
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(scoreLabelDamages);
		buttonPanel.add(scoreLabelBalls);
		buttonPanel.add(scoreLabelTotalAttackersLeft);
		buttonPanel.add(ammo);
		buttonPanel.add(rockets);
		// ... Layout outer panel with button panel above bouncing ball
		this.setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.NORTH);
		this.add(m_bb, BorderLayout.CENTER);
	}// end constructor

	// //////////////////////////////////// inner listener class StartAction
	class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			m_bb.setAnimation(true);
		}
	}

	// ////////////////////////////////////// inner listener class StopAction
	class StopAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			m_bb.setAnimation(false);
		}
	}

	// ///////////////////////////////////KeyListener
	// TODO two concurrent Keylisteners one in another
	// thread is needed so that player can shoot while moving
	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_RIGHT) {
			Gun.aimRight();
			// repaint();
		}  else if (c == KeyEvent.VK_BACK_SPACE ){
			//select guntype 
			//cycle through a gunList
			if(Gun.gunRoll == 4)Gun.gunRoll = 1;
			else Gun.gunRoll++;
		} else if (c == KeyEvent.VK_LEFT) {
			Gun.aimLeft();
		} else if (c == KeyEvent.VK_SPACE) {
			Gun.shoot();			
		} else if (c == KeyEvent.VK_R && 
				(Gun.x_gunpos >190) &&(Gun.x_gunpos < 210)
				) {
			Gun.reload();
			//TODO wait in another thread until resuming shooting/moving 
		}
			
	}

	public void keyReleased(KeyEvent e) {
		// Invoked when a key has been released.
	}

	public void keyTyped(KeyEvent e) {
		// Invoked when a key has been typed.
		
	}
}// endclass BBPanel
