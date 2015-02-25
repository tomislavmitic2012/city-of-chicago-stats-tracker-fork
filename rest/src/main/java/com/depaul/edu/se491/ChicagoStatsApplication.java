package com.depaul.edu.se491;

import com.depaul.edu.se491.config.InMemorySecurityConfig;
import com.depaul.edu.se491.filters.CORSResponseFilter;
import com.depaul.edu.se491.filters.LoggingResponseFilter;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Created by adampodraza on 2/3/15.
 *
 *
 */

public class ChicagoStatsApplication extends ResourceConfig {

    public ChicagoStatsApplication() {
        packages("com.depaul.edu.se491");

        register(CORSResponseFilter.class);
        register(LoggingResponseFilter.class);
        register(EntityFilteringFeature.class);
        register(InMemorySecurityConfig.class);

        EncodingFilter.enableFor(this, org.glassfish.jersey.message.GZipEncoder.class);
    }
}
