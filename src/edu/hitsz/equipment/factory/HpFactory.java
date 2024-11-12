package edu.hitsz.equipment.factory;

import edu.hitsz.equipment.BaseEquipment;
import edu.hitsz.equipment.HpEquipment;

public class HpFactory implements EquipmentFactory{
    @Override
    public BaseEquipment createEquipment(int locationX, int locationY, int speedX, int speedY){
        return new HpEquipment(locationX,locationY,speedX,speedY);
    }
}
