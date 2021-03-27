package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
import it.polimi.db2.gma.GMAEJB.enums.ExpertiseLevel;
import it.polimi.db2.gma.GMAEJB.enums.Sex;
import it.polimi.db2.gma.GMAEJB.exceptions.BadEntryException;
import it.polimi.db2.gma.GMAEJB.exceptions.BadWordException;
import it.polimi.db2.gma.GMAEJB.services.EntryService;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import it.polimi.db2.gma.GMAEJB.services.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.PersistenceException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@WebServlet(name = "QuestionnaireSubmitServlet", value = "/submit")
public class QuestionnaireSubmitServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/EntryService")
    private EntryService entryService;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/UserService")
    private UserService userService;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/QuestionnaireService")
    private QuestionnaireService questionnaireService;

    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        String[] ans = req.getParameterValues("answer[]");

        if (ans == null) {
            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Missing answer.");
            return;
        }

        List<String> answers = Arrays.asList(ans);
        answers.forEach(StringEscapeUtils::escapeJava);

        String ageStr = req.getParameter("age");
        String sexStr = req.getParameter("sex");
        String expLevelStr = req.getParameter("expLevel");

        // Retrieve optional answers and cast to right type.
        Integer age = null;
        Sex sex = null;
        ExpertiseLevel expLevel = null;

        try {
            if (ageStr != null && !ageStr.isEmpty()) {
                age = Integer.parseInt(StringEscapeUtils.escapeJava(ageStr));
            }

            if (sexStr != null && !sexStr.isEmpty()) {
                sex = Sex.valueOf(StringEscapeUtils.escapeJava(sexStr));
            }

            if (expLevelStr != null && !expLevelStr.isEmpty()) {
                expLevel = ExpertiseLevel.valueOf(StringEscapeUtils.escapeJava(expLevelStr));
            }

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Invalid age parameter specified.");
            return;
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Invalid sex or expertise parameter specified.");
            return;
        }

        // Retrieve questionnaire of the day
        QuestionnaireEntity questionnaire = questionnaireService.findQuestionnaireByDate(LocalDate.now());

        try {
            entryService.addNewEntry(user.getId(), questionnaire.getId(), answers, age, sex, expLevel);
        }
        catch (BadEntryException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not submit your answers.");
            return;
        } catch (BadWordException e) {
            userService.blockUser(user.getId());

            resp.sendRedirect(getServletContext().getContextPath() + "/banned");
            return;
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/greetings");
    }

}