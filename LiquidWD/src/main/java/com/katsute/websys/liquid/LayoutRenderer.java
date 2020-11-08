package com.katsute.websys.liquid;

import com.kttdevelopment.webdir.api.FileRender;
import com.kttdevelopment.webdir.api.Renderer;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public final class LayoutRenderer extends Renderer {

    private final File layouts;

    public LayoutRenderer(final File layouts){
        this.layouts = layouts;
    }

    private static final int len = ".html".length();
    private static final String regex = "(?<!\\\\)\\Q{{\\E(\\s?)\\Qcontent\\E\\1\\Q}}\\E";

    @Override
    public final byte[] render(final FileRender render){
        final Object obj = render.getFrontMatter().get("layouts");
        @SuppressWarnings("unchecked")
        final List<String> layouts = obj instanceof List ? (List<String>) obj : List.of(obj.toString());

        String content = Objects.requireNonNullElse(render.getContentAsString(), "");
        Collections.reverse(layouts);
        for(final String layout : layouts)
            content = applyLayout(layout, content);
        return asBytes(content);
    }

    private String applyLayout(final String layout, final String content){
        final String fileName = layout.toLowerCase().endsWith(".html") ? layout.substring(0, layout.length()-len) : layout;
        final String target    = fileName.isBlank() || fileName.equalsIgnoreCase(".html") ? layout : fileName + ".html";
        try{
            return Files.readString(new File(layouts, target).toPath()).replaceAll(regex, content);
        }catch(final IOException e){
            throw new UncheckedIOException(e);
        }
    }

}
