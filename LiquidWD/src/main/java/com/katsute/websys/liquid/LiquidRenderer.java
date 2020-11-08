package com.katsute.websys.liquid;

import com.kttdevelopment.webdir.api.FileRender;
import com.kttdevelopment.webdir.api.Renderer;
import liqp.*;
import liqp.filters.Filter;
import liqp.parser.Flavor;
import liqp.tags.Include;
import liqp.tags.Tag;

import java.io.File;
import java.util.*;

public final class LiquidRenderer extends Renderer {

    private final Map<String,? super Object> g_vars = new HashMap<>();
    private final List<Filter> g_filters    = new ArrayList<>();
    private final List<Tag> g_tags          = new ArrayList<>();

    private final Map<String,LiquidSet> f_set = new HashMap<>();

    private static final ParseSettings parse   = new ParseSettings.Builder().withFlavor(Flavor.JEKYLL).build();
    private static final RenderSettings render = new RenderSettings.Builder().withShowExceptionsFromInclude(true).build();

    LiquidRenderer(final File includes){
        g_vars.put(Include.INCLUDES_DIRECTORY_KEY, includes);
    }

    @SuppressWarnings("CollectionAddAllCanBeReplacedWithConstructor")
    @Override
    public byte[] render(final FileRender render){
        final Template template = Template.parse(Objects.requireNonNullElse(render.getContentAsString(), ""), parse);

        final Map<String,? super Object> map = new HashMap<>();
        // apply global set
        {
            map.putAll(g_vars);
            for(final Filter filter : g_filters)
                template.with(filter);
            for(final Tag tag : g_tags)
                template.with(tag);
        }

        // apply local set
        {
            final LiquidSet set;
            if((set = f_set.get(render.getInputFile().getAbsolutePath())) != null){
                map.putAll(set.getVars());
                for(final Filter filter : set.getFilters())
                    template.with(filter);
                for(final Tag tag : set.getTags())
                    template.with(tag);
            }
        }

        return asBytes(template.withRenderSettings(LiquidRenderer.render).render(Collections.unmodifiableMap(render.getFrontMatter())));
    }

    // global set

    public synchronized final void addVar(final String key, final Object value){
        g_vars.putIfAbsent(key,value);
    }

    public synchronized final void addFilter(final Filter filter){
        g_filters.add(filter);
    }

    public synchronized final void addTag(final Tag tag){
        g_tags.add(tag);
    }

    // local set

    public synchronized final void addVar(final File file, final String key, final Object value){
        final String abs = file.getAbsolutePath();
        if(!f_set.containsKey(abs))
            f_set.put(abs, new LiquidSet());
        f_set.get(abs).addVar(key, value);
    }

    public synchronized final void addFilter(final File file, final Filter filter){
        final String abs = file.getAbsolutePath();
        if(!f_set.containsKey(abs))
            f_set.put(abs, new LiquidSet());
        f_set.get(abs).addFilter(filter);
    }

    public synchronized final void addTag(final File file, final Tag tag){
        final String abs = file.getAbsolutePath();
        if(!f_set.containsKey(abs))
            f_set.put(abs,new LiquidSet());
        f_set.get(abs).addTag(tag);
    }

}
