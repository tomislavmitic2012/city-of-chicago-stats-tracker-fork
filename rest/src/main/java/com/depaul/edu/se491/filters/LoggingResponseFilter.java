package com.depaul.edu.se491.filters;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Created by Tomislav S. Mitic on 1/24/15.
 */
public class LoggingResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);

    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        String method = requestContext.getMethod();

        logger.debug("Requesting " + method + " for path " + requestContext.getUriInfo().getPath());
        Object entity = responseContext.getEntity();
        if (entity != null) {
            logger.debug("Response " +
                    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entity));
        }

    }
}
