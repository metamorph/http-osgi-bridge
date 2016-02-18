package net.gnejs.bundle.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class RestEndpoint {
	@GET
	@Produces({ MediaType.TEXT_PLAIN})
	@Path("/tstamp")
	public String timestamp() {
		return "" + System.currentTimeMillis();
	}
}
