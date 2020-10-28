/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.conversores;

import java.sql.Timestamp;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import utilidades.data.UtilData;
import utilidades.mensagens.UtilMensagens;
import utilidades.string.UtilString;

/**
 *
 * @author Thiago
 */
@FacesConverter(value = "converteDataDiaMesAno")
public class ConversorDataDiaMesAno implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            return new Timestamp(UtilData.converteEmDate(value).getTime());
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, UtilMensagens.dataErrada, ""));
        }


    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try {
            return UtilData.diaNmesNanoNL(((Timestamp) value).getTime());
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, UtilMensagens.dataErrada, ""));
        }

    }
}
