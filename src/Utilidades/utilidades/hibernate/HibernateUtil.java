package utilidades.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;

public class HibernateUtil {

    private static SessionFactory s;
    private static Session sessao;
    private static int abriu = 0;
    private static int fechou = 0;

    private HibernateUtil() {
    }

    public static Session getSessao() {
        if (sessao != null) {
            return sessao;
        } else {
            sessao = new Configuration().configure().buildSessionFactory().openSession();
            return sessao;
        }
    }
    
    public static Session getSession() {
        if (sessao != null) {
            return sessao;
        } else {
            sessao = new Configuration().configure().buildSessionFactory().openSession();
            return sessao;
        }
    }
    
    
    
    
    public static void novaSessao(){
        sessao = null;
        sessao = new Configuration().configure().buildSessionFactory().openSession();
    }
    
    public static void fecha() {
        sessao.flush();
        //sessao.();
        /*
         * sessao.close(); fechou++; System.out.println("fechou pela:
         * "+fechou+"x");
         *
         */
    }

    public static void abre() {
        /*
         * if (s == null) { s = new
         * Configuration().configure().buildSessionFactory(); } abriu++;
         * System.out.println("abriu pela: "+abriu+"x"); sessao =
         * s.openSession(); System.out.println("contador!:
         * "+sessao.getSessionFactory().getStatistics().getConnectCount()+"x");
         *
         */
    }
}
