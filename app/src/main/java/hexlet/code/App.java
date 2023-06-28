package hexlet.code;

import hexlet.code.controller.HelloWorld;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import io.javalin.config.JavalinConfig;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import io.javalin.rendering.template.JavalinThymeleaf;

public class App {
    public static void main(String[] args) {
        getApp().start(getPort());
    }

    public static Javalin getApp() {
        Javalin app = Javalin.create(c -> {
            if (!isProduction()) {
                c.plugins.enableDevLogging();
            }
            JavalinThymeleaf.init(getTemplateEngine());
        });
        addRoutes(app);
        app.before(ctx -> {
            ctx.attribute("ctx", ctx);
        });
        return app;
    }

    public static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "5000");
        return Integer.parseInt(port);
    }
    private static String getMode(){
        return System.getenv().getOrDefault("APP_ENV", "development");
    }
    private static boolean isProduction() {
        return getMode().equals("production");
    }

    private static void addRoutes(Javalin app) {
//        app.get("/", ctx -> ctx.result("Hello World"));

        // Добавляем маршруты в приложение. мя метода соответствует глаголу HTTP
        // Метод get добавляет обработчик, который будет выполняться для GET запроса по указанному пути
        app.get("/", HelloWorld.helloWorld);
//
//        app.get("companies", CompanyController.listCompanies);

        // Добавляем обработчик для POST запроса по пути */companies*
//        app.post("companies", CompanyController.createCompany);

        // При помощи методов routes() и path() маршруты можно группировать по пути

//        app.routes(() -> {
//            path("companies", () -> {
//                // GET /companies
//                get(CompanyController.listCompanies);
//                // POST /companies
//                post(CompanyController.createCompany);
//                // GET /companies/new
//                get("new", CompanyController.newCompany);
//            });
//        });

        // Создание динамического маршрута, в котором часть пути переменная

        // GET /companies/1
        // GET /companies/12
        // GET /companies/101

//        app.get("companies/{id}", CompanyController.showCompany);

        // В обработчике можно будет получить переменную часть пути при помощи метода ctx.pathParam("id")
    }

    private static TemplateEngine getTemplateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setCharacterEncoding("UTF-8");

        templateEngine.addTemplateResolver(templateResolver);
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new Java8TimeDialect());

        return templateEngine;
    }

}
