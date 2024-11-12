package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.ElitePlus;
import edu.hitsz.aircraft.Enemy;

public class ElitePlusFactory implements EnemyFactory {
    @Override
    public Enemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power){
        return new ElitePlus(locationX,locationY,speedX,speedY,hp,power);
    }

}
