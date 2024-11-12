package edu.hitsz.equipment;


import edu.hitsz.aircraft.HeroAircraft;

public class FireEquipment extends BaseEquipment {
    private static int equipmentNum=0;
    public FireEquipment(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public int active(HeroAircraft heroAircraft){
        System.out.println("火力道具生效");
        equipmentNum+=1;
        heroAircraft.setFiring();
        Runnable runnable = () ->{
            try {
                while(equipmentNum>0) {
                    Thread.sleep(3000);
                    equipmentNum -= 1;
                }
                heroAircraft.closeFiring();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
        return 0;
    }
}
