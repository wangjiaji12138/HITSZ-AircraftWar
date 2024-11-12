package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.dao.Player;
import edu.hitsz.dao.PlayerDaoImpl;
import edu.hitsz.equipment.BaseEquipment;
import edu.hitsz.equipment.BombEquipment;
import edu.hitsz.equipment.factory.*;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static edu.hitsz.application.ImageManager.EASY_MODE_BACKGROUND_IMAGE;
import static edu.hitsz.application.Main.cardLayout;

/**
* 游戏主面板，游戏启动
*
* @author hitsz
*/
public abstract class Game extends JPanel {
    private int backGroundTop = 0;
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private  List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseEquipment> equipments;

    /**
     * 屏幕中出现的敌机最大数量
     */

    //判断是否有boss存在
    private boolean bossNotValid= true;

    private String playerName = "NEWPBOY";
    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = setCycleDuration();
    private int cycleTime = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;
    protected MusicThread backgroundMusic;
    protected MusicThread backgroundMusicWithBoss;
    public boolean ifMusicOn = false;
    private String mode = "test";
    private Image backgroundImage = ImageManager.TEST_MODE_BACKGROUND_IMAGE;
    private int enemyMaxNumber = 5;
    private int scoreAround = 200;
    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        equipments = new LinkedList<>();
        enemyMaxNumber = setEnemyMaxNumber();
        mode = setMode();
        backgroundImage =setBackgroundImage();
        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                // 新敌机产生
                //创建Boss敌机
                if(score>=scoreAround && bossNotValid){
                    scoreAround+=200;
                    bossNotValid = false;
                    enemyAircrafts = createBossEnemy(enemyAircrafts);
                    if(mode != "easy"){
                    backgroundMusicWithBoss = new MusicThread("src/videos/bgm_boss.wav",true,ifMusicOn);
                    backgroundMusicWithBoss.start();
                    }
                }
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    double randomEnemySign = Math.random();
                    if(randomEnemySign<0.5) {//创建普通敌机
                       enemyAircrafts = createMobEnemy(enemyAircrafts);
                    }
                    else if(randomEnemySign<0.8){//创建精英敌机
                        enemyAircrafts = createEliteEnemy(enemyAircrafts);
                    }
                    else{//创建超级精英敌机
                        enemyAircrafts = createElitePlusEnemy(enemyAircrafts);
                    }
                }
                // 飞机射出子弹
                shootAction();
            }
            // 子弹移动
            bulletsMoveAction();

            //道具移动
            equipmentMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束

                executorService.shutdown();

                gameOverFlag = true;
                backgroundMusic.setMusicFlag(false);
                if(backgroundMusicWithBoss!=null){
                    backgroundMusicWithBoss.setMusicFlag(false);
                }
                MusicThread gameOverMusic = new MusicThread("src/videos/game_over.wav",false,ifMusicOn);
                gameOverMusic.start();
                System.out.println("Game Over!");
                Object[] options = {"Return to start Menu", "Record This battle"};
                int result = JOptionPane.showOptionDialog(null, "Game Over！", "Confirmation",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                // 根据用户的选择进行处理
                if (result == 0) {
                    heroAircraft.rebuildHeroAircraft();
                    Main.cardLayout.first(Main.cardPanel);

                } else if (result == 1) {
                    //跳转到排行榜
                    heroAircraft.rebuildHeroAircraft();
                    ScoreTable scoreTable = new ScoreTable(mode);
                    Main.cardPanel.add(scoreTable.getMainPanel());
                    cardLayout.last(Main.cardPanel);
                    String userInput = JOptionPane.showInputDialog(null, "请输入您的名字：");
                    if (userInput != null && !userInput.isEmpty()) {
                        playerName = userInput;
                        String relativePath = System.getProperty("user.dir");
                        String filePath = relativePath+"/"+mode+"ModeScore.txt";

                        PlayerDaoImpl Impl = new PlayerDaoImpl();
                        List<Player> playerList  = Impl.getPlayersFromFile(Impl,filePath);

                        Player newPlayer = new Player(Impl.getAllPlayers().size(),playerName,score,Impl.CurrentTime());
                        Impl.doAdd(playerList,newPlayer);

                        //加入新成员的排序数组
                        List<Player> newSortedplayers = Impl.sortScore(Impl.getAllPlayers());
                        Impl.PrintScore(newSortedplayers);
                        Impl.writeFile(filePath,newPlayer.recordToString());
                        ScoreTable newscoreTable = new ScoreTable(mode);
                        Main.cardPanel.add(newscoreTable.getMainPanel());
                        cardLayout.last(Main.cardPanel);
                    }

                }
            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

        backgroundMusic = new MusicThread("src/videos/bgm.wav",true,ifMusicOn);
        backgroundMusic.start();
    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击 DONE
        for (AbstractAircraft enemyaircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyaircraft.shoot());
        }
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }
    private void equipmentMoveAction(){
        for(BaseEquipment equipment : equipments){
            equipment.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄 DONE
        for (BaseBullet enemyBullet : enemyBullets) {
            if (enemyBullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(enemyBullet)) {
                // 敌机撞击到英雄机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(enemyBullet.getPower());
                enemyBullet.vanish();}

        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    Thread bulletHitMusic = new MusicThread("src/videos/bullet_hit.wav",false,ifMusicOn);
                    bulletHitMusic.start();
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if(enemyAircraft instanceof BossEnemy){
                            backgroundMusicWithBoss.setMusicFlag(false);
                            cycleDuration =setCycleDuration();
                            System.out.println("恭喜你击退Boss！");
                        }
                        // TODO 获得分数，产生道具补给 DONE
                        score += 10;
                        //获取当前敌机位置和速度
                        int x = enemyAircraft.getLocationX();
                        int y = enemyAircraft.getLocationY();
                        int speedY = enemyAircraft.getSpeedY();
                        int equipmentDropChance=-1;
                        if(enemyAircraft.getClass()== MobEnemy.class){
                            equipmentDropChance = 0;
                        }
                        if(enemyAircraft.getClass()==EliteEnemy.class||enemyAircraft.getClass()== ElitePlus.class) {
                            if(Math.random()<0.5){
                            equipmentDropChance = 1;
                            }
                        }
                        else if(enemyAircraft.getClass()==BossEnemy.class){
                            equipmentDropChance = (int)(30*Math.random())/10;
                            speedY=10;
                            bossNotValid=true;
                        }
                        for(int i=0;i<equipmentDropChance;i++) {
                            EquipmentFactory equipmentFactory;
                            double randomEquipmentSign = Math.random();
                            if (randomEquipmentSign < 0.25) {
                                equipmentFactory = new BombFactory();
                                BaseEquipment baseEquipment = equipmentFactory.createEquipment(
                                        x+(int)(Math.random()*50),
                                        y+(int)(Math.random()*50), 0, speedY);
                                equipments.add(baseEquipment);
                            } else if (randomEquipmentSign > 0.25 && randomEquipmentSign < 0.5) {
                                equipmentFactory = new FireFactory();
                                BaseEquipment baseEquipment = equipmentFactory.createEquipment(
                                        x+(int)(Math.random()*50),
                                        y+(int)(Math.random()*50), 0, speedY);
                                equipments.add(baseEquipment);
                            } else if (randomEquipmentSign > 0.5 && randomEquipmentSign < 0.75) {
                                equipmentFactory = new HpFactory();
                                BaseEquipment baseEquipment = equipmentFactory.createEquipment(
                                        x+(int)(Math.random()*50),
                                        y+(int)(Math.random()*50), 0, speedY);
                                equipments.add(baseEquipment);
                            }
                            else if (randomEquipmentSign > 0.75 && randomEquipmentSign < 0.99) {
                                equipmentFactory = new FirePlusFactory();
                                BaseEquipment baseEquipment = equipmentFactory.createEquipment(
                                        x+(int)(Math.random()*50),
                                        y+(int)(Math.random()*50), 0, speedY);
                                equipments.add(baseEquipment);
                            }
                        }
                    }
                    // 英雄机 与 敌机 相撞，均损毁
                    if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                        enemyAircraft.vanish();
                        heroAircraft.decreaseHp(Integer.MAX_VALUE);
                    }
                }
            }

            // Todo: 我方获得道具，道具生效 DONE
            for (BaseEquipment equipment : equipments) {
                if (equipment.notValid()) {
                    continue;
                }

                if (equipment.crash(heroAircraft)) {
                    if(equipment instanceof BombEquipment){
                        MusicThread bombExplosionMusic = new MusicThread("src/videos/bomb_explosion.wav",false,ifMusicOn);
                        bombExplosionMusic.start();
                        for(AbstractAircraft enemyAircraft :enemyAircrafts) {
                            ((BombEquipment) equipment).addObserver((Enemy)enemyAircraft);
                        }
                        for(BaseBullet enemyBullet:enemyBullets){
                            ((BombEquipment) equipment).addObserver((EnemyBullet)enemyBullet);
                        }
                    }
                    else{
                    MusicThread getSupplyMusic = new MusicThread("src/videos/get_supply.wav",false,ifMusicOn);
                    getSupplyMusic.start();
                    }
                    score += equipment.active(heroAircraft);
                    equipment.vanish();
                }
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        equipments.removeIf(AbstractFlyingObject::notValid);
    }

    //TODO
    @Override
    public void paint(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(backgroundImage, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(backgroundImage, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g,equipments);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

   private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }
    public int getCycleDuration(){
        return cycleDuration;
    }
   private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
    //TODO DONE
    public abstract List<AbstractAircraft> createMobEnemy(List<AbstractAircraft> enemyAircrafts);
    public abstract List<AbstractAircraft> createEliteEnemy(List<AbstractAircraft> enemyAircrafts);
    public abstract List<AbstractAircraft> createElitePlusEnemy(List<AbstractAircraft> enemyAircrafts);
    public abstract List<AbstractAircraft>  createBossEnemy(List<AbstractAircraft> enemyAircrafts);
    public abstract String setMode();
    public abstract int setEnemyMaxNumber();
    public abstract Image setBackgroundImage();
    public abstract int setCycleDuration();
}
