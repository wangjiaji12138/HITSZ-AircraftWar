package edu.hitsz.equipment;

import edu.hitsz.aircraft.HeroAircraft;

public class HpEquipment  extends BaseEquipment{
    public HpEquipment(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);

    }
    @Override
    public int active(HeroAircraft heroAircraft){
        System.out.println("加血道具生效，生命值恢复100");
        heroAircraft.decreaseHp(-100);
        if(heroAircraft.getHp()>=1000) {
            heroAircraft.decreaseHp(heroAircraft.getHp() - 1000);
        }
        return 0;
    }

}
