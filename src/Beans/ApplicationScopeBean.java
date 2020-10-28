
import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Thiago
 */
@ManagedBean
@ApplicationScoped
public class ApplicationScopeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public void preRenderView() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //tune session params, eg. session.setMaxInactiveInterval(..);
        //perform other pre-render stuff, like setting user context...
    }
    
}
