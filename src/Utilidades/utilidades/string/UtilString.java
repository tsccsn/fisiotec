/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package utilidades.string;

import cp.CPPontoDeEstagio;
import cp.portal.usuarios.ABSUsuario;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utilidades.numeros.UtilNumeros;

/**
 *
 * @author Thiago-Asus
 */
@ManagedBean(name = "utilString")
@ViewScoped
public class UtilString implements Serializable {

    private static final long serialVersionUID = 1L;

    public String divisor(String total, String porcentagem) {
        if (total.equals("0")) {
            return "0";
        } else {
            int t = Integer.parseInt(total);
            double t2 = t / 100d;
            Double d = new Double(porcentagem);
            d = d * t2;
            return new DecimalFormat("0").format((d));
        }

    }

    public String key(int a, int b) {
        return a + "-" + b;
    }

    public static String nomeFormal(ABSUsuario u) {
        try {
            String s = "";
            s = u.getPrimeiroNome().substring(0, 1);
            s = s.concat(". " + u.getSegundoNome());
            return s;
        } catch (NullPointerException e) {
            return "";
        }


    }

    public static ABSUsuario setaPrimeiroESegundoNomeNosUsuarios(ABSUsuario u) {
        if (u.getNomeCompleto().contains(" ")) {
            String s = u.getNomeCompleto().substring(u.getNomeCompleto().indexOf(' ') + 1);
            String se;
            try {
                se = s.substring(0, s.indexOf(' '));
            } catch (Exception e) {
                se = s;
            }
            u.setPrimeiroNome(u.getNomeCompleto().substring(0, u.getNomeCompleto().indexOf(' ')));
            u.setSegundoNome(se);
        }
        return u;
    }

    public String nomeDoPontoSemEspacoETudoMinusculo(CPPontoDeEstagio p) {
        return p.getNome().replaceAll(" ", "").toLowerCase();
    }

    public String longToString(long a) {
        return a + "";
    }

    public String ocultaSenha(ABSUsuario u) {
        String r = "";
        if (u != null && u.getSenha() != null) {
            for (int i = 0; i < u.getSenha().length(); i++) {
                r = r.concat("*");
            }
        }
        return r;
    }

    public static String abreviaMes(String mes) {
        return mes.replace("Janeiro", "Jan").
                replace("Fevereiro", "Fev").
                replace("Março", "Mar").
                replace("Abril", "Abr").
                replace("Maio", "Mai").
                replace("Junho", "Jun").
                replace("Julho", "Jul").
                replace("Agosto", "Ago").
                replace("Setembro", "Set").
                replace("Outubro", "Out").
                replace("Novembro", "Nov").
                replace("Dezembro", "Dez");
    }

    public static String getNomeClasse(Object o) {
        if (o.toString().contains("@")) {
            return o.toString().substring(o.toString().lastIndexOf(".") + 1, o.toString().lastIndexOf("@"));
        } else {
            return o.toString().substring(o.toString().lastIndexOf(".") + 1);
        }
    }

    public static String trataNomeUsuario(ABSUsuario u) {
        String nome = u.getPrimeiroNome().substring(0, 1);
        nome = nome.concat(". " + u.getSegundoNome());
        return nome;
    }

    public static String trataNomeUsuarioIII(String nome, long size) {
        ABSUsuario t = new ABSUsuario() {
        };
        t.setNomeCompleto(nome);
        return trataNomeUsuarioII(t, size);
    }

    public static String trataNomeUsuarioRecebendoStrinh(String nome, long size) {
        ABSUsuario absUsuario = new ABSUsuario() {
        };
        absUsuario.setNomeCompleto(nome);
        return trataNomeUsuarioIII(nome, size);
    }

    public static String trataNomeUsuarioII(ABSUsuario u, long size) {
        String s = u.getNomeCompleto();
        List<String> nomes = new LinkedList<>();
        if (s.contains(" ")) {
            while (s.contains(" ")) {
                if (s.indexOf(' ') == s.lastIndexOf(' ')) {
                    nomes.add(s.substring(s.indexOf(' ') + 1));
                    nomes.add(s.substring(0, s.indexOf(' ')));
                    s = "";
                } else {
                    nomes.add(s.substring(s.lastIndexOf(' ') + 1));
                    s = s.substring(0, s.lastIndexOf(' '));
                }
            }
        } else {
            return s;
        }
        Collections.reverse(nomes);
        int proxAbreviado = 0;
        if (nomeCompleto(nomes).length() > size) {
            nomes.set(0, nomes.get(0).substring(0, 1) + ".");
            proxAbreviado = 2;
        }
        try {
            while (nomeCompleto(nomes).length() > size) {
                while (nomes.get(proxAbreviado).length() < 3) {
                    proxAbreviado++;
                }
                nomes.set(proxAbreviado, nomes.get(proxAbreviado).substring(0, 1) + ".");
            }
        } catch (Exception e) {
        }

        return nomeCompleto(nomes);

    }

    private static String nomeCompleto(List<String> s) {
        String nomecompleto = "";
        for (String string : s) {
            nomecompleto = nomecompleto.concat(string + " ");
        }
        return nomecompleto;
    }

    public static String geraCodigoSecreto() {
        String resultado = "";
        for (int i = 0; i < 30; i++) {
            resultado = resultado.concat("" + UtilNumeros.randomNumeroEntreXeY(0, 9));
        }
        return resultado;
    }
}
