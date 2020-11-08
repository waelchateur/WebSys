package com.katsute.websys.filevars;

import com.kttdevelopment.webdir.api.PluginService;
import com.kttdevelopment.webdir.api.WebDirPlugin;

public final class Main extends WebDirPlugin {

    public Main(final PluginService service){
        super(service);
    }

    @Override
    public final void onEnable(){
        addRenderer("fileVars", new FileRenderer());
    }

}
