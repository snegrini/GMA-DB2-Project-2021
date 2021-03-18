package it.polimi.db2.gma.GMAWEB.controllers.admin;

import it.polimi.db2.gma.GMAEJB.entities.ProductEntity;
import it.polimi.db2.gma.GMAEJB.exceptions.BadProductException;
import it.polimi.db2.gma.GMAEJB.services.AdminService;
import it.polimi.db2.gma.GMAEJB.services.ProductService;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import it.polimi.db2.gma.GMAEJB.services.UserService;
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

@WebServlet(name = "AdminCreationServlet", value = "/admin/creation")
public class CreationServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private final String creationPath = "/WEB-INF/admin/creation.html";

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/ProductService")
    private ProductService productService;

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

        List<ProductEntity> products = productService.findAllProducts();

        ctx.setVariable("products", products);

        templateEngine.process(creationPath, ctx, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String date = StringEscapeUtils.escapeJava(req.getParameter("date"));
        String product = StringEscapeUtils.escapeJava(req.getParameter("product"));

        String[] ques = req.getParameterValues("question[]");

        if (ques == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing question.");
            return;
        }

        List<String> questions = Arrays.asList(ques);
        questions.forEach(StringEscapeUtils::escapeJava);

        if (date == null || product == null || date.isEmpty() || product.isEmpty() || questions.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing questionnaire values.");
            return;
        }

        // Checks that date is today or later.
        LocalDate localDate = LocalDate.parse(date);
        if (localDate.compareTo(LocalDate.now()) < 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date. Please select a valid date.");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(product);
        } catch (NumberFormatException e)  {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product id.  Please select a valid product.");
            return;
        }

        try {
            questionnaireService.addNewQuestionnaire(localDate, productId, questions);
        } catch (BadProductException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to add a new questionnaire.");
            return;
        }
    }
}
