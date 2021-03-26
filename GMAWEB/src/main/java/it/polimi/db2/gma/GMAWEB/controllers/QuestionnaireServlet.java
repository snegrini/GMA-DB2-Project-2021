package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.QuestionEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.enums.ExpertiseLevel;
import it.polimi.db2.gma.GMAEJB.enums.Sex;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "QuestionnaireServlet", value = "/questionnaire")
public class QuestionnaireServlet extends HttpServlet {

    private final String questionnairePath = "/WEB-INF/questionnaire.html";

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
        QuestionnaireEntity questionnaire = questionnaireService.findQuestionnaireByDate(LocalDate.now());

        if (questionnaire == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Could not retrieve the questionnaire.");
            return;
        }

        List<QuestionEntity> questions = questionnaire.getQuestions();

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        ctx.setVariable("sexs", Sex.values());
        ctx.setVariable("expLevels", ExpertiseLevel.values());
        ctx.setVariable("product", questionnaire.getProduct());
        ctx.setVariable("questions", questions);

        templateEngine.process(questionnairePath, ctx, resp.getWriter());
    }

    /*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path;

        if (req.getParameter("cancel") != null) {
            path = "/cancel";
        } else if (req.getParameter("submit") != null) {
            path = "/submit";
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad submit action.");
            return;
        }

        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }*/
}
