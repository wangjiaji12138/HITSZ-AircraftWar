package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.Enemy;

public class EliteEnemyFactory implements EnemyFactory {
    @Override
    public Enemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power){
        return new EliteEnemy(locationX,locationY,speedX,speedY,hp,power);
    }
}
