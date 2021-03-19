package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.*;
import it.polimi.db2.gma.GMAEJB.exceptions.BadProductException;
import it.polimi.db2.gma.GMAEJB.exceptions.BadQuestionnaireException;
import it.polimi.db2.gma.GMAEJB.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class EntryService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    public EntryEntity addEmptyEntry(int questionnaireId, int userId) {
        EntryEntity newEntry = new EntryEntity();

        UserEntity newUser = new UserEntity();
        newUser.setId(userId);
        newUser.addEntries(newEntry);

        QuestionnaireEntity newQuestionnaire = new QuestionnaireEntity();
        newQuestionnaire.setId(questionnaireId);
        newQuestionnaire.addEntries(newEntry);

        em.persist(newEntry);
        return newEntry;
    }
}
