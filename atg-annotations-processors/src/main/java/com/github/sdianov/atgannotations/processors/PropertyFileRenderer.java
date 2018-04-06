package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.processors.data.PropertyFileData;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PropertyFileRenderer {

    final String generationPath;

    public PropertyFileRenderer(String pGenPath) {
        generationPath = pGenPath;
    }

    public String renderContents(PropertyFileData data) {
        final StringBuilder sb = new StringBuilder();

        sb.append("# ").append(data.componentName.toString()).append("\n");
        sb.append("$class=").append(data.className).append("\n");

        if (data.scope != null && !data.scope.isEmpty()){
            sb.append("$scope=").append(data.scope).append("\n");
        }

        return sb.toString();
    }

    public void renderFile(PropertyFileData data) throws IOException {
        String s = renderContents(data);

        String[] context = data.componentName.path.toArray(new String[data.componentName.path.size()]);
        Path filePath = Paths.get(generationPath, context);
        File dir = filePath.toFile();
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Unable to create directory:" + dir.getAbsolutePath());
        }

        Path propertyPath = filePath.resolve( data.componentName.name + ".properties");


        try(PrintWriter wr = new PrintWriter(propertyPath.toFile())) {
            wr.print(s);
            wr.flush();
        }

    }

}
