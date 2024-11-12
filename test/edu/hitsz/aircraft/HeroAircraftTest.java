package edu.hitsz.aircraft;

import edu.hitsz.aircraft.factory.EnemyFactory;
import edu.hitsz.aircraft.factory.MobEnemyFactory;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    @Test
    void crash() {
        //前提条件：已创建英雄机和敌机
        assertTrue(HeroAircraft.getHeroAircraft() != null);
        HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
        EnemyFactory enemyFactory = new MobEnemyFactory();
        Enemy enemy = enemyFactory.createEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int) ((Math.random() - 0.5) * 10),
                10,
                30,
                30);
        //
        enemy.setLocation(10, 20);
        heroAircraft.setLocation(10, 20);
        boolean condition = heroAircraft.crash(enemy);
        assertTrue(condition);
        System.out.println("crash test passed");
        if (!condition) {

            System.out.println("crash test failed");
        }
    }

    @Test
    void decreaseHp() {
        assertTrue(HeroAircraft.getHeroAircraft() != null);
        HeroAircraft heroAircraft = HeroAircraft.getHeroAircraft();
        int power=20;
        BaseBullet enemyBullet = new EnemyBullet(10,20,30,30,power);
        int HpBeforeCrash=heroAircraft.getHp();
        heroAircraft.crash(enemyBullet);
        heroAircraft.decreaseHp(enemyBullet.getPower());
        int HpAfterCrash=heroAircraft.getHp();
        //前后生命值相减等于子弹power才判定为真
        assertEquals(power,HpBeforeCrash-HpAfterCrash);
        if(power==HpBeforeCrash-HpAfterCrash) {
            System.out.println("decreaseHP test passed");
        }
        else {
            System.out.println("decreaseHP test failed");
        }
    }
}