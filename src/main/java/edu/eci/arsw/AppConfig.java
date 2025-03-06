package edu.eci.arsw;


import edu.eci.arsw.blueprints.persistence.BlueprintFilter;
import edu.eci.arsw.blueprints.persistence.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.persistence.impl.SubsamplingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "customRedundancyFilter")
    public BlueprintFilter redundancyFilter() {
        return new RedundancyFilter();
    }
    @Bean(name = "customSubsamplingFilter")
    public BlueprintFilter subsamplingFilter() {
        return new SubsamplingFilter();
    }
}