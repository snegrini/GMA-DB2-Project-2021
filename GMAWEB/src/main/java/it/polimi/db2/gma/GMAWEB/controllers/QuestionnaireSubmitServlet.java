package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
import it.polimi.db2.gma.GMAEJB.services.EntryService;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@WebServlet(name = "QuestionnaireSubmitServlet", value = "/submit")
public class QuestionnaireSubmitServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/EntryService")
    private EntryService entryService;

    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String[] ans = req.getParameterValues("answer[]");

        if (ans == null) {
            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Missing answer.");
            return;
        }

        List<String> answers = Arrays.asList(ans);
        answers.forEach(StringEscapeUtils::escapeJava);

        String age = StringEscapeUtils.escapeJava(req.getParameter("age"));
        String sex = StringEscapeUtils.escapeJava(req.getParameter("sex"));
        String expLevel = StringEscapeUtils.escapeJava(req.getParameter("expLevel"));

        UserEntity user = (UserEntity) session.getAttribute("user");
        String questionnaireId = req.getParameter("questionnaireId");

        // TODO check parameters.
        
        // TODO control that the questionnaireId is the valid one of the day.

        try {
            entryService.addNewEntry(user.getId(), questionnaireId, answers, age, sex, expLevel);
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not submit your answers.");
            return;
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/greetings");
    }

}