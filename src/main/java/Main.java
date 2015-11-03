import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import utilities.FacebookStreamer;
import utilities.TwitterStreamer;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class Main {
	
	private static String fileName = "src/main/resources/twitter-track.properties";

	public static void main(String[] args) throws IOException {
		final String baseUri = "http://localhost:"+(System.getenv("PORT")!=null?System.getenv("PORT"):"9998")+"/";
        final Map<String, String> initParams = new HashMap<String, String>();

        initParams.put("com.sun.jersey.config.property.packages","resources");
        
        System.out.println("Starting grizzly...");
        SelectorThread threadSelector = GrizzlyWebContainerFactory.create(baseUri, initParams);
        System.out.println(String.format("Jersey started with WADL available at %sapplication.wadl.",baseUri, baseUri));        
        startStreaming();
	}
	
	public static void startStreaming() {
		Properties properties = new Properties();
    	InputStream is = null;
    	String[] keywords = null;
    	String[] languages = null;
    	double[][] locations = null;
    	
    	try {
    		is = new FileInputStream(fileName);
    		properties.load(is);
    		String key = properties.getProperty("keywords");
    		keywords = key.split(",");
    		String lan = properties.getProperty("languages");
    		languages = lan.split(",");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	TwitterStreamer twitterStreamer = new TwitterStreamer(keywords, languages, locations, true);
        try {
        	twitterStreamer.startStreaming();
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
}
