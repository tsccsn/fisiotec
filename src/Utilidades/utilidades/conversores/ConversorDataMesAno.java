/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.conversores;

import java.sql.Timestamp;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago
 */
@FacesConverter(value="conversorDataMesAno")
public class ConversorDataMesAno implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return new Timestamp(UtilData.converteEmDate((value)).getTime());
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return UtilData.mesEanoN(((Timestamp) value).getTime());
    }

}
