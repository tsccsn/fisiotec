/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.clinica;

import cp.clinica.CPPaciente;
import dao.clinica.DaoPaciente;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Felipe Machado
 */
@FacesConverter(value = "pacienteConverter")
public class PacienteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        CPPaciente paciente = DaoPaciente.getByName(string);
        return paciente;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        CPPaciente paciente = new CPPaciente();
        paciente = (CPPaciente) o;
        return paciente.getNome();
    }
}
