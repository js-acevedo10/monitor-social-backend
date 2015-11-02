package utilities;

import twitter4j.Status;

public class TwitterJSONParser {
	public static String parseTwitt(Status status) {
		String respuesta = status.getText().toLowerCase();
		return respuesta;
	}
}
