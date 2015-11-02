package resources;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import twitter4j.Status;
import utilities.TwitterJSONParser;
import utilities.TwitterStreamer;

@Path("/mensajes")
public class RestResource {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getTwitterMessages() {
		ArrayList<Status> twitList = TwitterStreamer.getLastTweets();
		String respuesta = "";
		for(Status stauts : twitList) {
			respuesta += "<h1>" + TwitterJSONParser.parseTwitt(stauts) + "</h1>";
		}
		return respuesta;
	}
}
