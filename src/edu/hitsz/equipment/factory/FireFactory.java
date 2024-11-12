package edu.hitsz.equipment.factory;

import edu.hitsz.equipment.BaseEquipment;
import edu.hitsz.equipment.FireEquipment;

public class FireFactory implements EquipmentFactory{
    @Override
    public BaseEquipment createEquipment(int locationX, int locationY, int speedX, int speedY){
        return new FireEquipment(locationX,locationY,speedX,speedY);
    }
}