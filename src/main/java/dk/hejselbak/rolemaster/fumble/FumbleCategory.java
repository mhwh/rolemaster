package dk.hejselbak.rolemaster.fumble;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum FumbleCategory {
    HAND_ARMS_ONE_HANDED, 
    HAND_ARMS_TWO_HANDED, 
    SPEAR_AND_POLE_ARMS, 
    MOUNTED_ARMS, 
    THROWN_ARMS, 
    BOWS
}