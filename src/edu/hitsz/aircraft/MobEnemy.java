package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategy.ShootStrategy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends Enemy {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX, locationY, speedX, speedY,hp,power);
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();
    }
}
