package examples.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestJson {

    @JsonProperty
    private String name;
    @JsonProperty
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "RequestJson{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
