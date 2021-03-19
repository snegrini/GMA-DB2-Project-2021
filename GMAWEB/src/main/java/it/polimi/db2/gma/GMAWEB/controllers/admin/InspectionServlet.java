package it.polimi.db2.gma.GMAWEB.controllers.admin;

import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import it.polimi.db2.gma.GMAEJB.utils.QuestionnaireInfo;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "InspectionServlet", value = "/admin/inspection")
public class InspectionServlet extends HttpServlet {
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
        List<QuestionnaireInfo> questionnaires;
        try {
            questionnaires = questionnaireService.getQuestionnairesInfos();
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the questionnaires.");
            return;
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("questionnaires", questionnaires);
        String path = "/WEB-INF/admin/inspection.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }
}
