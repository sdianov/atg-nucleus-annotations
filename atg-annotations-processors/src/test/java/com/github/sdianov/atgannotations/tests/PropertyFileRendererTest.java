package com.github.sdianov.atgannotations.tests;

import com.github.sdianov.atgannotations.processors.PropertyFileRenderer;
import com.github.sdianov.atgannotations.processors.data.ComponentName;
import com.github.sdianov.atgannotations.processors.data.PropertyFileData;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.processing.Filer;

import static org.mockito.Mockito.*;

public class PropertyFileRendererTest {

    PropertyFileData data;
    PropertyFileRenderer renderer;

    @Before
    public void init() {

        Filer filer = mock(Filer.class);

        renderer = new PropertyFileRenderer(".", filer );

        data = new PropertyFileData();
        data.className = "com.example.Service";
        data.componentName = ComponentName.fromString("/org/example/Test");
    }

    @Test
    public void renderContentsTest() {

        String text = renderer.renderContents(data);

        //assert text.equals("$class=com.example.Service\n");
    }

}
