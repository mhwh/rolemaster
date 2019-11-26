package dk.hejselbak.weapon;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class WeaponService {

    private final SortedSet<Weapon> weapons = new TreeSet<Weapon>();
    private final Logger log = LoggerFactory.getLogger(WeaponService.class);
    
    @Inject 
    private EntityManager em; 

    public WeaponService() {
    }

    public SortedSet<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getWeapon(int id) {
        Weapon weapon = em.find(Weapon.class, id);
        log.debug("Found " + weapon.toString());
        return weapon;
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
}