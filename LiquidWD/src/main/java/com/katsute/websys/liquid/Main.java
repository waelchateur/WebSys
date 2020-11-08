package com.katsute.websys.liquid;

import com.kttdevelopment.webdir.api.PluginService;
import com.kttdevelopment.webdir.api.WebDirPlugin;

import java.io.File;

public final class Main extends WebDirPlugin {

    private LiquidRenderer liquid;

    public Main(final PluginService service){
        super(service);
    }

    @Override
    public final void onEnable(){
        addRenderer("layout", new LayoutRenderer(new File("_layouts")));
        addRenderer("liquid", liquid = new LiquidRenderer(new File("_includes")));
    }

    public final LiquidRenderer getLiquid(){
        return liquid;
    }

}
