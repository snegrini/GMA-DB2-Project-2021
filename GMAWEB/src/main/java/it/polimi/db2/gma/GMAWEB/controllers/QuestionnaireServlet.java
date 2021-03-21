package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.QuestionEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import it.polimi.db2.gma.GMAEJB.utils.LeaderboardRow;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
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
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "QuestionnaireServlet", value = "/questionnaire")
public class QuestionnaireServlet extends HttpServlet {
    private TemplateEngine templateEngine;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String date = StringEscapeUtils.escapeJava(req.getParameter("date"));
        LocalDate localDate = LocalDate.parse(date);

        if (localDate.compareTo(LocalDate.now()) < 0) {
            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Invalid date. Please select a valid date.");
            return;
        }

        List<QuestionEntity> questions;
        QuestionnaireEntity questionnaire;
        questionnaire = questionnaireService.findQuestionnaireByDate(Date.valueOf(localDate));

        try {
            questions = questionnaireService.getQuestionList(questionnaire.getId());
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the questions.");
            return;
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("questions", questions);
        ctx.setVariable("questionnaireId", questionnaire.getId());
        String path = "/WEB-INF/questionnaire.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path;

        if (req.getParameter("submit") != null) {
            path = "/submit";
        } else if (req.getParameter("cancel") != null) {
            path = "/cancel";
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad submit action.");
            return;
        }

        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }
}
