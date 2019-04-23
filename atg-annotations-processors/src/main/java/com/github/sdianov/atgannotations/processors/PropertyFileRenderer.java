package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.processors.data.PropertyFileData;
import com.github.sdianov.atgannotations.processors.data.PropertyRecordData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.sdianov.atgannotations.processors.AnnotationUtils.isNullOrBlank;


public class PropertyFileRenderer {

    private final String generationPath;

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
            sb.append("$scope=").append(data.scope).append("\n");
        }

        if (!isNullOrBlank(data.description)) {
            sb.append("$description=").append(data.description).append("\n");
        }

        sb.append("\n");
        if (data.rawLines != null && data.rawLines.size() > 0) {
            for (String line : data.rawLines) {
                sb.append(line).append("\n");
            }
            sb.append("\n");
        }

        for (PropertyRecordData record : data.properties) {
            sb.append(record.name);

            sb.append(record.operation);
            sb.append("=");

            if (record.values.size() > 1) {
                for (int i = 0; i < record.values.size(); i++) {
                    String value = record.values.get(i);
                    sb.append("\\\n");
                    sb.append("\t").append(value);

                    if (i < record.values.size() - 1)
                        sb.append(",");
                }
                sb.append("\n\n");
            } else {
                sb.append(record.values.get(0)).append("\n\n");
            }
        }

        return sb.toString();
    }

    public void renderFile(PropertyFileData data) throws IOException {
        String s = renderContents(data);

        String filePath = FilenameUtils.concat(generationPath, data.componentName.fullPath());
        File dir = new File(filePath);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Unable to create directory:" + dir.getAbsolutePath());
        }

        String propertyPath = FilenameUtils.concat(filePath, data.componentName.getName() + ".properties");

        FileUtils.writeStringToFile(new File(propertyPath), s, Charset.defaultCharset());

    }

}
