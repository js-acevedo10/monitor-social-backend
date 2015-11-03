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
//		ArrayList<Status> twitList = TwitterStreamer.getLastTweets();
//		String respuesta = "";
//		for(Status stauts : twitList) {
//			respuesta += TwitterJSONParser.parseTwitt(stauts);
//		}
//		if(respuesta.equalsIgnoreCase("")) {
//			respuesta = "0 tweets.";
//		}
//		return respuesta;
		String respuesta = "A&uacute;n no se han procesado Tweets";
		int counter = TwitterStreamer.getCount();
		if(counter > 0) {
			Status lastStatus = TwitterStreamer.getLastTweet();
			respuesta = "<h1>N&uacute;mero de Tweets Procesados:" + counter + "</h1><h1>&Uacute;ltimo Tweet Procesado: " + "</h1>" + TwitterJSONParser.parseTwitt(lastStatus);
		}
		
		return respuesta;
	}
}
