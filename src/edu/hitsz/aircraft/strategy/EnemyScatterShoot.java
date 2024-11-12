package edu.hitsz.aircraft.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class EnemyScatterShoot implements ShootStrategy{

    @Override
    public List<BaseBullet> executeShootStrategy(int direction,int locationX,int locationY,int speedY,int power) {
        List<BaseBullet> res = new LinkedList<>();
        int shootNum = 3;
        int speedX = 3;
        speedY = speedY*direction;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(
                    locationX + (i * 2 - shootNum + 1) * 10, locationY-10*direction,
                    (i-1)*speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }
}
