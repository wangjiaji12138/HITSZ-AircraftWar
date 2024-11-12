package edu.hitsz.equipment;
import edu.hitsz.aircraft.HeroAircraft;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FirePlusEquipment extends BaseEquipment {
    private static int equipmentNum=0;

    public FirePlusEquipment(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public int active(HeroAircraft heroAircraft) {
        System.out.println("超级火力道具生效");
        equipmentNum+=1;
        heroAircraft.setFiringPlus();
        Runnable runnable = () ->{
            try {
                while(equipmentNum>0) {
                    Thread.sleep(3000);
                    equipmentNum -= 1;
                }
                heroAircraft.closeFiringPlus();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
        return 0;
    }
}
