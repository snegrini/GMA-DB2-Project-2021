package it.polimi.db2.gma.GMAWEB.controllers.admin;

import it.polimi.db2.gma.GMAEJB.exceptions.BadEntryException;
import it.polimi.db2.gma.GMAEJB.exceptions.BadQuestionnaireException;
import it.polimi.db2.gma.GMAEJB.services.EntryService;
import it.polimi.db2.gma.GMAEJB.services.UserService;
import it.polimi.db2.gma.GMAEJB.utils.Entry;
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

@WebServlet(name = "AdminQuestionnaireServlet", value = "/admin/questionnaire")
public class QuestionnaireServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/UserService")
    private UserService userService;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestQuestionnaireId = req.getParameter("id");
        String requestUserId = req.getParameter("user");

        int questionnaireId;
        List<UserInfo> submittedUsers;
        List<UserInfo> notSubmittedUsers;
        try {
            questionnaireId = Integer.parseInt(requestQuestionnaireId);

            submittedUsers = userService.getQuestionnaireUserInfo(questionnaireId, 1);
            notSubmittedUsers = userService.getQuestionnaireUserInfo(questionnaireId, 0);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Questionnaire ID");
            return;
        } catch (BadQuestionnaireException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        int userId;
        Entry entry;

        if (requestUserId != null) {
            try {
                userId = Integer.parseInt(requestUserId);
                entry = entryService.getUserQuestionnaireAnswers(questionnaireId, userId);
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the answers.");
                return;
            }
        } else {
            try {
                entry = entryService.getDefaultQuestionnaireAnswers(questionnaireId);
            } catch (BadEntryException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the answers.");
                return;
            }
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("submittedUsers", submittedUsers);
        ctx.setVariable("notSubmittedUsers", notSubmittedUsers);
        ctx.setVariable("questionnaireId", questionnaireId);

        if (entry != null) {
            ctx.setVariable("entry", entry);
        }

        String path = "/WEB-INF/admin/questionnaire.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }
}
