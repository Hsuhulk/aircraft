import java.awt.image.BufferedImage;
import java.util.Timer;

public class Bullet extends Sprite {
    protected int _x =0;
    protected int _y =0;
	public Bullet(MainScene scene, String img_path, int width, int height) {
		super(scene, img_path, width, height);
		Timer bullet_timer = new Timer();
		bullet_timer.schedule(new bulletTimer(), 0 , 8);
		
	}

	public Bullet(MainScene scene, BufferedImage img, int width, int height) {
		super(scene, img, width, height);
		Timer bullet_timer = new Timer();
		bullet_timer.schedule(new bulletTimer(), 0 , 8);
	}
	
	public class bulletTimer extends java.util.TimerTask{
		@Override
		public void run() {
			setPosition(get_x(),get_y() - MainScene.Velocity_Bullet );
		}
		
	}

}
