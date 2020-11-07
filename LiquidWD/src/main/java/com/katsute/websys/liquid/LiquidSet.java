package com.katsute.websys.liquid;

import liqp.filters.Filter;
import liqp.tags.Tag;

import java.util.*;

final class LiquidSet {

    private final Map<String,Object> vars = new HashMap<>();
    private final List<Filter> filters    = new ArrayList<>();
    private final List<Tag> tags          = new ArrayList<>();

    LiquidSet(){ }

    public synchronized final void addVar(final String key, final Object value){
        vars.put(key,value);
    }

    public synchronized final void addFilter(final Filter filter){
        filters.add(filter);
    }

    public synchronized final void addTag(final Tag tag){
        tags.add(tag);
    }

    public final Map<String, Object> getVars(){
        return Collections.unmodifiableMap(vars);
    }

    public final List<Filter> getFilters(){
        return Collections.unmodifiableList(filters);
    }

    public final List<Tag> getTags(){
        return Collections.unmodifiableList(tags);
    }

}
