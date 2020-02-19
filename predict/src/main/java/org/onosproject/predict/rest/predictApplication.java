package org.onosproject.predict.rest;

import org.onlab.rest.AbstractWebApplication;

import java.util.Set;

public class predictApplication extends AbstractWebApplication{
    @Override
    public Set<Class<?>> getClasses() {
        return getClasses(predictResource.class);
    }
}
