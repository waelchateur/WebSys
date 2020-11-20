package com.katsute.websys.renderers.liquid;

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

    private static final ParseSettings parse   = new ParseSettings.Builder().withFlavor(Flavor.JEKYLL).build();
    private static final RenderSettings render = new RenderSettings.Builder().withShowExceptionsFromInclude(true).build();

    public LiquidRenderer(final File includes){
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

        return asBytes(template.withRenderSettings(LiquidRenderer.render).render(Collections.unmodifiableMap(render.getFrontMatter())));
    }

    // global set

    public synchronized final void addFilter(final Filter filter){
        g_filters.add(filter);
    }

    public synchronized final void addTag(final Tag tag){
        g_tags.add(tag);
    }

}
