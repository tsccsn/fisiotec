package testes;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class GeraBanco {

    public static void faz() {
        Configuration configuration = new Configuration();
        configuration.configure();
        Configuration cfg = new Configuration();
        SchemaExport se = new SchemaExport(cfg.configure());
        se.create(true, true);
    }
}
