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
public class EasyGame extends Game {
    /*
       简单模式
       1.敌机数量较低 = 5 DONE
       2.无boss敌机 DONE
       3.产生敌机周期不随时间变化 = 600 DONE
       4.敌机power不变 =30 DONE
       5.敌机速度较慢 speedY=10 DONE
     */
    private int enemyPower = 30;
    private int enemySpeedY = 10;
    private int enemyHp = 30;
    @Override
    public List<AbstractAircraft> createBossEnemy(List<AbstractAircraft> enemyAircrafts){
        return enemyAircrafts;
    }

    public List<AbstractAircraft> createMobEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new MobEnemyFactory();
        Enemy mobEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                enemySpeedY, enemyHp,enemyPower);
        enemyAircrafts.add(mobEnemy);
        return enemyAircrafts;
    }
    public List<AbstractAircraft> createEliteEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new EliteEnemyFactory();
        Enemy eliteEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)((Math.random()-0.5)*10),
                enemySpeedY, enemyHp,enemyPower);
        enemyAircrafts.add(eliteEnemy);
        return enemyAircrafts;
    }
    public List<AbstractAircraft> createElitePlusEnemy(List<AbstractAircraft> enemyAircrafts){
        EnemyFactory enemyFactory = new ElitePlusFactory();
        Enemy elitePlusEnemy = enemyFactory.createEnemy( (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_PLUS_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)((Math.random()-0.5)*10),
                enemySpeedY, enemyHp,enemyPower);
        enemyAircrafts.add(elitePlusEnemy);
        return enemyAircrafts;
    }
    @Override
    public String setMode(){
        return "easy";
    }
    @Override
    public int setEnemyMaxNumber(){
        return 5;
    }
    @Override
    public Image setBackgroundImage(){
        return ImageManager.EASY_MODE_BACKGROUND_IMAGE;
    }
    @Override
    public int setCycleDuration(){
        return 600;
    }
}
