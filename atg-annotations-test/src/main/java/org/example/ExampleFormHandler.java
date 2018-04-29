package org.example;

import atg.droplet.GenericFormHandler;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import com.github.sdianov.atgannotations.NucleusComponent;
import com.github.sdianov.atgannotations.NucleusInject;
import com.github.sdianov.atgannotations.Scope;

@NucleusComponent(scope = Scope.REQUEST)
public class ExampleFormHandler extends GenericFormHandler {

    private ExampleTools exampleTools;

    public boolean handleSubmit(DynamoHttpServletRequest pRequest,
                                 DynamoHttpServletResponse pResponse) {

        return true;
    }

    public ExampleTools getExampleTools() {
        return exampleTools;
    }

    @NucleusInject
    public void setExampleTools(ExampleTools exampleTools) {
        this.exampleTools = exampleTools;
    }
}
