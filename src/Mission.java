import java.awt.Dimension;

import javax.swing.*;

public class Mission {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO
		// create screen
		// balls move down the screen
		// when they hit earth they reduce your points count
		// (and crush your buildings)
		// shoot with your cannon
		// blast all balls to go to next level
		JFrame win = new JFrame("Bouncing Ball with a Gun");
		Dimension d = new Dimension(400, 300);

		win.setPreferredSize(d);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		win.setContentPane(new BBPanel());

		win.pack();
		win.setVisible(true);
	}

}
