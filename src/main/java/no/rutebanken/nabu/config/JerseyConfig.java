package no.rutebanken.nabu.config;

import no.rutebanken.nabu.rest.FileUploadResource;
import no.rutebanken.nabu.rest.StatusResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(MultiPartFeature.class);
        register(FileUploadResource.class);
        register(StatusResource.class);
    }

}
