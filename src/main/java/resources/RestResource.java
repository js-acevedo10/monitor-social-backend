package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import utilities.TwitterStreamer;

@Path("/mensajes")
public class RestResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTwitterMessages() throws JSONException {
		
		String entrada = TwitterStreamer.getLastUserInteraction();
		entrada.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u");
		
		String x = "{evento:'Nuevo', texto:'" + entrada + "'}";
		
		JSONObject respuesta = new JSONObject(x);

		return Response.status(200).entity(respuesta).build();
	}
}
