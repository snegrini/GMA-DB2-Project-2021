package it.polimi.db2.gma.GMAWEB.filters;

import it.polimi.db2.gma.GMAEJB.entities.UserEntity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminChecker")
public class AdminChecker implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);
        UserEntity user = (UserEntity) session.getAttribute("user");

        // FIXME
        if (user == null /*|| !user.getRole().equals(UserRole.ADMIN)*/) {
            response.sendRedirect(request.getContextPath()); // Unauthorized access request, redirect to home page.
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
