package com.katsute.websys.vars;

import com.kttdevelopment.simplehttpserver.SimpleHttpExchange;
import com.kttdevelopment.webdir.api.FileRender;
import com.kttdevelopment.webdir.api.Renderer;

import java.net.URI;
import java.util.*;

public class WebSysRenderer extends Renderer {

    @Override
    public byte[] render(final FileRender render){
        final Map<String,? super Object> map = new HashMap<>();

        // exchange
        {
            final SimpleHttpExchange exchange = render.getHttpExchange();
            map.put("cookies", exchange.getCookies());

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
            map.put("uri", uri);

            map.put("get", exchange.getGetMap());
            map.put("post", exchange.getPostMap());

            render.getFrontMatter().put("exchange", map);
        }

        return super.render(render);
    }

}
