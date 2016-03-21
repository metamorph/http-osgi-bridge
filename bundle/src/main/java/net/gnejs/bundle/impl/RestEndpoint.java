package net.gnejs.bundle.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/greeter")
public class RestEndpoint {
	@Path("/{name}")
	@GET
	public String sayHello(@PathParam("name") String name) {
		return "Hello, " + name;
	}
}
