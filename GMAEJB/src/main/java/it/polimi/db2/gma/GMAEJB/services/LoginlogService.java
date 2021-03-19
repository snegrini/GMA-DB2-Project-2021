package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.LoginlogEntity;
import it.polimi.db2.gma.GMAEJB.entities.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;

@Stateless
public class LoginlogService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    public void addLoginLog(UserEntity userEntity) {
        Timestamp sqlTimestamp = new Timestamp(System.currentTimeMillis());

        LoginlogEntity loginlog = new LoginlogEntity(sqlTimestamp, userEntity);
        em.persist(loginlog);
    }
}
