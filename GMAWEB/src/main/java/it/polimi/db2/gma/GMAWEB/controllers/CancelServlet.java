package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.LoginlogEntity;
import it.polimi.db2.gma.GMAEJB.services.LoginlogService;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import org.eclipse.persistence.internal.oxm.mappings.Login;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;


@WebServlet(name = "CancelServlet", value = "/cancel")
public class CancelServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/LoginlogService")
    private LoginlogService loginlogService;

    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        LoginlogEntity log = new LoginlogEntity();

        try { //Errore di tipo da fixare si potrebbe passare anche solo l'id
            log = loginlogService.addLoginLog(session.getAttribute("user"));
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not cancel.");
            return;
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/homepage.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }

}