package com.katsute.websys.renderers;

import com.kttdevelopment.webdir.api.FileRender;
import com.kttdevelopment.webdir.api.Renderer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URLRenderer extends Renderer {

    final Pattern regex = Pattern.compile("^\\Q[InternetShortcut]\\E\\r?\\n\\QURL=\\E(.+)$");

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public final byte[] render(final FileRender render){
        final Matcher matcher = regex.matcher(render.getContentAsString());
        if(matcher.find())
            return
                asBytes(
                    String.format("<!DOCTYPE html><html><body onload=\"window.location.replace('%s')\"></body></html>", matcher.group(1))
                );
        else
            return super.render(render);
    }

}