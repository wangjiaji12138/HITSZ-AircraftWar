package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.factory.*;
import java.awt.*;
import java.util.List;


/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class DifficultGame extends Game {
    /*
   困难模式
   1.敌机数量较多 = 10 DONE
   2.boss敌机血量随已产生boss数变大(无上限) DONE
   3.产生敌机周期随已产生boss数变小 DONE
   4.敌机power随已产生boss数变大(无上限)
   5.敌机速度较快 speedY=20 DONE
   6.一般敌机死亡俯冲 DONE
    */
    private int createdBossNum = 1;
    private int enemyPower = 20;
    private int enemySpeedY = 13;
    private int enemyHp = 30;
    @Override
    public List<AbstractAircraft> createBossEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory= new BossEnemyFactory();
        createdBossNum+=1;
        Enemy bossEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)((Math.random()-0.5)*10),
                0,
                150+createdBossNum*enemyHp,
                createdBossNum*enemyPower);
        enemyAircrafts.add(bossEnemy);
        System.out.println("Boss卷土重来！游戏难度提高！目前Boss机血量"+bossEnemy.getHp()+",敌机周期"+
                this.getCycleDuration()+",敌机子弹伤害"+createdBossNum*10+enemyPower);
        return enemyAircrafts;
    }
    public List<AbstractAircraft> createMobEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new MobEnemyFactory();
        Enemy mobEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                10+enemySpeedY, enemyHp, enemyPower);
        enemyAircrafts.add(mobEnemy);
        return enemyAircrafts;
    }
    public List<AbstractAircraft> createEliteEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new EliteEnemyFactory();
        Enemy eliteEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)(Math.random()-0.5)*10,
                enemySpeedY, enemyHp,enemyPower+createdBossNum*10);
        enemyAircrafts.add(eliteEnemy);
        return enemyAircrafts;
    }
    public List<AbstractAircraft> createElitePlusEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new ElitePlusFactory();
        Enemy elitePlusEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)(Math.random()-0.5)*10,
                enemySpeedY, enemyHp,createdBossNum*10+enemyPower);
        enemyAircrafts.add(elitePlusEnemy);
        return enemyAircrafts;
    }
    @Override
    public String setMode(){
        return "difficult";
    }
    @Override
    public int setEnemyMaxNumber(){
        return 10;
    }
    @Override
    public Image setBackgroundImage(){
        return ImageManager.DIFFICULT_MODE_BACKGROUND_IMAGE;
    }

    public int setCycleDuration(){
        if(createdBossNum<=4){
            return 600-50*createdBossNum;
        }
        else{
            return 400;
        }
    }
}

