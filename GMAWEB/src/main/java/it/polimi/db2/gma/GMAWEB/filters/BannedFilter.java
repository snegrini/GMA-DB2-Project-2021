package it.polimi.db2.gma.GMAWEB.filters;

import it.polimi.db2.gma.GMAEJB.entities.UserEntity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "BannedFilter")
public class BannedFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);

        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user.getIsBlocked().intValue() != 0) {
            response.sendRedirect(request.getContextPath() + "/banned"); // Banned user, redirect to banned page
        } else {
            chain.doFilter(req, resp); // Not banned user, just continue request.
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
