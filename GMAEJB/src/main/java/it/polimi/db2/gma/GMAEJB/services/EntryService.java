package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.EntryEntity;
import it.polimi.db2.gma.GMAEJB.exceptions.BadEntryException;
import it.polimi.db2.gma.GMAEJB.utils.Entry;
import it.polimi.db2.gma.GMAEJB.utils.QuestionAnswer;
import it.polimi.db2.gma.GMAEJB.utils.StatsAnswers;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class EntryService {
    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    public Entry getUserQuestionnaireAnswers(int questionnaireID, int userID) throws BadEntryException {
        List<QuestionAnswer> questionAnswerList;
        StatsAnswers statsAnswers;

        try {
            questionAnswerList = em.createNamedQuery("EntryEntity.getQuestionsAnswers", QuestionAnswer.class)
                    .setParameter("qid", questionnaireID)
                    .setParameter("uid", userID)
                    .getResultList();

            if (questionAnswerList.isEmpty()) {
                throw new Exception();
            }

            statsAnswers = em.createNamedQuery("EntryEntity.getStatsAnswers", StatsAnswers.class)
                    .setParameter("qid", questionnaireID)
                    .setParameter("uid", userID)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            throw new BadEntryException("Could not fetch the entry");
        }

        return new Entry(statsAnswers, questionAnswerList);
    }

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
