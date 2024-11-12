package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategy.EnemyStraightShoot;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class EliteEnemy extends Enemy{
    private int power =30;
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX,locationY,speedX, speedY,hp,power);
        this.power = power;
    }

    private int direction =1;

    public int getDirection(){
        return direction;
    }

    @Override
    public List<BaseBullet> shoot() {
        this.setShootStrategy(new EnemyStraightShoot());
        return this.executeShootStrategy(
                getDirection(), getLocationX(),getLocationY(),getSpeedY()+5,power
        );
    }
}
