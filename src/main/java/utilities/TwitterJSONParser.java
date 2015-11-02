package utilities;

import org.apache.commons.lang3.StringEscapeUtils;

import twitter4j.Status;

public class TwitterJSONParser {
	public static String parseTwitt(Status status) {
		String name = StringEscapeUtils.escapeHtml4(status.getUser().getName()).replaceAll("[^\\x20-\\x7e]", "");
		String screen_name = StringEscapeUtils.escapeHtml4("@" + status.getUser().getScreenName()).replaceAll("[^\\x20-\\x7e]", "");
		String text = StringEscapeUtils.escapeHtml4(status.getText()).replaceAll("[^\\x20-\\x7e]", "");
		
		String respuesta = "<h1>" + name + "</h1><h2>" + "" + screen_name + "</h2><p>" + text + "</p>";
		
		return respuesta;
	}
}
