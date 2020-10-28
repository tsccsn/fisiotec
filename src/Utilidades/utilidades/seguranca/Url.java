/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.seguranca;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thiago
 */
@ManagedBean(name = "ender")
@ViewScoped
public class Url implements Serializable{

    public static final String local = "http://f3soft:8084/fisiotec/";
    public static final String localResources = "http://f3soft:8084/fisiotec/resources/";
    public static final String localpc = "X:/Thiago/Documentos/Faculdade/Uninorte/TCC/NetBeans/fisiotec/web/";
    public static final String localpcResources = "X:/Thiago/Documentos/Faculdade/Uninorte/TCC/NetBeans/fisiotec/web/resources/";
    
//    public static final String local = "http://f3soft/fisiotec/";
//    public static final String localResources = "http://f3soft/fisiotec/resources/";
//    public static final String localpc = "C:/Program Files/Apache Software Foundation/Tomcat 7.0.23/webapps/fisiotec/web/";
//    public static final String localpcResources = "C:/Program Files/Apache Software Foundation/Tomcat 7.0.23/webapps/fisiotec/web/resources/";

    public String getLocal() {
        return local;
    }

    public String getLocalResources() {
        return localResources;
    }

    public String getLocalpc() {
        return localpc;
    }

    public String getLocalpcResources() {
        return localpcResources;
    }
    
    
}
