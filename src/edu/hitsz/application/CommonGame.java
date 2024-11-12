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
public class CommonGame extends Game {
    /*
   一般模式
   敌机数量适中 = 7 DONE
   boss敌机血量不变 = 180 DONE
   产生敌机周期随时间变化 = 600-450 DONE
   敌机power随随已产生boss数增大 30-60 DONE
   敌机速度较快 speedY=12 DONE
    */
    private int createdBossNum = 1;
    private int enemyPower = 20;
    private int enemyHp = 30;
    private int enemySpeedY = 12;
    private String mode = "common";
    private int enemyMaxNumber = 7;
    private Image backgroundImage = ImageManager.COMMON_MODE_BACKGROUND_IMAGE;
    @Override
    public List<AbstractAircraft> createBossEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory= new BossEnemyFactory();
        Enemy bossEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)((Math.random()-0.5)*15),
                0,
                4*enemyHp,
                enemyPower+createdBossNum*10);
        enemyAircrafts.add(bossEnemy);
        System.out.println("Boss卷土重来！游戏难度提高！目前敌机周期"+
                this.getCycleDuration()+",敌机子弹伤害"+createdBossNum*10+enemyPower);
        if(createdBossNum<=3){createdBossNum+=1;}
        return enemyAircrafts;
    }
    public List<AbstractAircraft> createMobEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new MobEnemyFactory();
        Enemy mobEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                enemySpeedY,
                enemyHp,
                30+createdBossNum*10);
        enemyAircrafts.add(mobEnemy);
        return enemyAircrafts;
    }
    public List<AbstractAircraft> createEliteEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new EliteEnemyFactory();
        Enemy eliteEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)((Math.random()-0.5)*10),
                enemySpeedY,
                enemyHp,
                30+createdBossNum*10);
        enemyAircrafts.add(eliteEnemy);
        return enemyAircrafts;
    }
    public List<AbstractAircraft> createElitePlusEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new ElitePlusFactory();
        Enemy elitePlusEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)((Math.random()-0.5)*10),
                enemySpeedY,
                enemyHp,
                30+createdBossNum*10);
        enemyAircrafts.add(elitePlusEnemy);
        return enemyAircrafts;
    }
    @Override
    public String setMode(){
        return "common";
    }
    @Override
    public int setEnemyMaxNumber(){
        return 7;
    }
    @Override
    public Image setBackgroundImage(){
        return ImageManager.COMMON_MODE_BACKGROUND_IMAGE;
    }

    public int setCycleDuration(){
        return 650-50*createdBossNum;
    }
}

