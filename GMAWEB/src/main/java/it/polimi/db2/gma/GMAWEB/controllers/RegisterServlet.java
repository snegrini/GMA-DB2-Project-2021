package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAWEB.exceptions.InputException;
import org.apache.commons.text.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterServlet extends HttpServlet {
    private final String registerPath = "/WEB-INF/register.html";
    private TemplateEngine templateEngine;

    @Override
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

        templateEngine.process(registerPath, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = StringEscapeUtils.escapeJava(req.getParameter("username"));
        String password = StringEscapeUtils.escapeJava(req.getParameter("password"));
        String confirmPassword = StringEscapeUtils.escapeJava(req.getParameter("confirm_password"));
        String email = StringEscapeUtils.escapeJava(req.getParameter("email"));

        if (username == null || password == null || confirmPassword == null || email == null ||
                username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing credential value.");
            return;
        }

        try {
            checkInputs(username, password, confirmPassword, email);
            // TODO Check if email or username already exists
        } catch (InputException e) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", e.getMessage());

            templateEngine.process(registerPath, ctx, resp.getWriter());
        }

        /*
        UserEntity user;
        try {
            user = userService.checkCredentials(username, password);
        } catch (CredentialsException | NonUniqueResultException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not check credentials.");
            return;
        }

        String path;
        if (user == null) {
            resp.setContentType("text/html");

            ServletContext servletContext = getServletContext();
            final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
            ctx.setVariable("errorMessage", "Incorrect username or password.");

            templateEngine.process(indexPath, ctx, resp.getWriter());
        } else {
            req.getSession().setAttribute("user", user);
            path = getServletContext().getContextPath() + "/homepage";
            resp.sendRedirect(path);
        }*/
    }

    private void checkInputs(String username, String password, String confirmPassword, String email) throws InputException {
        if (username.length() > 45) {
            throw new InputException("Username is too long (max 45 chars)!");
        }

        if (email.length() > 90) {
            throw new InputException("Email is too long (max 90 chars)!");
        }

        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        if (!pattern.matcher(email).matches()) {
            throw new InputException("Invalid email address!");
        }

        if (password.length() > 45 || password.length() < 8) {
            throw new InputException("Username is too long (min 8 max 45 chars)!");
        }

        if (!confirmPassword.equals(password)) {
            throw new InputException("Password entered are not the same!");
        }
    }
}
