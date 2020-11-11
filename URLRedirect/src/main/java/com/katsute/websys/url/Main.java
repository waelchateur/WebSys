package com.katsute.websys.url;

import com.kttdevelopment.webdir.api.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Main extends WebDirPlugin {

    public Main(final PluginService service){
        super(service);
    }

    @Override
    public void onEnable(){
        addRenderer("url", new Renderer(){
            final Pattern regex = Pattern.compile("^\\Q[InternetShortcut]\\E\\n\\r?\\QURL=\\E(.+)$");

            @Override
            public byte[] render(final FileRender render){
                final Matcher matcher = regex.matcher(render.getContentAsString());
                if(matcher.find())
                    return
                        asBytes(
                            "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "    <head>\n" +
                            "        <script>\n" +
                            "            function __init(){\n" +
                            "                window.location.replace(\"" + matcher.group(1) + "\")\n" +
                            "            }\n" +
                            "        </script>\n" +
                            "    </head>\n" +
                            "    <body onload=\"__init()\"></body>\n" +
                            "</html>"
                        );
                else
                    return super.render(render);
            }
        });
    }

}
