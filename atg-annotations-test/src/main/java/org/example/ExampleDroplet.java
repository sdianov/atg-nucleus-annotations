package org.example;

import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;
import atg.userprofiling.ProfileTools;
import com.github.sdianov.atgannotations.NucleusComponent;
import com.github.sdianov.atgannotations.NucleusInject;
import com.github.sdianov.atgannotations.Scope;

import javax.servlet.ServletException;
import java.io.IOException;

@NucleusComponent(scope = Scope.GLOBAL,
        description = "Example droplet component")
public class ExampleDroplet extends DynamoServlet {

    private ExampleTools exampleTools;

    public ProfileTools getProfileTools() {
        return profileTools;
    }

    @NucleusInject(name = "/atg/userprofiling/ProfileTools")
    public void setProfileTools(ProfileTools profileTools) {
        this.profileTools = profileTools;
    }

    private ProfileTools profileTools;

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
