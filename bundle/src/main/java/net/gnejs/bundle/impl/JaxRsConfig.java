package net.gnejs.bundle.impl;

import com.eclipsesource.jaxrs.publisher.ApplicationConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andersen on 19/02/16.
 */
public class JaxRsConfig implements ApplicationConfiguration {
	@Override public Map<String, Object> getProperties() {
		return new HashMap<String, Object>(){{
			put("jersey.config.server.tracing.type", "ON_DEMAND");
			put("jersey.config.server.tracing.threshold", "TRACE");
		}
		};
	}
}
