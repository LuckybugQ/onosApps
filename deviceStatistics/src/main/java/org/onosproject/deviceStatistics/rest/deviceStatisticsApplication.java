package org.onosproject.deviceStatistics.rest;

import org.onlab.rest.AbstractWebApplication;
import org.onosproject.deviceStatistics.rest.deviceStatisticsResource;

import java.util.Set;

public class deviceStatisticsApplication extends AbstractWebApplication {
    @Override
    public Set<Class<?>> getClasses() {
        return getClasses(deviceStatisticsResource.class);
    }
}
