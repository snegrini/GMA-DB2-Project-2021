package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.exceptions.BadEntryException;
import it.polimi.db2.gma.GMAEJB.utils.Entry;
import it.polimi.db2.gma.GMAEJB.utils.QuestionAnswer;
import it.polimi.db2.gma.GMAEJB.utils.StatsAnswers;
import it.polimi.db2.gma.GMAEJB.utils.UserInfo;

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
}
