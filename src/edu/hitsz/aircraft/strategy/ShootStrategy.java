package edu.hitsz.aircraft.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {
    public List<BaseBullet> executeShootStrategy(int direction,int locationX,int locationY,int speedY,int power);
}
