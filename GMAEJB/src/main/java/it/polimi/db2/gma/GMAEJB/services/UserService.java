package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
import it.polimi.db2.gma.GMAEJB.exceptions.CredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    private final PasswordEncoder encoder;

    public UserService() {
        encoder = new BCryptPasswordEncoder();
    }

    public UserService(EntityManager em, BCryptPasswordEncoder encoder) {
        this.em = em;
        this.encoder = encoder;
    }

    /**
     * Checks user credentials against those saved in the database.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return the {@code UserEntity} linked to the usercode and password if the user is found and password matches, {@code null} otherwise.
     * @throws CredentialsException     when the connection with the database fails.
     * @throws NonUniqueResultException when there are more than one user registered with same credentials.
     */
    public UserEntity checkCredentials(String username, String password) throws CredentialsException, NonUniqueResultException {
        List<UserEntity> uList;

        try {
            uList = em.createNamedQuery("UserEntity.findByUsername", UserEntity.class)
                    .setParameter("username", username)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentials.");
        }

        if (uList.isEmpty()) {
            return null;
        } else if (uList.size() == 1) {
            UserEntity user = uList.get(0);

            if (encoder.matches(password, user.getPassword())) {
                return user;
            }
            return null;
        }

        throw new NonUniqueResultException("More than one user registered with same credentials.");
    }

}