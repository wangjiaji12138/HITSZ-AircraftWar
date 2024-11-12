package edu.hitsz.aircraft.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class HeroStraightShoot implements ShootStrategy{
    private int direciton;
    private int locationX;
    private int locationY;
    private int speedY;
    private int power;
    public HeroStraightShoot(int direction,int locationX,int locationY,int speedY,int power){
        this.direciton =direction;
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedY = speedY;
        this.power = power;
    }
    @Override
    public List<BaseBullet> executeShootStrategy(int direciton,int locationX,int locationY,int speedY,int power){
        List<BaseBullet> res = new LinkedList<>();
        int shootNum = 1;
        int speedX = 0;
        speedY = speedY*direciton;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new HeroBullet(locationX, locationY+30*direciton, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
