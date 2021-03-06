package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "BannedServlet", value = "/banned")
public class BannedServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    public void init() {
        ServletContext servletContext = getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
        templateResolver.setSuffix(".html");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            UserEntity user = (UserEntity) session.getAttribute("user");
            if (user.getIsBlocked().intValue() == 0) {
                resp.sendRedirect(req.getContextPath() + "/homepage");
            }
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        String path = "/WEB-INF/banned.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }
}
