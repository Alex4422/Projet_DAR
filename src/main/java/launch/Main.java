package launch;

import java.io.File;

import app.App;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static SessionFactory factory;

    public static void main(String[] args) throws Exception {
        factory = initDb(App.dbUrl());

        String appDir = "src/main/webapp/build";
        Tomcat tomcat = new Tomcat();

        String webport = System.getenv("PORT");
        if (webport == null || webport.isEmpty()) {
            webport = "8080";
        }

        tomcat.setPort(Integer.valueOf(webport));

        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(appDir).getAbsolutePath());
        System.out.println("configuration app with basedir: " + new File("./" + appDir).getAbsolutePath());

        File additionWeb = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWeb.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }

    public static SessionFactory getFactory() {
        if (factory == null) {
            factory = initDb(App.dbUrl());
        }
        return factory;
    }

    public static SessionFactory initDb(String dbUrl) {
        return new Configuration()
                .addAnnotatedClass(entities.User.class)
                .addAnnotatedClass(entities.UserSession.class)
                .addAnnotatedClass(entities.Episode.class)
                .addAnnotatedClass(entities.Rating.class)
                .addAnnotatedClass(entities.Message.class)
                .setProperty("hibernate.connection.url", dbUrl)
                .setProperty("hibernate.connection.driver_class", App.dbClass())
                .setProperty("hibernate.dialect", App.dbDialect())
                //.setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.enable_lazy_load_no_trans", "true")
                .buildSessionFactory();
    }
}
