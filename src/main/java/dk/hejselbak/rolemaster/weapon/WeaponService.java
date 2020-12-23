package dk.hejselbak.rolemaster.weapon;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class WeaponService {

    @Inject EntityManager em; 

    public SortedSet<Weapon> getWeapons() {
        log.debug("[" + System.currentTimeMillis() + "] getWeapons was called on WeaponService...");
        return new TreeSet<Weapon>(em.createQuery("select w from Weapon w", Weapon.class).getResultList());
    }

    public Weapon getWeapon(final int id) {
        return em.find(Weapon.class, id);
    }

    /**
     * Cross-index the attacks Table Type (Arms Law = AL)with the targets armour type
     * in the Armour DB mod area, subtract this from the IAV.
     * Then add the Weapon OB mod for the armour type. This is the FAN. If
     * the FAN is greater than the To Hit Threshold the attack does damage.
     * Criticals result if it has passed the appropriate threshold. To determine how
     * many hits occured divide (FAN-THT) by the Basic Hit Factor (The number in
     * brackets on the attack statistics area) DAMAGE =
     * ((OB-DB-ArmourMod+WeaponMod)-THT)/BHF
     *
     * @param weapon The weapon being used for this attack
     * @param at The AT of the target
     * @param roll The IAV. Roll the attack, check for fumble/failure, modify by OB/DB and other factors as usual. This is the IAV.
     *
     * This method returns a AttackResult instance.
     * @see AttackResult
     */
    public AttackResult hit(final Weapon weapon, final int at, final int roll) {
        return weapon.hit(at, roll);
    }

}