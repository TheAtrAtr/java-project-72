package hexlet.code.controller;


import io.javalin.http.Handler;

import java.io.PrintWriter;

public class HelloWorld {
    public static Handler helloWorld = ctx -> {
        PrintWriter writer = ctx.res().getWriter();
        writer.write("Hello World");
    };
}
