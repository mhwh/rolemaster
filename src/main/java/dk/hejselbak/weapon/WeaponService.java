package dk.hejselbak.weapon;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class WeaponService {

    @Inject 
    EntityManager em; 

    public WeaponService() {
    }

    public SortedSet<Weapon> getWeapons() {
        return new TreeSet<Weapon>(em.createQuery("select w from Weapon w", Weapon.class).getResultList());
    }

    public Weapon getWeapon(int id) {
        return em.find(Weapon.class, id);
    }

    /**
    Roll the attack, check for fumble/failure, modify by OB/DB and other factors as usual. This is the IAV.
    Cross-index the attacks Table Type (Arms Law = AL) with the targets armour type in the Armour DB mod area, 
    subtract this from the IAV. Then add the Weapon OB mod for the armour type. This is the FAN.
    If the FAN is greater than the To Hit Threshold the attack does damage. 
    Criticals result if it has passed the appropriate threshold.
    To determine how many hits occured divide (FAN-THT) by the Basic Hit Factor (The number in brackets on the attack statistics area)
        DAMAGE = ((OB-DB-ArmourMod+WeaponMod)-THT)/BHF

        This method should return hits (0 - ?), potential crit and severity.
    */
    public AttackResult hit(Weapon weapon, int at, int roll) {
        TypedQuery<ArmorDBModTable> query = em.createQuery("SELECT a FROM ArmorDBModTable a WHERE a.law = :law AND a.at = :at", ArmorDBModTable.class);
        ArmorDBModTable armorTable = query.setParameter("law", weapon.getArmorTableLaw()).setParameter("at", at).getSingleResult();
        int hits = 0;
        CritSeverity sev = null; 
        CritTable table = null;

        if (armorTable != null) {
            if (ArmorThreshold.isHit(roll, at)) {
                hits = (int) ((roll + weapon.getATModifier(at) - armorTable.getMod() - ArmorThreshold.getTHT(roll, at)) / weapon.getATFactor(at));
                if (ArmorThreshold.isCrit(roll, at)) {
                    sev = ArmorThreshold.getCritSeverity(roll, at); 
                    table = weapon.getCritTable();
                    log.debug("IAV:" + roll + ", ArmorMod:" + armorTable.getMod() + ", WeaponMod:" + weapon.getATModifier(at) + ", THT:" + ArmorThreshold.getTHT(roll, at) + ", BHF:" + weapon.getATFactor(at) + " = " + new AttackResult(hits, sev, table).toString());
                }
            } 
        } 
        
        return new AttackResult(hits, sev, table);
    }

}