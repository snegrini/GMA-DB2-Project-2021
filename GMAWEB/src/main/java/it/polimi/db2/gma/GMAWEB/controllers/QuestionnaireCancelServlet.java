package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
import it.polimi.db2.gma.GMAEJB.exceptions.BadEntryException;
import it.polimi.db2.gma.GMAEJB.services.EntryService;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
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
import java.time.LocalDate;


@WebServlet(name = "QuestionnaireCancelServlet", value = "/cancel")
public class QuestionnaireCancelServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/EntryService")
    private EntryService entryService;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        // Retrieve questionnaire of the day
        QuestionnaireEntity questionnaire = questionnaireService.findQuestionnaireByDate(LocalDate.now());

        if (questionnaire == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Could not retrieve the questionnaire.");
            return;
        }

        try {
            entryService.addEmptyEntry(user.getId(), questionnaire.getId());
        } catch(BadEntryException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Could not cancel.");
            return;
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/homepage");
    }
}