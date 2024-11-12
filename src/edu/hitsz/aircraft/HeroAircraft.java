package edu.hitsz.aircraft;
import edu.hitsz.aircraft.strategy.HeroCircleShoot;
import edu.hitsz.aircraft.strategy.HeroScatterShoot;
import edu.hitsz.aircraft.strategy.HeroStraightShoot;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {
    private volatile static HeroAircraft heroAircraft;
    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    private int power =30;
    private int speedY =20;
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp,int power) {
        super(locationX, locationY, speedX, speedY, hp,power);
    }
    public void rebuildHeroAircraft(){
        heroAircraft.decreaseHp(-1000);
        heroAircraft.closeFiringPlus();
        heroAircraft.closeFiring();
        heroAircraft.setLocation(Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight());
    }
    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft==null){
            synchronized (HeroAircraft.class){
                if(heroAircraft==null){
                    heroAircraft = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1000,30);
                }
            }
        }
        return heroAircraft;
    }

    private boolean isFiring = false;
    private boolean isFiringPlus = false;
    private int direction = -1;

    //返回子弹数
    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void setFiring(){
        isFiring = true;
    }
    public void closeFiring(){
        isFiring = false;
    }

    public void setFiringPlus(){
        isFiringPlus = true;
    }
    public void closeFiringPlus(){
        isFiringPlus = false;
    }
    public int getDirection(){
        return direction;
    }
    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        if(isFiringPlus){
            heroAircraft.setShootStrategy(new HeroCircleShoot(direction,locationX,locationY,speedY,power));
        }
        else if(isFiring){
            heroAircraft.setShootStrategy(new HeroScatterShoot(direction,locationX,locationY,speedY,power));
            Runnable r = () -> {
                try {
                    Thread.sleep(3000); // 线程睡眠3秒
                    heroAircraft.setShootStrategy(new HeroStraightShoot(direction,locationX,locationY,speedY,power));
                    System.out.println("火力状态结束");
                    isFiring =false;
                } catch (InterruptedException e) {e.printStackTrace();}
            };
            Thread thread = new Thread(r);
            thread.start();
        }
        else{
            heroAircraft.setShootStrategy(new HeroStraightShoot(direction,locationX,locationY,speedY,power));
        }
        return heroAircraft.executeShootStrategy(getDirection(), getLocationX(),getLocationY(),speedY,power);
    }
}
