package com.katsute.websys.liquidwd;

import com.kttdevelopment.webdir.api.FileRender;
import com.kttdevelopment.webdir.api.Renderer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public final class LayoutRenderer extends Renderer {

    private final File layouts;

    public LayoutRenderer(final File layouts){
        this.layouts = layouts;
    }

    private static final int len = ".html".length();
    private static final String regex = "(?<!\\\\)\\Q{{\\E(\\s?)\\Qcontent\\E\\1\\Q}}\\E";

    @Override
    public final byte[] render(final FileRender render){
        final String layout    = Objects.requireNonNull(render.getFrontMatter().get("layout").toString());
        final String layoutExt = layout.toLowerCase().endsWith(".html") ? layout.substring(0, layout.length()-len) : layout;
        final String target    = layoutExt.isBlank() || layoutExt.equalsIgnoreCase(".html") ? layout : layoutExt;

        try{
            return asBytes(Files.readString(new File(layouts, target).toPath()).replaceAll(regex, render.getContentAsString()));
        }catch(IOException e){
            return super.render(render);
        }
    }

}
