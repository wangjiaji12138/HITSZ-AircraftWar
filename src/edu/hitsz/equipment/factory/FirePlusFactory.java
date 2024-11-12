package edu.hitsz.equipment.factory;

import edu.hitsz.equipment.BaseEquipment;
import edu.hitsz.equipment.FirePlusEquipment;

public class FirePlusFactory implements EquipmentFactory{
    @Override
    public BaseEquipment createEquipment(int locationX, int locationY, int speedX, int speedY){
        return new FirePlusEquipment(locationX,locationY,speedX,speedY);
    }
}