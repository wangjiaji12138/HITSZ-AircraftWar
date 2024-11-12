package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategy.EnemyCircleShoot;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public class BossEnemy extends Enemy{
    private int power =30;
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power){
        super(locationX,locationY,speedX, speedY,hp,power);
        this.power = power;
    }
    private int direction =1;

    public int getDirection(){
        return direction;
    }

    @Override
    public List<BaseBullet> shoot() {
        this.setShootStrategy(new EnemyCircleShoot());
        return this.executeShootStrategy(
                getDirection(), getLocationX(),getLocationY(),20,power
        );
    }
    @Override
    public int update(){
        return 0;
    }
}
