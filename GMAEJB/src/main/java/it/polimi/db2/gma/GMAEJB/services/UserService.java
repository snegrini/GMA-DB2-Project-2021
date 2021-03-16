package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
import it.polimi.db2.gma.GMAEJB.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.*;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    /**
     * Checks user credentials against those saved in the database.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return the {@code UserEntity} linked to the username and password if the user is found and password matches, {@code null} otherwise.
     * @throws CredentialsException     when the connection with the database fails or when there are more than one user registered with same credentials.
     * @throws NonUniqueResultException when there are more than one user registered with same credentials.
     */
    public UserEntity checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<UserEntity> uList;

        try {
            uList = em.createNamedQuery("UserEntity.checkCredentials", UserEntity.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }

        if (uList.isEmpty()) {
            return null;
        } else if (uList.size() == 1) {
            return uList.get(0);
        }

        throw new NonUniqueResultException("More than one user registered with same credentials.");
    }

}