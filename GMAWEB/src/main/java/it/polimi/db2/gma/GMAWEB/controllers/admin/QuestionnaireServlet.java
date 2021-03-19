package it.polimi.db2.gma.GMAWEB.controllers.admin;

import it.polimi.db2.gma.GMAEJB.entities.QuestionnaireEntity;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import it.polimi.db2.gma.GMAEJB.utils.UserInfo;
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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "AdminQuestionnaireServlet", value = "/admin/questionnaire")
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
        String requestId = req.getParameter("id");

        // TODO Fetch user param and check validity

        int questionnaireId;
        QuestionnaireEntity questionnaire;
        try {
            questionnaireId = Integer.parseInt(requestId);
            questionnaire = questionnaireService.findQuestionnaireById(questionnaireId);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the questionnaire.");
            return;
        }

        List<UserInfo> submittedUsers = questionnaire.getEntries().stream()
                .filter(e -> e.getIsSubmitted() == 1)
                .map(e -> new UserInfo(e.getUser().getId(), e.getUser().getUsername()))
                .collect(Collectors.toList());

        List<UserInfo> notSubmittedUsers = questionnaire.getEntries().stream()
                .filter(e -> e.getIsSubmitted() == 0)
                .map(e -> new UserInfo(e.getUser().getId(), e.getUser().getUsername()))
                .collect(Collectors.toList());

        // TODO Fetch user answers and pass

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("submittedUsers", submittedUsers);
        ctx.setVariable("notSubmittedUsers", notSubmittedUsers);
        ctx.setVariable("questionnaireId", questionnaireId);
        String path = "/WEB-INF/admin/questionnaire.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }
}
