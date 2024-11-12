package edu.hitsz.equipment.factory;

import edu.hitsz.equipment.BaseEquipment;

public interface EquipmentFactory {
    public abstract BaseEquipment createEquipment(int locationX, int locationY, int speedX, int speedY);

    }

