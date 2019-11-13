package dk.hejselbak.weapon;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class WeaponService {

    private final SortedSet<Weapon> weapons = new TreeSet<Weapon>();
    private final Logger log = LoggerFactory.getLogger(WeaponService.class);

    public WeaponService() {
        initiateWeapons();
    }

    public SortedSet<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getWeapon(int id) {
        Weapon result = null;
        for (Weapon w : weapons) {
            if (w.getId() == id) {
              result = w;
              break;
            }
        }
        return result;
    }

    public AttackTableEntry hit(Weapon weapon, int at, int roll) {
        AttackTableEntry[][] table = weapon.getAttackTable();
        AttackTableEntry result;

        do {
        result = table[at-1][roll-1];
        roll--;
        } while (result == null && roll > 1);

        return (result != null) ? result : AttackTableEntry.NO_HIT;
    }

    private void initiateWeapons() {
        AttackTableEntry[][] table;
    
        log.debug("Initialising the weapons...");
    
        table = new AttackTableBuilder().
          addEntry(20, 40, 1).addEntry(20, 77, 2).addEntry(20, 114, 3).addEntry(20, 145, 3, "A", "K").addEntry(20, 146, 3, "A", "P").addEntry(20, 149, 3, "B", "P").addEntry(20, 150, 3, "C", "P").
          addEntry(19, 50, 1).addEntry(19, 75, 2).addEntry(19, 100, 3).addEntry(19, 125, 4).addEntry(19, 140, 4, "A", "K").addEntry(19, 141, 4, "A", "P").addEntry(19, 146, 4, "B", "P").addEntry(19, 149, 4, "C", "P").
          addEntry(18, 60, 1).addEntry(18, 82, 2).addEntry(18, 105, 3).addEntry(18, 128, 4).addEntry(18, 130, 4, "A", "K").addEntry(18, 131, 4, "A", "P").addEntry(18, 132, 4, "A", "S").addEntry(18, 133, 4, "A", "P").addEntry(18, 134, 4, "A", "S").addEntry(18, 135, 4, "A", "P").addEntry(18, 136, 4, "A", "S").addEntry(18, 137, 4, "A", "P").addEntry(18, 138, 4, "A", "S").addEntry(18, 139, 4, "A", "P").addEntry(18, 140, 4, "B", "P").addEntry(18, 145, 4, "C", "P").addEntry(18, 149, 4, "D", "P").addEntry(18, 150, 4, "E", "P").
          addEntry(1, 90, 7).addEntry(1, 95, 8, "A", "S").addEntry(1, 150, 18, "E", "S").
          build();
        weapons.add(new Weapon(0, "Dagger", WeaponGroup.ONE_HANDED_SLASHING, 1, table, new RangeBuilder().addRange(-10, 1, 10).addRange(-20, 11, 25).addRange(-30, 26, 50).build()));
    
        table = new AttackTableBuilder().addEntry(1, 85, 8, "A", "K").addEntry(1, 150, 34, "E", "S").addEntry(20, 30, 1).addEntry(20, 64, 5).addEntry(20, 150, 14, "E", "K").build();
        weapons.add(new Weapon(1, "Falchion", WeaponGroup.ONE_HANDED_SLASHING, 5, table));
        weapons.add(new Weapon(2, "Hand axe", WeaponGroup.ONE_HANDED_SLASHING, 4, new RangeBuilder().addRange(-15, 1, 10).addRange(-90, 51, 100).addRange(-30, 11, 25).addRange(-45, 26, 50).build()));
        weapons.add(new Weapon(3, "Maine Gauche", WeaponGroup.ONE_HANDED_SLASHING, 2, new RangeBuilder().addRange(-15, 1, 10).build()));
        weapons.add(new Weapon(4, "Scimitar", WeaponGroup.ONE_HANDED_SLASHING, 4));
        weapons.add(new Weapon(5, "Rapier", WeaponGroup.ONE_HANDED_SLASHING, 4));
        weapons.add(new Weapon(6, "Broadsword", WeaponGroup.ONE_HANDED_SLASHING, 3));
        weapons.add(new Weapon(7, "Short Sword", WeaponGroup.ONE_HANDED_SLASHING, 2, new RangeBuilder().addRange(-30, 1, 10).build()));
        weapons.add(new Weapon(8, "Armored fist", WeaponGroup.ONE_HANDED_CONCUSSION, 1));
        weapons.add(new Weapon(9, "Club", WeaponGroup.ONE_HANDED_CONCUSSION, 4, new RangeBuilder().addRange(-40, 1, 10).build()));
        weapons.add(new Weapon(10, "War Hammer", WeaponGroup.ONE_HANDED_CONCUSSION, 4, new RangeBuilder().addRange(-20, 1, 10).addRange(-40, 11, 25).addRange(-60, 26, 50).build()));
        weapons.add(new Weapon(11, "Mace", WeaponGroup.ONE_HANDED_CONCUSSION, 2, new RangeBuilder().addRange(-35, 1, 10).build()));
        weapons.add(new Weapon(12, "Morning star", WeaponGroup.ONE_HANDED_CONCUSSION, 8));
        weapons.add(new Weapon(13, "Whip", WeaponGroup.ONE_HANDED_CONCUSSION, 6));
        weapons.add(new Weapon(14, "Bola", WeaponGroup.MISSILE, 7, new RangeBuilder().addRange(0, 1, 50).addRange(-20, 51, 100).addRange(-40, 101, 150).build()));
        weapons.add(new Weapon(15, "Composite bow", WeaponGroup.MISSILE, 4, new RangeBuilder().addRange(25, 1, 10).addRange(0, 11, 100).addRange(-35, 101, 200).addRange(-60, 201, 300).build()));
        weapons.add(new Weapon(16, "Heavy crossbow", WeaponGroup.MISSILE, 5, new RangeBuilder().addRange(30, 1, 20).addRange(0, 21, 100).addRange(-25, 101, 200).addRange(-40, 201, 300).addRange(-55, 301, 360).build()));
        weapons.add(new Weapon(17, "Light crossbow", WeaponGroup.MISSILE, 5, new RangeBuilder().addRange(15, 1, 10).addRange(0, 11, 100).addRange(-35, 101, 200).addRange(-50, 201, 300).addRange(-75, 301, 360).build()));
        weapons.add(new Weapon(18, "Long bow", WeaponGroup.MISSILE, 5, new RangeBuilder().addRange(20, 1, 10).addRange(0, 11, 100).addRange(-30, 101, 200).addRange(-40, 201, 300).addRange(-50, 301, 400).build()));
        weapons.add(new Weapon(19, "Short bow", WeaponGroup.MISSILE, 4, new RangeBuilder().addRange(10, 1, 10).addRange(0, 11, 100).addRange(-40, 101, 180).addRange(-70, 181, 240).build()));
        weapons.add(new Weapon(20, "Sling", WeaponGroup.MISSILE, 6, new RangeBuilder().addRange(15, 1, 10).addRange(0, 11, 60).addRange(-40, 61, 120).addRange(-65, 121, 180).build()));
        weapons.add(new Weapon(21, "Battle axe", WeaponGroup.TWO_HANDED, 5));
        weapons.add(new Weapon(22, "Flail", WeaponGroup.TWO_HANDED, 8));
        weapons.add(new Weapon(23, "War mattock", WeaponGroup.TWO_HANDED, 6));
        weapons.add(new Weapon(24, "Quarterstaff", WeaponGroup.TWO_HANDED, 3));
        weapons.add(new Weapon(25, "Two-handed sword", WeaponGroup.TWO_HANDED, 5));
        weapons.add(new Weapon(26, "Javelin", WeaponGroup.POLE_ARM, 4));
        weapons.add(new Weapon(27, "Lance", WeaponGroup.POLE_ARM, 7));
        weapons.add(new Weapon(28, "Pole arm", WeaponGroup.POLE_ARM, 7));
        weapons.add(new Weapon(29, "Spear", WeaponGroup.POLE_ARM, 5, new RangeBuilder().addRange(-10, 1, 10).addRange(-20, 11, 25).addRange(-30, 26, 50).build()));
    
        log.debug("[DONE] Initialising the weapons...");
      }

}