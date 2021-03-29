package it.polimi.db2.gma.GMAEJB.services;

import it.polimi.db2.gma.GMAEJB.entities.*;
import it.polimi.db2.gma.GMAEJB.enums.ExpertiseLevel;
import it.polimi.db2.gma.GMAEJB.enums.Sex;
import it.polimi.db2.gma.GMAEJB.exceptions.BadEntryException;
import it.polimi.db2.gma.GMAEJB.exceptions.BadWordException;
import it.polimi.db2.gma.GMAEJB.utils.Entry;
import it.polimi.db2.gma.GMAEJB.utils.QuestionAnswer;
import it.polimi.db2.gma.GMAEJB.utils.StatsAnswers;
import it.polimi.db2.gma.GMAEJB.utils.UserInfo;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class EntryService {
    private static final String TRIGGER_SIGNAL = "45000";

    @PersistenceContext(unitName = "GMAEJB")
    private EntityManager em;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/UserService")
    private UserService userService;

    public Entry getUserQuestionnaireAnswers(int questionnaireID, int userID) throws BadEntryException {
        String username;
        List<QuestionAnswer> questionAnswerList;
        StatsAnswers statsAnswers;

        try {
            username = em.find(UserEntity.class, userID).getUsername();

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

        return new Entry(username, statsAnswers, questionAnswerList);
    }

    public Entry getDefaultQuestionnaireAnswers(int questionnaireID) throws BadEntryException {
        String username;
        List<QuestionAnswer> questionAnswerList;
        StatsAnswers statsAnswers;

        try {
            List<UserInfo> questionnaireUsers = userService.getQuestionnaireUserInfo(questionnaireID, 1);
            UserInfo userInfo = (questionnaireUsers.isEmpty()) ? null : questionnaireUsers.get(0);

            if (userInfo == null) {
                return null;
            }

            username = userInfo.getUsername();

            questionAnswerList = em.createNamedQuery("EntryEntity.getQuestionsAnswers", QuestionAnswer.class)
                    .setParameter("qid", questionnaireID)
                    .setParameter("uid", userInfo.getId())
                    .getResultList();

            if (questionAnswerList.isEmpty()) {
                throw new Exception();
            }

            statsAnswers = em.createNamedQuery("EntryEntity.getStatsAnswers", StatsAnswers.class)
                    .setParameter("qid", questionnaireID)
                    .setParameter("uid", userInfo.getId())
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

        } catch (Exception e) {
            throw new BadEntryException("Could not fetch the entry");
        }

        return new Entry(username, statsAnswers, questionAnswerList);
    }

    public void addEmptyEntry(int userId, int questionnaireId) throws BadEntryException {
        UserEntity user = em.find(UserEntity.class, userId);
        QuestionnaireEntity questionnaire = em.find(QuestionnaireEntity.class, questionnaireId);

        if (user == null || questionnaire == null) {
            throw new BadEntryException("User or questionnaire not found.");
        }

        // Build new EntryEntity object and set the questionnaire to it.
        EntryEntity entry = new EntryEntity();
        entry.setIsSubmitted((byte) 0);

        questionnaire.addEntry(entry);
        user.addEntry(entry);

        em.persist(entry);
    }

    public void addNewEntry(int userId, int questionnaireId, List<String> strAnswers, Integer age, Sex sex, ExpertiseLevel expLevel) throws BadEntryException, BadWordException {
        UserEntity user = em.find(UserEntity.class, userId);
        QuestionnaireEntity questionnaire = em.find(QuestionnaireEntity.class, questionnaireId);

        if (user == null || questionnaire == null) {
            throw new BadEntryException("User or questionnaire not found.");
        }

        // Check if user has already submitted the questionnaire.
        EntryEntity result = em.createNamedQuery("EntryEntity.findByUserAndQuestionnaire", EntryEntity.class)
                .setParameter("userId", user.getId())
                .setParameter("questionnaireId", questionnaire.getId())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (result != null) {
            throw new BadEntryException("User has already submitted this questionnaire.");
        }

        List<QuestionEntity> questions = questionnaire.getQuestions();

        // Check number of answers
        if (strAnswers.size() != questions.size()) {
            throw new BadEntryException("Answers do not match questions.");
        }

        // Build new EntryEntity object and set the questionnaire and the user to it.
        EntryEntity entry = new EntryEntity();
        entry.setIsSubmitted((byte) 1);

        questionnaire.addEntry(entry);
        user.addEntry(entry);



        // Build new AnswerEntity objects and add them to the entry.
        for (int i = 0; i < strAnswers.size(); i++) {
            AnswerEntity answer = new AnswerEntity(strAnswers.get(i));
            questions.get(i).addAnswer(answer);
            entry.addAnswer(answer);
        }

        // Build new StatsEntity object and set it to the entry.
        if (age != null || sex != null || expLevel != null) {
            StatsEntity stats = new StatsEntity();
            stats.setAge(age);
            stats.setSex(sex);
            stats.setExpertiseLevel(expLevel);
            entry.setStats(stats);
        }

        em.persist(entry);

        // Graceful exception handling on bad word trigger.
        try {
            em.flush();
        } catch (PersistenceException e) {
            Throwable t = ExceptionUtils.getRootCause(e);

            if (t instanceof SQLException) {
                SQLException exception = (SQLException) t;

                if (exception.getSQLState().equals(TRIGGER_SIGNAL)) {
                    throw new BadWordException("Offensive word detected.");
                }
            }

        }
    }

    public EntryEntity getEntryByIds(int questionnaireId, int userId) throws BadEntryException {
        QuestionnaireEntity questionnaire = em.find(QuestionnaireEntity.class, questionnaireId);
        UserEntity user = em.find(UserEntity.class, userId);

        if (user == null || questionnaire == null) {
            throw new BadEntryException("User or questionnaire not found.");
        }

        EntryEntity entry = em.createNamedQuery("EntryEntity.findByUserAndQuestionnaire", EntryEntity.class)
                .setParameter("userId", user.getId())
                .setParameter("questionnaireId", questionnaire.getId())
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);

        // Pull fresh data from DB, in order to get the points updated from the trigger.
        if (entry != null) {
            em.refresh(entry);
        }

        return entry;
    }
}
