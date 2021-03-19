package it.polimi.db2.gma.GMAWEB.controllers;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "QuestionnaireServlet", value = "/questionnaire")
public class QuestionnaireServlet extends HttpServlet {
    private TemplateEngine templateEngine;

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
        try {
            doPost(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("going") == "GoTo") {
            String questPage = "TRUE";
            String path = "/WEB-INF/questionnairepage.html";
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("questPage", questPage);
            templateEngine.process(path, ctx, resp.getWriter());
        } else if (req.getParameter("cancel") == "cancel") {
            String path = "/WEB-INF/homepage.html";
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            templateEngine.process(path, ctx, resp.getWriter());
        } else if (req.getParameter("next") == "next") {
            String questPage = "FALSE";
            String path = "/WEB-INF/questionnairepage.html";
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("questPage", questPage);
            templateEngine.process(path, ctx, resp.getWriter());
        } else if (req.getParameter("submit") == "submit") {
            String path = "/WEB-INF/greetingspage.html";
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            templateEngine.process(path, ctx, resp.getWriter());
        } else if (req.getParameter("previous") == "previous") {
            String questPage = "TRUE";
            String path = "/WEB-INF/questionnairepage.html";
            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("questPage", questPage);
            templateEngine.process(path, ctx, resp.getWriter());
        }
    }

}
