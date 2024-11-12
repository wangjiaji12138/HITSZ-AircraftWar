package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.Enemy;

public interface EnemyFactory {
    public abstract Enemy createEnemy(int locationX, int locationY, int speedX, int speedY, int hp,int power);
}
