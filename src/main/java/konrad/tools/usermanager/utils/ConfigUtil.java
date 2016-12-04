package konrad.tools.usermanager.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Stworzone przez Konrad Botor dnia 2016-07-06.
 */
@Service
public class ConfigUtil {
    private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
    private static Map<String, String> configuration;

    @PostConstruct
    public void init() {
        logger.info("init");
        configuration = new TreeMap<>();
        configuration.putAll(System.getenv());
        Properties systemProperties = System.getProperties();
        for (Object property: systemProperties.keySet()) {
            configuration.put(property.toString(), systemProperties.getProperty(property.toString()));
        }
    }

    public String getProperty(String key) {
        logger.debug("getProperty(String)");
        return configuration.get(key);
    }

    public String getProperty(String key, String defaultValue) {
        logger.debug("getProperty(String, String)");
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public Set<Map.Entry<String, String>> getProperties() {
        return configuration.entrySet();
    }

    public Set<String> getPropertyKeys() {
        return configuration.keySet();
    }

}
