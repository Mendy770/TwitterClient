package com.codepath.apps.javid;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * 
 * @author yuvaln
 *
 */
@Table(name = "tweets")
public class TweetModel extends Model {
	
	@Column(name = "userProfileImage")
	public String userProfileImage;
	
	@Column(name = "userName")
	public String userName;
	
	@Column(name = "userScreenName")
	public String userScreenName;
	
	@Column(name = "tweetBody")
	public String tweetBody;
	
	
	public TweetModel() {
		super();
	}
	
	public TweetModel(String userProfileImage, String userName, String userScreenName, String tweetBody) {
		super();
		this.userProfileImage = userProfileImage;
		this.userName = userName;
		this.userScreenName = userScreenName;
		this.tweetBody = tweetBody;
	}
	
	private static void deleteAll() {
		List<TweetModel> tweets = new Select().from(TweetModel.class).execute();
		for (int i = 0; i < tweets.size(); i++) {
			tweets.get(i).delete();
		}
	}
	
	public static void store(ArrayList<Tweet> tweets) {
		deleteAll();
		TweetModel tweet = null;
		for (int i = 0; i < tweets.size(); i++) {
			tweet = new TweetModel(tweets.get(i).getUser().getProfileBackgroundImageUrl(),
					tweets.get(i).getUser().getName(),
					tweets.get(i).getUser().getScreenName(),
					tweets.get(i).getBody());
			
			tweet.save();
		}
	}
	
	public static ArrayList<Tweet> extract(List<TweetModel> tweetModels) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		Tweet tweet = null;
		User user = null;
		for (int i = 0; i < tweetModels.size(); i++) {
			user = new User();
			user.name = tweetModels.get(i).userName;
			user.screenName = tweetModels.get(i).userScreenName;
			user.profileBgImageUrl = tweetModels.get(i).userProfileImage;
			tweet = new Tweet();
			tweet.user = user;
			tweet.body = tweetModels.get(i).tweetBody;
			tweets.add(tweet);
		}
		return tweets;
	}
	
	public static ArrayList<Tweet> getTweetsFromDB() {
		List<TweetModel> tweets = new Select().from(TweetModel.class).execute();
		return extract(tweets);
	}
}
