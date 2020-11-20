package com.katsute.websys;

import com.katsute.websys.renderers.*;
import com.katsute.websys.renderers.liquid.LayoutRenderer;
import com.katsute.websys.renderers.liquid.LiquidRenderer;
import com.kttdevelopment.webdir.api.PluginService;
import com.kttdevelopment.webdir.api.WebDirPlugin;

import java.io.File;

public final class Main extends WebDirPlugin {

    public Main(final PluginService service){
        super(service);
    }

    @Override
    public synchronized final void onEnable(){
        addRenderer("webdir", new WebDirRenderer(this));
        addRenderer("file", new FileRenderer());

        addRenderer("url", new URLRenderer());

        addRenderer("layout", new LayoutRenderer(new File("_layouts")));
        addRenderer("liquid", new LiquidRenderer(new File("_includes")));

        addRenderer("sass", new SASSRenderer());
    }

}
