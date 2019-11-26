package dk.hejselbak.weapon;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
        return em.find(Weapon.class, id);
    }

    public AttackTableEntry hit(Weapon weapon, int at, int roll) {
        List<AttackTableEntry> table = weapon.getAttackTable();

        if (table == null || table.size() == 0) {
            log.debug("No HIT on weapon (" + weapon.getName() + ") attackroll (at:" + at + ", roll:" + roll + "). ");
            return AttackTableEntry.NO_HIT;
        }
        Optional<AttackTableEntry> result = table.stream().filter(entry -> entry.getArmourType() == at && roll >= entry.getRoll()).findFirst();
        return result.isPresent() ? result.get() : AttackTableEntry.NO_HIT;
    }
}