package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.basic.BombObserver;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;
import java.util.Observer;

public abstract class Enemy extends AbstractAircraft implements BombObserver {

    public Enemy(int locationX, int locationY, int speedX, int speedY, int hp,int power){
        super(locationX, locationY, speedX, speedY,hp,power);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
    @Override
    public int update(){
        this.vanish();
        return 10;
    }

}
