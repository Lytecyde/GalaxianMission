import java.awt.Color;
import java.awt.Graphics;

public class TakeOverRocket extends Rocket{
	public TakeOverRocket(int x, int y, int vx, int vy){
		m_x = x;
		m_y = y;
		m_velocityX = 0;
		m_velocityY = Data.velocityXRocket;
	}
	public void draw(Graphics g){
		g.setColor(Color.CYAN);
		g.fillOval(super.m_x, super.m_y, DIAMETER, 2*DIAMETER);
	}
}
