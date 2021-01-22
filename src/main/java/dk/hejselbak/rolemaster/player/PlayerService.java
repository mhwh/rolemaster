package dk.hejselbak.rolemaster.player;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class PlayerService {
    @Inject EntityManager em;

    public List<Player> getPlayers() {
        //return new TreeSet<Weapon>(em.createQuery("select w from Weapon w", Weapon.class).getResultList());
        return em.createQuery("select p from Player p", Player.class).getResultList();
    }
}
