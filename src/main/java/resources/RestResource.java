package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/mensajes")
public class RestResource {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String hello() {
		return "<h1>Hola</h1>";
	}
}
