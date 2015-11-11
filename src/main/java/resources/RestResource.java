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
		
		JSONObject entrada = TwitterStreamer.getLastUserInteraction();
		
		String evento = entrada.getString("evento");
		JSONObject respuesta = null;
		
		switch (evento) {
		case "onStatus":
			respuesta = new JSONObject()
			.append("tipo", evento)
			.append("text", entrada.getString("text"));
			JSONObject usuario = entrada.getJSONObject("user");
			respuesta.append("user-id", usuario.getString("id"));
			respuesta.append("name", usuario.getString("name"));
			break;
		case "follow":
			respuesta = new JSONObject()
			.append("tipo", evento)
			.append("text", "El usuario " + entrada.getString("source") + " ha comenzado a seguir a " + entrada.getString("followedUser"));
			break;
		default:
			break;
		}
		
		return Response.status(200).entity(respuesta).build();
	}
}
