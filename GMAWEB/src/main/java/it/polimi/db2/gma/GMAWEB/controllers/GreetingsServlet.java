package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.EntryEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
import it.polimi.db2.gma.GMAEJB.services.EntryService;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "GreetingsServlet", value = "/greetings")
public class GreetingsServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        // TODO retrieve Ids
        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        QuestionnaireEntity questionnaire = questionnaireService.findQuestionnaireByDate(LocalDate.now());

        EntryEntity entry = entryService.getEntryByIds(questionnaire.getId(), user.getId());
        int points = entry.getPoints();

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("points", String.valueOf(points));
        String path = "/WEB-INF/greetings.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}