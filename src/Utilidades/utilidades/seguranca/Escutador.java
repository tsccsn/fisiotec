/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.seguranca;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.http.*;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

/**
 * Web application lifecycle listener.
 *
 * @author Thiago
 */
public class Escutador implements Filter, EventListener, ServletContextListener {

    @Override
    public void handleEvent(Event evt) {
        System.out.println("handleEvent is call");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized is call");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed is call");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init is call");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        System.out.println("doFilter is call");
    }

    @Override
    public void destroy() {
        System.out.println("destroy is call");
    }
}
