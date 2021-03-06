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
	public Response getTwitterMessages() {
		
		String entrada = TwitterStreamer.getLastUserInteraction();
		entrada.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace(":", "=");
		
		String x = "{evento:Mensaje Recibido en Twitter, texto:\"" + entrada + "\"}";
		
		JSONObject respuesta;
		try {
			respuesta = new JSONObject(x);
		} catch (JSONException e) {
			respuesta = new JSONObject();
		}

		return Response.ok()
				.entity(respuesta)
				.header("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept")
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD, OPTIONS")
				.build();
	}
}
