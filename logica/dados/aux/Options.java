package Planet_Bound.logica.dados.aux;

import Planet_Bound.logica.dados.resourcesandplanets.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class Options {

    final Resource headerResource;
    final String headerString;

    final Vector<Resource> optionsResource;
    Vector<String> optionsString;

    int optionsSize;

    public Options(String header, Vector<String> optionsString) {
        this.headerString = header;
        this.optionsString = optionsString;
        optionsSize = optionsString.size();
        headerResource = null;
        optionsResource = null;
    }

    public Options(String header, Vector<String> optionsString, Vector<Resource> optionsResources) {
        this.headerString = header;
        this.headerResource = null;
        this.optionsResource = optionsResources;
        this.optionsString = optionsString;
    }

    public Options(String header, Resource resource, Vector<String> strings, Vector<Resource> resources) {
        headerString = header;
        headerResource = resource;
        optionsResource = resources;
        optionsString = strings;
    }

    public int getNOptions() {
        if (optionsString != null && optionsResource != null)
            return Math.min(optionsResource.size(), optionsString.size());
        if (optionsString != null)
            return optionsString.size();
        if (optionsResource != null)
            return optionsResource.size();
        else
            //erro, tudo esta null neste momento
            return -1;
    }

    public String getHeaderString() {
        return headerString;
    }

    public Resource getHeaderResource() {
        return headerResource;
    }

    public List<String> getOptionsString() {
        if (optionsString == null) {
            optionsString = new Vector<>();
            for (var i : optionsResource)
                optionsString.add(i.toString());
        }
        return Collections.unmodifiableList(optionsString);
    }

    public List<Resource> getOptionsResources() {
        if (optionsResource == null) return null;
        return Collections.unmodifiableList(optionsResource);
    }

    @Override
    public String toString() {
        if (optionsString != null)
            return optionsString.toString();
        else
            return optionsResource.toString();
    }
}
