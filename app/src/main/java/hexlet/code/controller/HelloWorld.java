package hexlet.code.controller;


import io.javalin.http.Handler;

import java.io.PrintWriter;

public class HelloWorld {
    public static Handler helloWorld = ctx -> ctx.render("index.html");
}
