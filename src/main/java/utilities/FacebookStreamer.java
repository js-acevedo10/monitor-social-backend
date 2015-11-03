package utilities;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;

public class FacebookStreamer {
	
	public FacebookStreamer() {
		Facebook facebook = new FacebookFactory().getInstance();
		ResponseList<Post> results = null;
		try {
			results = facebook.searchPosts("#transmilenio");
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		System.out.println(results.size());
	}
}
