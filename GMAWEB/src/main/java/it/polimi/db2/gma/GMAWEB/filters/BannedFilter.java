package it.polimi.db2.gma.GMAWEB.filters;

import it.polimi.db2.gma.GMAEJB.entities.UserEntity;
import it.polimi.db2.gma.GMAEJB.services.UserService;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "BannedFilter")
public class BannedFilter implements Filter {

    @EJB(name = "it.polimi.db2.gma.GMAEJB.services/UserService")
    private UserService userService;

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);

        UserEntity sessionUser = (UserEntity) session.getAttribute("user");

        // Retrieve updated user from service
        UserEntity user = userService.findUserById(sessionUser.getId());

        if (user.getIsBlocked().intValue() != 0) {
            // Update session user with IsBlocked attribute set to 1.
            request.getSession().setAttribute("user", user);

            response.sendRedirect(request.getContextPath() + "/banned"); // Banned user, redirect to banned page
        } else {
            chain.doFilter(req, resp); // Not banned user, just continue request.
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
