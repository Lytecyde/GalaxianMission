import java.awt.Graphics;
import java.util.LinkedList;


public class Explosion {
	int x,y;
	int size;
	public int explosionDuration = 15;
	public LinkedList<Pellet> explosionParticles = new LinkedList<Pellet>();
	public Explosion(int bx, int by){
		x = bx;
		y = by;
	}
	// ============================================================== explode
	public void explode() {
		// break into falling pellets
		Data.nofExplosions++;
		for(int i = 0;i < 24;i++){
			explosionParticles.add(new Pellet(x, y, 
					(int)(Math.random()*6)-3, (int)(Math.random()*6)-3));			
		}
		Data.expls.add(this);
	}
	//=============================================================== remove explosion
	public void removeExplosion(){
		if(this.explosionDuration < 1){
			explosionParticles.clear();	
			Data.nofExplosions--;
			//should not go below zero
		}
		
	}
}//end of class
