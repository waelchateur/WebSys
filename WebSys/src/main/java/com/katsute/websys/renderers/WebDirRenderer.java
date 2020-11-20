package com.katsute.websys.renderers;

import com.katsute.websys.Main;
import com.kttdevelopment.simplehttpserver.SimpleHttpExchange;
import com.kttdevelopment.simplehttpserver.SimpleHttpServer;
import com.kttdevelopment.webdir.api.FileRender;
import com.kttdevelopment.webdir.api.Renderer;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public final class WebDirRenderer extends Renderer {

    private final Main main;

    public WebDirRenderer(final Main main){
        this.main = main;
    }

    @Override
    public final byte[] render(final FileRender render){
        final Map<String,? super Object> map = render.getFrontMatter();

        // config
        map.put("config", main.getConfigYml());

        /* exchange */ {
            final SimpleHttpExchange exchange = render.getHttpExchange();
            final Map<String,? super Object> exmap = new HashMap<>();

            exmap.put("cookies", exchange.getCookies());

            /* uri */ {
                final URI URI = exchange.getURI();
                final Map<String, ? super Object> uri = new HashMap<>();
                uri.put("authority", URI.getAuthority());
                uri.put("fragment", URI.getFragment());
                uri.put("host", URI.getHost());
                uri.put("path", URI.getPath());
                uri.put("port", URI.getPort());
                uri.put("query", URI.getQuery());
                uri.put("scheme", URI.getScheme());
                uri.put("uri", URI.toString());
                uri.put("ascii", URI.toASCIIString());
                exmap.put("uri", uri);
            }

            exmap.put("get", exchange.getGetMap());
            exmap.put("post", exchange.getPostMap());

            map.put("exchange", exmap);
        }

        return super.render(render);
    }

}
