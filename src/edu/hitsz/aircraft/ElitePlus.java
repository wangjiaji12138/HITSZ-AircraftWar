package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategy.EnemyScatterShoot;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class ElitePlus extends EliteEnemy{
    private int power;
    public ElitePlus(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX,locationY,speedX, speedY,hp,power);
        this.power = power;
    }


    @Override
    public List<BaseBullet> shoot() {
        this.setShootStrategy(new EnemyScatterShoot());
        return this.executeShootStrategy(getDirection(), getLocationX(),getLocationY(),getSpeedY()+5,power);
    }
    @Override
    public int update(){
        this.decreaseHp(30);
        if(this.hp<=0){
            return 10;
        }
        else{
            return 0;
        }
    }
}
