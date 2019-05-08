package org.example;

import atg.nucleus.GenericService;
import com.github.sdianov.atgannotations.*;

import java.util.List;
import java.util.Map;

@NucleusComponent(scope = Scope.GLOBAL,
        name = "/org/example/ExampleAnnotatedTools",
        rawLines = {
                "# additional comment",
                "$instanceFactory=/atg/multisite/Subclasser"
        }
)
@NucleusRegister(registryName = "/atg/Initial", propertyName = "initialServices")
class ExampleTools extends GenericService {

    private String stringValue;

    private List<String> listValue;

    private Map<String, String> mapValue;

    private Object refValue;

    public String getStringValue() {
        return stringValue;
    }

    @NucleusValue("1234")
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }


    public List<String> getListValue() {
        return listValue;
    }

    @NucleusValue(
            list = {"123", "456"})
    public void setListValue(List<String> listValue) {
        this.listValue = listValue;
    }

    public Map<String, String> getMapValue() {
        return mapValue;
    }

    @NucleusValue(map = {
            @MapValue(key = "a", value = "b"),
            @MapValue(key = "c", value = "d")
    },
            operation = CollectionOperation.APPEND)
    public void setMapValue(Map<String, String> mapValue) {
        this.mapValue = mapValue;
    }

    public Object getRefValue() {
        return refValue;
    }

    @NucleusValue(propertyRef = "/Constants.null")
    public void setRefValue(Object refValue) {
        this.refValue = refValue;
    }
}
