package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.Enemy;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class MobEnemyFactory implements EnemyFactory {
    @Override
    public Enemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power){
        return new MobEnemy(locationX,locationY,speedX,speedY,hp,power);
    }
}
