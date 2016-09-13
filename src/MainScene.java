import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



/**
 * Created by Matt on 2016/8/8.
 */
public class MainScene implements KeyListener{
    class RenderLayer{
        public Sprite Sprite;
        public int Layer;
        
        public RenderLayer(Sprite sprite, int layer){
            Sprite = sprite;
            Layer = layer;
        }

        @Override
        public int hashCode() {
            return Sprite.hashCode();
        }
    }

    public static final int Velocity_Fighter = 4;
    public static final int Velocity_Bullet = 7;
    public static final int Velocity_Bullet_Enemy = 3;
    public static final int Velocity_Enemy = 1;

    private List<RenderLayer> _render_objects = new CopyOnWriteArrayList<>();
    private Insets _rect = null;
    public Insets get_rect(){return _rect;}
    private List<Integer> _list_keys = new ArrayList<Integer>();
    private Fighter _fighter;
    public MainScene() {
        _rect = new Insets(0, 0, main.WINDOWS_HEIGHT, main.WINDOWS_WIDTH);

        Sprite _sprite_bg = new Sprite(this, "res\\bg.png", main.WINDOWS_WIDTH, main.WINDOWS_HEIGHT);
        _sprite_bg.setPosition(main.WINDOWS_WIDTH / 2, main.WINDOWS_HEIGHT / 2);
        addToScene(_sprite_bg);
        
        Sprite _sprite_bg2 = new Sprite(this, "res\\bg.png", main.WINDOWS_WIDTH, main.WINDOWS_HEIGHT);
        _sprite_bg2.setPosition(main.WINDOWS_WIDTH / 2 , main.WINDOWS_HEIGHT * 3 / 2 - 10);
        addToScene(_sprite_bg2);
        
        
        Timer bg_timer = new Timer();
        bg_timer.schedule(new myTimer(_sprite_bg), 0 , 8);
        bg_timer.schedule(new myTimer(_sprite_bg2), 0 , 8);

        int left = 80;
        Sprite _enemy1 = new Sprite(this, "res\\enemy1.png", 80, 80);
        _enemy1.setPosition(left, main.WINDOWS_HEIGHT / 2);
        addToScene(_enemy1);
        left+=80;

        Sprite _enemy2 = new Sprite(this, "res\\enemy2.png", 80, 80);
        _enemy2.setPosition(left, main.WINDOWS_HEIGHT / 2);
        addToScene(_enemy2);
        left+=80;

        Sprite _enemy3 = new Sprite(this, "res\\enemy3.png", 80, 80);
        _enemy3.setPosition(left, main.WINDOWS_HEIGHT / 2);
        addToScene(_enemy3);
        left+=80;

        Sprite _enemy4 = new Sprite(this, "res\\enemy4.png", 80, 80);
        _enemy4.setPosition(left, main.WINDOWS_HEIGHT / 2);
        addToScene(_enemy4);
        left+=80;

        Sprite _bullet = new Sprite(this, "res\\bullet.png", 16, 20);
        _bullet.setPosition(left, main.WINDOWS_HEIGHT / 2);
        addToScene(_bullet);

        _fighter = new Fighter(this, "res\\fighter.png", 90, 60, 3);
        SpawnFighter();
        addToScene(_fighter);
    }

    //重置飛機位置
    private void SpawnFighter(){
        _fighter.setPosition(main.WINDOWS_WIDTH / 2, main.WINDOWS_HEIGHT - 60);
    }


    //更新畫面
    private void updateFrame(){
        main.clearSprite();

        List<RenderLayer> render_objs = _render_objects;

        Collections.sort(render_objs,
                new Comparator<RenderLayer>() {
                    public int compare(RenderLayer o1, RenderLayer o2) {
                        return o1.Layer - o2.Layer;
                    }
                });

        for(RenderLayer renderLayer : render_objs)
            main.addSprite(renderLayer.Sprite);
    }
    
    //更新
    public void update(){
        updateFrame();
    }

    //移除場景物件
    private void removeFromScene(Sprite sprite){
        for(int i=0; i<_render_objects.size(); i++)
        if(_render_objects.get(i).hashCode()==sprite.hashCode())
            _render_objects.remove(i);
    }

    //加入場景
    private void addToScene(Sprite sprite){
        addToScene(sprite, 0);
    }

    private void addToScene(Sprite sprite, int layer){
        _render_objects.add(new RenderLayer(sprite, layer));
    }

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!_list_keys.contains(e.getKeyCode()))
			_list_keys.add(new Integer(e.getKeyCode()));
		_fighter_move();
//		System.out.println(_list_keys);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		_list_keys.remove(new Integer(e.getKeyCode()));
	}
    
    private void _fighter_move(){
    	
    	if(_list_keys.contains(KeyEvent.VK_LEFT))
    		_fighter.setPosition(_fighter.get_position().x-Velocity_Fighter, _fighter.get_position().y);
    	if(_list_keys.contains(KeyEvent.VK_UP))
    		_fighter.setPosition(_fighter.get_position().x, _fighter.get_position().y-Velocity_Fighter);
    	if(_list_keys.contains(KeyEvent.VK_RIGHT))
    		_fighter.setPosition(_fighter.get_position().x+Velocity_Fighter, _fighter.get_position().y);
    	if(_list_keys.contains(KeyEvent.VK_DOWN))
    		_fighter.setPosition(_fighter.get_position().x, _fighter.get_position().y+Velocity_Fighter);
    	if(_list_keys.contains(KeyEvent.VK_Z)){
    		Bullet _bullet_bg = new Bullet(this, "res\\bullet.png", 16, 20);
    		_bullet_bg.setPosition(_fighter.get_position().x, _fighter.get_position().y);
            addToScene(_bullet_bg);
    	}    		
    }  
    public class myTimer extends java.util.TimerTask{
    	Sprite sprite;
    	myTimer(Sprite sprite){
    		this.sprite = sprite;
    	}
		@Override
		public void run() {
			sprite.setPosition(sprite.get_x(),sprite.get_y() - MainScene.Velocity_Bullet );
			if(sprite.get_y() <= -344){
				sprite.setPosition(main.WINDOWS_WIDTH / 2, main.WINDOWS_HEIGHT * 3 / 2 - 30 );
			}
		}
		
	}

}
