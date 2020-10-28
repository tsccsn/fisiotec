/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.data;

import cp.clinica.CPConsulta;
import cp.clinica.CPPaciente;
import cp.clinica.CPSessao;
import cp.clinica.CPTratamento;
import dao.clinica.*;
import org.joda.time.DateTime;
import testes.GeraBanco;
import utilidades.data.UtilData;

/**
 *
 * @author Thiago
 */
public class Teste_1 {

    public static void main(String args[]) {
        GeraBanco.faz();

        CPPaciente p = new CPPaciente();
        p.setBairro("bairro do paciente");
        p.setCelfone("(68)9281-2198");
        p.setCep("69.905-100");
        p.setCidade("Rio Branco");
        p.setComplemento("Complemento");
        p.setCpf("00196016207");
        p.setDataNasc(UtilData.converteEmDate("05/02/1991"));
        p.setEmail("thiaguerd@hotmail.com");
        p.setEstado("Acre");
        p.setFone("(68)9281-2198");
        p.setLogradouro("largadouro");
        p.setMae("Minha mãe");
        p.setNaturalidade("Naturalidade");
        p.setNome("Thiago Feitosa");
        p.setNum(10);
        p.setPai("Nome do meu pai");
        p.setSexo("Masculino");
        p.setRg("1050418");
        p.setRaca("Negra");
        p = DaoPaciente.mergeII(p);

        p = new CPPaciente();
        p.setBairro("Aviário");
        p.setCelfone("(68)8403-1523");
        p.setCep("69.905-100");
        p.setCidade("Rio Branco");
        p.setComplemento("Complemento");
        p.setCpf("85876895253");
        p.setDataNasc(UtilData.converteEmDate("05/02/1991"));
        p.setEmail("flpmchd@hotmail.com");
        p.setEstado("Acre");
        p.setFone("(68)3224-9039");
        p.setLogradouro("Travessa Santa Luzia");
        p.setMae("Ruth Machado");
        p.setNaturalidade("Rio Branco");
        p.setNome("Felipe Machado Carneiro");
        p.setNum(125);
        p.setPai("Antonio Carneiro");
        p.setSexo("Masculino");
        p.setRg("410352");
        p.setRaca("Parda");
        p = DaoPaciente.mergeII(p);

        CPConsulta c = new CPConsulta();
        c.setPaciente(p);
        c.setBox(4);
        c.setDia(UtilData.converteEmDate("16/12/2011"));
        c.setHoraFinal(new DateTime(2011, 12, 16, 14, 55).toDate());
        c.setHoraInicial(new DateTime(2011, 12, 16, 14, 00).toDate());
        c.setStatus("Aberto");
         c = DaoConsuta.mergeII(c);

        CPSessao s = new CPSessao();
        s.setDiaCad(UtilData.converteEmDate("19/12/2011"));
        s.setPaciente(p);
        s.setPatologia("Ortopedia");
        s.setQtdTratamento(2);
        s.setStatus("Em Aberto");


        CPTratamento t = new CPTratamento();
        t.setBox(5);
        t.setDia(UtilData.converteEmDate("20/12/2011"));
        t.setHoraFinal(new DateTime(2011, 12, 20, 14, 55).toDate());
        t.setHoraInicial(new DateTime(2011, 12, 20, 14, 00).toDate());
        t.setNumTratamento(1);
        t.setSessao(s);
        t.setStatus("Em Aberto");
        t = DaoTratamento.mergeII(t);

        t = new CPTratamento();
        t.setBox(5);
        t.setDia(UtilData.converteEmDate("21/12/2011"));
        t.setHoraFinal(new DateTime(2011, 12, 21, 14, 55).toDate());
        t.setHoraInicial(new DateTime(2011, 12, 21, 14, 00).toDate());
        t.setNumTratamento(1);
        t.setSessao(s);
        t.setStatus("Em Aberto");
        t = DaoTratamento.mergeII(t);
    }
}
