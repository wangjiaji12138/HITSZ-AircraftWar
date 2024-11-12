package edu.hitsz.application;


import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.equipment.BombEquipment;
import edu.hitsz.equipment.FireEquipment;
import edu.hitsz.equipment.FirePlusEquipment;
import edu.hitsz.equipment.HpEquipment;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage TEST_MODE_BACKGROUND_IMAGE;
    public static BufferedImage EASY_MODE_BACKGROUND_IMAGE;
    public static BufferedImage COMMON_MODE_BACKGROUND_IMAGE;
    public static BufferedImage DIFFICULT_MODE_BACKGROUND_IMAGE;
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage ELITE_PLUS_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;
    public static BufferedImage FIRE_EQUIPMENT_IMAGE;
    public static BufferedImage FIRE_PLUS_EQUIPMENT_IMAGE;
    public static BufferedImage BOMB_EQUIPMENT_IMAGE;
    public static BufferedImage HP_EQUIPMENT_IMAGE;

    static {
        try {
            TEST_MODE_BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            EASY_MODE_BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            COMMON_MODE_BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
            DIFFICULT_MODE_BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            ELITE_PLUS_IMAGE= ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));
            FIRE_EQUIPMENT_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            FIRE_PLUS_EQUIPMENT_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bulletPlus.png"));
            BOMB_EQUIPMENT_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            HP_EQUIPMENT_IMAGE = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(ElitePlus.class.getName(), ELITE_PLUS_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FireEquipment.class.getName(), FIRE_EQUIPMENT_IMAGE);
            CLASSNAME_IMAGE_MAP.put(FirePlusEquipment.class.getName(), FIRE_PLUS_EQUIPMENT_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BombEquipment.class.getName(), BOMB_EQUIPMENT_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HpEquipment.class.getName(), HP_EQUIPMENT_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

}
