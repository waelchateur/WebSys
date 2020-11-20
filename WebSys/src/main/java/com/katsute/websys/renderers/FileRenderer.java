package com.katsute.websys.renderers;

import com.kttdevelopment.webdir.api.FileRender;
import com.kttdevelopment.webdir.api.Renderer;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class FileRenderer extends Renderer {

    @Override
    public byte[] render(final FileRender render){
        final File IN = render.getInputFile();

        final Map<String,? super Object> map = new HashMap<>(fileAsMap(IN));
        if(IN.isDirectory()){
            final List<Map<String,? super Object>> files = new ArrayList<>();
            for(final File listFile : Objects.requireNonNullElse(IN.listFiles(), new File[0]))
                files.add(fileAsMap(listFile));
            map.put("files", files);
        }
        render.getFrontMatter().put("file", map);

        return super.render(render);
    }

    private Map<String,? super Object> fileAsMap(final File file){
        final Map<String,? super Object> map = new HashMap<>();

        map.put("name", file.getName());
        try{
            map.put("path", file.getCanonicalPath());
        }catch(final IOException e){
            map.put("path", file.getAbsolutePath());
        }
        map.put("dir", file.isDirectory());
        map.put("hidden", file.isHidden());
        map.put("canWrite", file.canWrite());
        map.put("canRead", file.canRead());
        map.put("canExecute", file.canExecute());
        map.put("lastModified", file.lastModified());
        map.put("length", file.length());
        map.put("size", getUnitBytes(file.length()));

        try{
            map.put("parent", file.getParentFile().getName());
        }catch(final NullPointerException ignored){ }

        return map;
    }

    private final String[] units = {"B", "KB", "MB", "GB", "TB"};

    private String getUnitBytes(final long bytes){
        if(bytes <= 0) return "0";
        int digitGroups = (int) (Math.log10(bytes)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(bytes / Math.pow(1024, digitGroups)) + ' ' + units[digitGroups];
    }

}
