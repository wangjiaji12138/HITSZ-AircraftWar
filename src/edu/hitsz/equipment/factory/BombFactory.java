package edu.hitsz.equipment.factory;

import edu.hitsz.equipment.BaseEquipment;
import edu.hitsz.equipment.BombEquipment;

public class BombFactory implements EquipmentFactory{
    @Override
    public BaseEquipment createEquipment(int locationX, int locationY, int speedX, int speedY){
        return new BombEquipment(locationX,locationY,speedX,speedY);
    }
}
