package com.katsute.websys.sass;

import com.kttdevelopment.webdir.api.PluginService;
import com.kttdevelopment.webdir.api.WebDirPlugin;

public final class Main extends WebDirPlugin {

    private SassRenderer sass;

    public Main(final PluginService service){
        super(service);
    }

    @Override
    public final void onEnable(){
        addRenderer("sass", sass = new SassRenderer());
    }

}
