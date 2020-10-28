/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Thiago
 */
@FacesConverter(value = "conversorString")
@Deprecated
public class ConversorString implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if(string.isEmpty()){
            return "";
        }else{
            return string;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if(o == null){
            return "";
        }else{
            return o.toString();
        }
    }
    
}
