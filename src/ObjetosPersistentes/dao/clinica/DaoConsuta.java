/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.clinica;

import cp.clinica.CPConsulta;
import cp.clinica.CPPaciente;
import cp.clinica.CPTratamento;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import utilidades.hibernate.HibernateUtil;

/**
 *
 * @author Felipe Machado
 */
public class DaoConsuta {

    public static DaoGenerico<CPConsulta> daoU = new DaoGenerico<CPConsulta>(CPConsulta.class);

    public static void merge(CPConsulta p) {
        daoU.merge(p);
    }
    public static CPConsulta mergeII(CPConsulta p) {
        return daoU.mergeII(p);
    }

    public static void deleta(CPConsulta p) {
        daoU.delete(p);
    }

    public static List<CPConsulta> getAll(String ordem) {
        return daoU.getAll(ordem);
    }

    public static List<CPConsulta> getAll() {
        return daoU.getAll();
    }

    public static CPConsulta getById(long id) {
        return daoU.getById(id);
    }

    public static List<CPConsulta> getByDay(Date dia) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPConsulta u where u.dia like :dia and u.status = :status or u.status = :trat order by u.horaInicial");
        q.setString("status", "Aberto");
        q.setString("trat", "Tratamento");
        q.setDate("dia", dia);
        List<CPConsulta> lista = q.list();
        return lista;
    }

    public static List<CPConsulta> getAtuais() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPConsulta u where u.status = :status or u.status = :trat order by u.dia, u.horaInicial");
        q.setString("status", "Aberto");
        q.setString("trat", "Tratamento");
        List<CPConsulta> lista = q.list();
        return lista;
    }

    public static List<CPConsulta> getAntigas() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPConsulta u where u.status != :status or u.status != :trat");
        q.setParameter("status", "Aberto");
        q.setParameter("trat", "Tratamento");
        List<CPConsulta> lista = q.list();
        return lista;
    }

    public static List<CPConsulta> getPorIntervalo(Date de, Date ate) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPConsulta u where u.dia between :de and :ate");
        q.setDate("de", de);
        q.setDate("ate", ate);
        List<CPConsulta> lista = q.list();
        return lista;
    }

    public static boolean jaExiste(int box, Date dia, Date horaInicial, Date horaFinal) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from CPConsulta a where a.box = :box and a.dia = :dia and :horaInicial between a.horaInicial and a.horaFinal and :horaFinal between a.horaInicial and a.horaFinal");
        Query q2 = s.createQuery("from CPTratamento a where a.box = :box and a.dia = :dia and :horaInicial between a.horaInicial and a.horaFinal and :horaFinal between a.horaInicial and a.horaFinal");
        q.setInteger("box", box);
        q2.setInteger("box", box);
        q.setDate("dia", dia);
        q2.setDate("dia", dia);
        q.setTime("horaInicial", horaInicial);
        q2.setTime("horaInicial", horaInicial);
        q.setTime("horaFinal", horaFinal);
        q2.setTime("horaFinal", horaFinal);
        List<CPConsulta> lista = q.list();
        List<CPTratamento> lista2 = q2.list();
        if (lista.isEmpty() && lista2.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static List<CPConsulta> pesquisa(CPPaciente paciente, Date dia, Date horaInicial, Date horaFinal, String obs, String status, int box, Date de, Date ate) {
        String hql = null;
        Session s = HibernateUtil.getSession();

        if (paciente != null) {
            if (hql == null) {
                hql = "from CPConsulta c where c.paciente like :paciente";
            } else {
                hql += " and c.paciente like :paciente";
            }
        }
        if (dia != null) {
            if (hql == null) {
                hql = "from CPConsulta c where c.dia like :dia";
            } else {
                hql += " and c.dia like :dia";
            }
        }
        if (horaInicial != null) {
            if (hql == null) {
                hql = "from CPConsulta c where c.horaInicial like :horaInicial";
            } else {
                hql += " and c.horaInicial like :horaInicial";
            }
        }
        if (horaFinal != null) {
            if (hql == null) {
                hql = "from CPConsulta c where c.horaFinal like :horaFinal";
            } else {
                hql += " and c.horaFinal like :horaFinal";
            }
        }
        if (!obs.isEmpty()) {
            if (hql == null) {
                hql = "from CPConsulta c where c.obs like :obs";
            } else {
                hql += " and c.obs like :obs";
            }
        }
        if (!status.equals("")) {
            if (hql == null) {
                hql = "from CPConsulta c where c.status like :status";
            } else {
                hql += " and c.status like :status";
            }
        }
        if (box != 0) {
            if (hql == null) {
                hql = "from CPConsulta c where c.box like :box";
            } else {
                hql += " and c.box like :box";
            }
        }
        if (de != null && ate != null) {
            if (hql == null) {
                hql = "from CPConsulta c where c.dia between :de and :ate";
            } else {
                hql += " and c.dia between :de and :ate";
            }
        }
        if (de != null && ate == null) {
            if (hql == null) {
                hql = "from CPConsulta c where c.dia between :de and :hoje";
            } else {
                hql += " and c.dia between :de and :hoje";
            }
        }
        if (de == null && ate != null) {
            if (hql == null) {
                hql = "from CPConsulta c where c.dia like :ate";
            } else {
                hql += " and c.dia like " + ate;
            }
        }
        System.out.println(hql);
        Query q = s.createQuery(hql);
        if (paciente != null) {
            q.setParameter("paciente", paciente);
        }
        if (dia != null) {
            q.setDate("dia", dia);
        }
        if (horaInicial != null) {
            q.setTime("horaInicial", horaInicial);
        }
        if (horaFinal != null) {
            q.setTime("horaFinal", horaFinal);
        }
        if (!obs.isEmpty()) {
            q.setString("obs", "%" + obs + "%");
        }
        if (!status.equals("")) {
            q.setString("status", status);
        }
        if (box != 0) {
            q.setInteger("box", box);
        }
        if (de != null && ate != null) {
            q.setDate("de", de);
            q.setDate("ate", ate);
        }
        if (de != null && ate == null) {
            q.setDate("de", de);
            q.setDate("hoje", new Date());
        }
        if (de == null && ate != null) {
            q.setDate("ate", ate);
        }

        List<CPConsulta> lista = q.list();
        return lista;
    }
}
