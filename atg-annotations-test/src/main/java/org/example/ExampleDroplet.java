package org.example;

import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;
import atgannotations.NucleusComponent;
import atgannotations.NucleusInject;

import javax.servlet.ServletException;
import java.io.IOException;

@NucleusComponent
public class ExampleDroplet extends DynamoServlet {

    private ExampleTools exampleTools;

    @Override
    public void service(DynamoHttpServletRequest req, DynamoHttpServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    public ExampleTools getExampleTools() {
        return exampleTools;
    }

    @NucleusInject
    public void setExampleTools(ExampleTools exampleTools) {
        this.exampleTools = exampleTools;
    }
}
