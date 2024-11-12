package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.Enemy;

public class BossEnemyFactory implements EnemyFactory{
    @Override
    public Enemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power){
        return new BossEnemy(locationX,locationY,speedX,speedY,hp,power);
    }
}
