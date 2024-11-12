package edu.hitsz.equipment;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.BombObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class BombEquipment extends BaseEquipment{

    public BombEquipment(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    private List<BombObserver> bombObservers = new ArrayList<>();

    public void addObserver(BombObserver bombObserver){
        bombObservers.add(bombObserver);
    }
    public void removeObserver(BombObserver bombObserver){
        bombObservers.remove(bombObserver);
    }

    @Override
    public int active(HeroAircraft heroAircraft){
        int addScore = 0;
        System.out.println("炸弹道具生效");
        for(BombObserver bombObserver :bombObservers){
            addScore +=bombObserver.update();
        }
        return addScore;
    }

}
