package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.processors.data.PropertyFileData;
import com.github.sdianov.atgannotations.processors.data.PropertyRecordData;

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

        for (String c : data.headerComments) {
            sb.append("# ").append(c).append("\n");
        }
        sb.append("$class=").append(data.className).append("\n");

        if (data.scope != null && !data.scope.isEmpty()) {
            sb.append("$scope=").append(data.scope).append("\n\n");
        }

        if (data.rawLines != null && data.rawLines.size() > 0) {
            for (String line : data.rawLines) {
                sb.append(line).append("\n");
            }
            sb.append("\n");
        }

        for (PropertyRecordData record : data.properties) {
            sb.append(record.name).append("=");

            if (record.values.size() > 1) {
                for(String value : record.values) {
                    sb.append("\\\n");
                    sb.append("\t").append(value);
                }
                sb.append("\n");
            } else {
                sb.append(record.values.get(0)).append("\n\n");
            }
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

        Path propertyPath = filePath.resolve(data.componentName.name + ".properties");


        try (PrintWriter wr = new PrintWriter(propertyPath.toFile())) {
            wr.print(s);
            wr.flush();
        }

    }

}
