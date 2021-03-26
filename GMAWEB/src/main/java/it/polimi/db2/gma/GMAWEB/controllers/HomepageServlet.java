package it.polimi.db2.gma.GMAWEB.controllers;

import it.polimi.db2.gma.GMAEJB.entities.*;
import it.polimi.db2.gma.GMAEJB.exceptions.BadEntryException;
import it.polimi.db2.gma.GMAEJB.services.EntryService;
import it.polimi.db2.gma.GMAEJB.services.ProductService;
import it.polimi.db2.gma.GMAEJB.services.QuestionnaireService;
import it.polimi.db2.gma.GMAEJB.utils.ProductOfDay;
import it.polimi.db2.gma.GMAEJB.utils.Review;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomepageServlet", value = "/homepage")
public class HomepageServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/ProductService")
    private ProductService productService;

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
        ProductEntity product;
        try {
            product = productService.findProductByDay(new Date(new java.util.Date().getTime()));
        } catch (PersistenceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Could not retrieve the questionnaire.");
            return;
        }

        ProductOfDay productOfDay = null;
        if (product != null) { // Building a custom object to send to the view instead of all the entity
            List<Review> reviewList = new ArrayList<>();

            for (ReviewEntity review : product.getReviews()) {
                reviewList.add(new Review(review.getUser().getUsername(), review.getReview()));
            }

            productOfDay = new ProductOfDay(product.getName(), product.getImage(), reviewList);
        }

        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        QuestionnaireEntity questionnaire = questionnaireService.findQuestionnaireByDate(LocalDate.now());

        EntryEntity entry = null;
        try {
            entry = entryService.getEntryByIds(questionnaire.getId(), user.getId());
        } catch (BadEntryException e) {
            e.printStackTrace();
        }

        resp.setContentType("text/html");

        ServletContext servletContext = getServletContext();
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        ctx.setVariable("productOfDay", productOfDay);
        ctx.setVariable("entry", entry);
        String path = "/WEB-INF/homepage.html";

        templateEngine.process(path, ctx, resp.getWriter());
    }
}
