package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.EntryEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
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
        newQuestionnaire.addEntry(newEntry);

        em.persist(newEntry);
        return newEntry;
    }

    public EntryEntity addNewEntry(int userId, String questionnaireId, List<String> answers, String age, String sex, String eLevel) {
        EntryEntity newEntry = new EntryEntity();

        UserEntity newUser = new UserEntity();
        newUser.setId(userId);
        QuestionnaireEntity newQuestionnaire = new QuestionnaireEntity();
        newQuestionnaire.setId(Integer.parseInt(questionnaireId));

        //retrieve the questions
        List<QuestionEntity> questions = newQuestionnaire.getQuestions();
        //add at every question the answer
        int i=0;
        for (QuestionEntity q : questions) {
            q.addAnswer(answers.get(i));
            ++i;
        }

        //add the entry
        int j=0;
        for (QuestionEntity q: questions) {
            newEntry.addAnswerEntity(answers.get(j));
            ++j;
        }
        newEntry.addStatsEntity(age,sex,eLevel);
        newEntry.setQuestionnaireEntity(newQuestionnaire);
        newEntry.setUserEntity(newUser);

        em.persist(newEntry);
        return newEntry;
    }
}
