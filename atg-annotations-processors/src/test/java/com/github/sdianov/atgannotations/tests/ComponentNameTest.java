package com.github.sdianov.atgannotations.tests;

import com.github.sdianov.atgannotations.processors.data.ComponentName;
import org.junit.Test;
import static org.junit.Assert.*;

public class ComponentNameTest {

    @Test
    public void testFromString(){
        ComponentName name = ComponentName.fromString("/org/example/TestComponent");

        assertEquals(name.toString(), "/org/example/TestComponent");
    }

    @Test
    public void testFromClassName(){
        ComponentName name = ComponentName.fromClassName("org.example.TestClass");

        assertEquals(name.toString(), "/org/example/TestClass");
    }
}
