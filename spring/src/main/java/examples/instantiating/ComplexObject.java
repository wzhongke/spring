package examples.instantiating;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author wangzhongke
 */
public class ComplexObject {

    private Properties adminEmails;

    private List someList;

    private Map someMap;

    private Set someSet;

    private List nullList;

    public List getSomeList() {
        return someList;
    }

    public void setSomeList(List someList) {
        this.someList = someList;
    }

    public Map getSomeMap() {
        return someMap;
    }

    public void setSomeMap(Map someMap) {
        this.someMap = someMap;
    }

    public Set getSomeSet() {
        return someSet;
    }

    public void setSomeSet(Set someSet) {
        this.someSet = someSet;
    }

    public Properties getAdminEmails() {
        return adminEmails;
    }

    public void setAdminEmails(Properties adminEmails) {
        this.adminEmails = adminEmails;
    }

    public List getNullList() {
        return nullList;
    }

    public void setNullList(List nullList) {
        this.nullList = nullList;
    }
}
