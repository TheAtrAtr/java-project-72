package hexlet.code.controller;

import hexlet.code.domain.Url;
import hexlet.code.domain.UrlCheck;
import hexlet.code.domain.query.QUrl;
import io.ebean.PagedList;
import io.javalin.http.Handler;
import io.javalin.http.NotFoundResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class UrlController {
    public static Handler urls = ctx -> {
        String urlFromInputField = parse(ctx.formParam("url"));
        if (urlFromInputField == null) {
            ctx.sessionAttribute("flash", "Ссылка введена в некорректном формате");
            ctx.redirect("/");
            return;
        }
        Url url = new QUrl().name.equalTo(urlFromInputField).findOne();
        if (url != null) {
            ctx.sessionAttribute("flash", "Эта ссылка уже есть в базе");
            ctx.redirect("/urls");
            return;
        }
        url = new Url(urlFromInputField);
        url.save();
        ctx.sessionAttribute("flash", "Ссылка добавлена в базу");
        ctx.redirect("/urls");


    };

    public static Handler listUrls = ctx -> {
        int number = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1) - 1;
        int rows = 10;
        int off = (number - 1) * rows;

        PagedList<Url> pagedList = new QUrl().setFirstRow(off).setMaxRows(rows).orderBy().id.asc().findPagedList();
        List<Url> list_url = pagedList.getList();
        int countUrls = new QUrl().findCount();
        ctx.attribute("urls", list_url);
        ctx.attribute("page", number);
        ctx.attribute("countUrls", countUrls);
        ctx.render("urls/index.html");

    };
    public static Handler showUrl = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();

        List<UrlCheck> checks = url.getUrlChecks();

        ctx.attribute("url", url);
        ctx.render("urls/show.html");
    };
    public static Handler checkUrl = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();

        if (url == null) {
            throw new NotFoundResponse();
        }
        try {
            String urlName = url.getName();
            HttpResponse<String> response = Unirest.get(urlName).asString();

            String content = response.getBody();
            Document doc = Jsoup.parse(content);

            int statusCode = response.getStatus();
            String title = doc.title();
            String h1 = "";
            String description = "";

            Element h1Element = doc.selectFirst("h1");
            Element descriptionElement = doc.selectFirst("meta[name=description]");

            if (h1Element != null) {
                h1 = h1Element.text();
            }
            if (descriptionElement != null) {
                description = descriptionElement.attr("content");
            }

            UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, url);
            urlCheck.save();

            ctx.sessionAttribute("flash", "Страница успешно проверена");
        } catch (UnirestException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
        }
        ctx.redirect("/urls/" + id);
    };

    public static String parse(String url) {
        try {
            URL u = new URL(url);
            String protocol = u.getProtocol();
            String authority = u.getAuthority();
            return protocol + "://" + authority;
        } catch (MalformedURLException e) {
            return null;
        }

    }
}
