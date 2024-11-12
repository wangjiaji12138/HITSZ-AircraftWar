package edu.hitsz.aircraft.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class EnemyCircleShoot implements ShootStrategy {

    @Override
    public List<BaseBullet> executeShootStrategy(int direction,int locationX,int locationY,int speedY,int power){
        List<BaseBullet> res = new LinkedList<>();
        int shootNum = 20;
        int speedX = speedY;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            double angleInDegrees = 18*i;
            // 将角度调整到180度到360度之间
            angleInDegrees = (angleInDegrees >= 180) ? angleInDegrees - 360 : angleInDegrees;
            double angleInRadians = Math.toRadians(angleInDegrees); // 将角度转换为弧度
            double sinValue = Math.sin(angleInRadians);
            double cosValue = Math.cos(angleInRadians);
            // 多个子弹环形分散
            bullet = new EnemyBullet((int)(locationX + cosValue*10),
                    (int)(locationY+ sinValue*10),
                    (int)(cosValue*speedX),(int)(sinValue*speedY), power);
            res.add(bullet);
        }
        return res;
    }
}
