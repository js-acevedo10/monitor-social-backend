package utilities;

import java.util.ArrayList;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamer {
	StatusListener statusListener;
	TwitterStream twitterStream;
	ConfigurationBuilder cb;
	FilterQuery fq;
	
	private String[] keywords;
	private String[] languages;
	private double[][] locations;
	private boolean saveSome;
	public static ArrayList<Status> saved;
	public static Status lastStatus;
	public static int statusCounter;
	
	public TwitterStreamer(String[] keywords, String[] languages, double[][] locations, boolean saveSome) {
		this.keywords = keywords;
		this.languages = languages;
		this.locations = locations;
		this.saveSome = saveSome;
		saved = new ArrayList<Status>();
		statusCounter = 0;
	}
	
	public void startStreaming() {
		statusListener = new StatusListener() {
			public void onException(Exception e) {
				e.printStackTrace();
			}
			public void onTrackLimitationNotice(int limitNotice) {}
			public void onStatus(Status status) {
				if(saveSome) {
					statusCounter++;
					lastStatus = status;
				}
			}
			public void onStallWarning(StallWarning stallWarning) {}
			public void onScrubGeo(long arg0, long arg1) {}
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
		};
		
		cb = new ConfigurationBuilder();
		cb.setOAuthConsumerSecret("dAg6JZB84XXlKn21VGqxGSofaJtkIaD8c9pUrqCJJbyWSMZ2bD");
		cb.setOAuthConsumerKey("bGPtpsnIx0eQJuVtXtHncfZUA");
		cb.setOAuthAccessToken("2334095631-QwupYxwRjThfNx7s4j24Vo28ylS64NFs7LA4Fqh");
		cb.setOAuthAccessTokenSecret("4XVrQTuvs02XO6WTqhwJz8Pq2P9m4B00JI4X0lVZnafV9");
		
		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
	    twitterStream.addListener(statusListener);
	    
	    fq = new FilterQuery();
	    if(keywords != null && keywords.length != 0) {
	    	fq.track(keywords);
	    	for(String a : keywords) {
	    		System.out.print(a);
	    	}
	    	System.out.println();
	    }
	    if(languages != null && languages.length != 0) {
	    	fq.language(languages);
	    	for(String a : languages) {
	    		System.out.print(a);
	    	}
	    	System.out.println();
	    }
	    if(locations != null && languages.length != 0) {
	    	fq.locations(locations);
	    }
	    twitterStream.filter(fq);
	}
	
	public void stopStreaming() {
		twitterStream.shutdown();
		System.out.println("---------------------Streaming detenido---------------------");
	}
	
	public static ArrayList<Status> getLastTweets() {
		System.out.println(saved.size());
		return saved;
	}
	
	public static Status getLastTweet() {
		return lastStatus;
	}
	
	public static int getCount() {
		return statusCounter;
	}
}
