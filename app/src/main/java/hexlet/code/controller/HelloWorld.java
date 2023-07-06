package hexlet.code.controller;


import io.javalin.http.Handler;

public class HelloWorld {
    public static Handler helloWorld = ctx -> ctx.render("index.html");
}
