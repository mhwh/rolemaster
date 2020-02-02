package dk.hejselbak.weapon;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@ApplicationScoped
@Slf4j
public class FumbleService {

    @Inject EntityManager em; 

    public FumbleService() {
        log.info("Starting Fumble Service ...");
    }

    public List<FumbleTable> listTables() {
        return em.createQuery("SELECT ft from FumbleTable", FumbleTable.class).getResultList();
    }

    public FumbleTable getFumbleTable(Long id) {
        final TypedQuery<FumbleTable> query = em.createQuery(
            "SELECT ft from FumbleTable ft WHERE ft.id = :id", FumbleTable.class);
        
        return query.setParameter("id", id).getSingleResult();
    }

    public FumbleEntry getFumble(Long fumbleTable_id, String group, int roll) {
        Integer prev_roll = -1;

        FumbleTable ft = getFumbleTable(fumbleTable_id);
        FumbleGroup fg = FumbleGroup.valueOf(group);
        FumbleEntry backup = null;

        if (ft != null && fg != null) {
            for (FumbleEntry fe : ft.getEntries()) {
                if (fe.getGroup().equals(fg) && fe.getMax_roll() >= roll && prev_roll < roll ) {
                    return fe;
                } else if (fe.getGroup().equals(fg)) {
                    prev_roll = fe.getMax_roll();
                    backup = fe;
                }
            }
            // If roll was bigger than the largest value in the fumble table, then we return the biggest fumble we got!
            return backup;
        }
        return null; 
    }
}