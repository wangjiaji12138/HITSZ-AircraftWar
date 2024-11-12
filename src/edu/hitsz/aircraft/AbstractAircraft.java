package edu.hitsz.aircraft;

import edu.hitsz.aircraft.strategy.ShootStrategy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;
    private int power;
    /**
     * 子弹伤害
     */
    /**
     * 子弹射击方向 (向上发射：-1，向下发射：1)
     */
    private int direction = -1;
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hp = hp;
        this.maxHp = hp;
        this.power = power;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }
    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    private ShootStrategy shootStrategy;
    public void setShootStrategy(ShootStrategy shootStrategy){
        this.shootStrategy = shootStrategy;
    }
    public abstract List<BaseBullet> shoot();
    public List<BaseBullet> executeShootStrategy(int direction,int locationX, int locationY,int speedY,int power){
        return shootStrategy.executeShootStrategy(direction,locationX,locationY,speedY,power);

    }
    public void setPower(int power){this.power = power;}
    public void setSpeedY(int speedY){this.speedY = speedY;}
}


