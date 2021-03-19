package it.polimi.db2.gma.GMAWEB.controllers.admin;

import it.polimi.db2.gma.GMAEJB.entities.ProductEntity;
import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.exceptions.BadProductException;
import it.polimi.db2.gma.GMAEJB.exceptions.BadQuestionnaireException;
import it.polimi.db2.gma.GMAEJB.services.ProductService;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import org.apache.commons.text.StringEscapeUtils;
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
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AdminDeletionServlet", value = "/admin/deletion")
public class DeletionServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private final String deletionPath = "/WEB-INF/admin/deletion.html";

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

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());

        List<QuestionnaireEntity> questionnaires = questionnaireService.findQuestionnairesUntil(LocalDate.now());

        ctx.setVariable("questionnaires", questionnaires);

        templateEngine.process(deletionPath, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String strQuestionnaireId = StringEscapeUtils.escapeJava(req.getParameter("questionnaireId"));

        int questionnaireId;
        try {
            questionnaireId = Integer.parseInt(strQuestionnaireId);
        } catch (NumberFormatException e)  {
            resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Invalid questionnaire id.");
            return;
        }

        try {
            questionnaireService.deleteQuestionnaire(questionnaireId);
        } catch (BadQuestionnaireException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/admin/deletion");
    }
}
