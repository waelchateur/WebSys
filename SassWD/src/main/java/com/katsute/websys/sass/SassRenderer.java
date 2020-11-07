package com.katsute.websys.sass;

import com.kttdevelopment.webdir.api.FileRender;
import com.kttdevelopment.webdir.api.Renderer;
import io.bit3.jsass.*;
import io.bit3.jsass.Compiler;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public final class SassRenderer extends Renderer {

    private final Compiler compiler = new Compiler();

    @Override
    public final byte[] render(final FileRender render){
        final File input  = render.getInputFile().getAbsoluteFile();
        final String name = FilenameUtils.getBaseName(input.getName());

        final String content = render.getContentAsString();
        if(!content.isEmpty() && !name.startsWith("_"))
            try{
                final String abs = render.getOutputFile().getAbsoluteFile().getParentFile().getAbsolutePath();

                final Path css = Paths.get(abs, name + ".css");
                final Path map = Paths.get(abs, name + ".css.map");

                final Options options = new Options();
                // options.setSourceMapFile(Paths.get(render.getInputFile().getAbsoluteFile().getParentFile().getAbsolutePath(), name + ".css.map").toUri());

                final Output out = compiler.compileString(content, input.toURI(), input.toURI(), options);

                Files.write(css, out.getCss().getBytes(StandardCharsets.UTF_8));
                Files.write(map,out.getSourceMap().getBytes(StandardCharsets.UTF_8));
            }catch(final CompilationException | IOException ignored){ }
        else if(name.startsWith("_"))
            render.setOutputFile(null);

        return super.render(render);
    }

}
