package dk.hejselbak.rolemaster.critial;

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
public class CriticalService {

    @Inject EntityManager em; 

    public CriticalService() {
        log.info("Starting Critical Service ...");
    }

    public List<CriticalTable> listTables() {
        return em.createQuery("SELECT ct from CriticalTable ct", CriticalTable.class).getResultList();
    }

    public CriticalTable getCritTable(String shortName) {
        final TypedQuery<CriticalTable> query = em.createQuery(
            "SELECT ct from CriticalTable ct WHERE ct.short_name = :shortName", CriticalTable.class);
        
        return query.setParameter("shortName", shortName.toUpperCase()).getSingleResult();
    }

    public CriticalEntry getCritcal(String critShortName, CritSeverity sev, int roll) {
        CriticalTable ct = getCritTable(critShortName);
        Integer prev_roll = 0;

        if (ct != null) {
            for (CriticalEntry ce : ct.getEntries()) {
                if (ce.getSeverity().equals(sev) && ce.getMax_roll() >= roll && prev_roll < roll ) {
                    return ce;
                } else if (ce.getSeverity().equals(sev)) {
                    prev_roll = ce.getMax_roll();
                }
            }
        }
        return null; 
    }
}