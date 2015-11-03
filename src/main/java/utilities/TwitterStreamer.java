package utilities;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamer {
	StatusListener statusListener;
	UserStreamListener userStreamListener;
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
	public static String lastUserInteraction;
	
	public TwitterStreamer(String[] keywords, String[] languages, double[][] locations, boolean saveSome) {
		this.keywords = keywords;
		this.languages = languages;
		this.locations = locations;
		this.saveSome = saveSome;
		saved = new ArrayList<Status>();
		statusCounter = 0;
		lastUserInteraction = "<h1>No ha ocurrido nada nuevo";
	}
	
	public void startStreaming() throws Exception {
		
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		String query = "select * from users";
		ResultSet rs = statement.executeQuery(query);
		String accessToken = "";
		String accessSecret = "";
		String nombre = "";
		while(rs.next()) {
			System.out.println(rs.getString(1));
			accessToken = rs.getString(3);
			accessSecret = rs.getString(4);
			nombre = rs.getString(2);
		}
		lastUserInteraction += " en el perfil de " + nombre + "</h1>";
		
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
		
		userStreamListener = new UserStreamListener() {
			
			public void onException(Exception ex) {}
			
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
			
			public void onStatus(Status status) {}
			
			public void onStallWarning(StallWarning warning) {}
			
			public void onScrubGeo(long userId, long upToStatusId) {}
			
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
			
			public void onUserSuspension(long suspendedUser) {}
			
			public void onUserProfileUpdate(User updatedUser) {}
			
			public void onUserListUpdate(User listOwner, UserList list) {}
			
			public void onUserListUnsubscription(User subscriber, User listOwner,
					UserList list) {}
			
			public void onUserListSubscription(User subscriber, User listOwner,
					UserList list) {}
			
			public void onUserListMemberDeletion(User deletedMember, User listOwner,
					UserList list) {}
			
			public void onUserListMemberAddition(User addedMember, User listOwner,
					UserList list) {}
			
			public void onUserListDeletion(User listOwner, UserList list) {}
			
			public void onUserListCreation(User listOwner, UserList list) {}
			
			public void onUserDeletion(long deletedUser) {}
			
			public void onUnfollow(User source, User unfollowedUser) {}
			
			public void onUnfavorite(User source, User target, Status unfavoritedStatus) {}
			
			public void onUnblock(User source, User unblockedUser) {}
			
			public void onRetweetedRetweet(User source, User target,
					Status retweetedStatus) {}
			
			public void onQuotedTweet(User source, User target, Status quotingTweet) {}
			
			public void onFriendList(long[] friendIds) {}
			
			public void onFollow(User source, User followedUser) {
				System.out.println(source.getScreenName() + " ---- " + followedUser.getName());
				lastUserInteraction = "<h2>" + source.getName() + "(@" + source.getScreenName() + ")" + " ha comenzado a seguir a " + followedUser.getName() + "(@" + followedUser.getScreenName() + ")" + "</h2>";
			}
			
			public void onFavoritedRetweet(User source, User target,
					Status favoritedRetweeet) {}
			
			public void onFavorite(User source, User target, Status favoritedStatus) {}
			
			public void onDirectMessage(DirectMessage directMessage) {}
			
			public void onDeletionNotice(long directMessageId, long userId) {}
			
			public void onBlock(User source, User blockedUser) {}
		};
		
		twitterStream = new TwitterStreamFactory().getInstance();
//		twitterStream.setOAuthAccessToken(new AccessToken("2334095631-QwupYxwRjThfNx7s4j24Vo28ylS64NFs7LA4Fqh", "4XVrQTuvs02XO6WTqhwJz8Pq2P9m4B00JI4X0lVZnafV9"));
		twitterStream.setOAuthAccessToken(new AccessToken(accessToken, accessSecret));
	    //twitterStream.addListener(statusListener);
		twitterStream.addListener(userStreamListener);
	    
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
	    twitterStream.user();
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
	
	public static String getLastUserInteraction() {
		return lastUserInteraction;
	}
	
	public static int getCount() {
		return statusCounter;
	}
	
	private static Connection getConnection() throws URISyntaxException, SQLException {
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

	    return DriverManager.getConnection(dbUrl, username, password);
	}
}
