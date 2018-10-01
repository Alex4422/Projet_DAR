package launch;

import java.io.File;

import app.App;
import db.DBService;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {

    public static void main(String[] args) throws Exception {
        String appDir = "src/main/webapp/";
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
}
