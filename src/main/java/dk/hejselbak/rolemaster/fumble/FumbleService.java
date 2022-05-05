package dk.hejselbak.rolemaster.fumble;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import lombok.extern.slf4j.Slf4j;


@ApplicationScoped
@Slf4j
public class FumbleService {

    @Inject EntityManager em; 

    public FumbleService() {
        log.info("Starting Fumble Service ...");
    }

    public List<FumbleTableSuper> listTables() {
        List<FumbleTableSuper> result = em.createQuery("SELECT ft from FumbleTable ft", FumbleTableSuper.class).getResultList();
        log.debug("#" + result.size() + " number of fumble tables returned in the list...");
        return result;
    }

    public FumbleTable getFumbleTable(Long id) {
        final TypedQuery<FumbleTable> query = em.createQuery(
            "SELECT ft from FumbleTable ft WHERE ft.id = :id", FumbleTable.class);
        FumbleTable result;

        try {
            result = query.setParameter("id", id).getSingleResult();
        } catch (PersistenceException e) {
            log.debug("Exception occurred while trying to locate a fumble table with the id of " + id + "." + e.getMessage());
            result = null;
        } catch (IllegalStateException e) {
            log.debug("Exception occurred while trying to locate a fumble table with the id of " + id + "." + e.getMessage());
            result = null;
        }

        return result;
    }

    public FumbleEntry getFumble(Long fumbleTable_id, String group, int roll) {
        Integer prev_roll = -1;

        FumbleTable ft = getFumbleTable(fumbleTable_id);
        FumbleCategory fg = FumbleCategory.valueOf(group);
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