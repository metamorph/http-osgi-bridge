package net.gnejs.bundle.impl;

import com.eclipsesource.jaxrs.publisher.ApplicationConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/greeter")
public class RestEndpoint implements ApplicationConfiguration {
	@Path("/{name}")
	@GET
	public String sayHello(@PathParam("name") String name) {
		return "Hello, " + name;
	}

	@Override public Map<String, Object> getProperties() {
		Map<String, Object> cfg = new HashMap<>();
		cfg.put("jersey.config.server.tracing.type", "ALL");
		cfg.put("jersey.config.server.tracing.threshold", "TRACE");
		return cfg;
	}
}
