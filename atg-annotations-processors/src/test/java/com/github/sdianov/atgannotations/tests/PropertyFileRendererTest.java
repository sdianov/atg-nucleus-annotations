package com.github.sdianov.atgannotations.tests;

import com.github.sdianov.atgannotations.processors.PropertyFileRenderer;
import com.github.sdianov.atgannotations.processors.data.PropertyFileData;
import org.junit.Before;
import org.junit.Test;

public class PropertyFileRendererTest {

    PropertyFileData data;
    PropertyFileRenderer renderer;

    @Before
    public void init(){

        renderer = new PropertyFileRenderer(".");

        data = new PropertyFileData();
        data.className = "com.example.Service";

    }

    @Test
    public void renderContentsTest(){

        String text = renderer.renderContents(data);

        assert text.equals("$class=com.example.Service\n");
    }

}
