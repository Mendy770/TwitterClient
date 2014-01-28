package com.codepath.apps.javid;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	
	public final int REQUEST_ID = 888;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		Intent intent = getIntent();
		String isConnected = intent.getStringExtra("isConnected");
		
		if (isConnected == "1") {
		
			MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
				
				@Override
				public void onSuccess(JSONArray jsonTweets) {
					ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
					TweetModel.store(tweets);
					ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
					TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
					lvTweets.setAdapter(adapter);
				}
			});
		}
		else {
			ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
			TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), TweetModel.getTweetsFromDB());
			lvTweets.setAdapter(adapter);
		}
	}
	
	public void onComposeClick(MenuItem mi) {
		Intent i= new Intent(this, ComposeTweetActivity.class);
		startActivityForResult(i, REQUEST_ID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_ID) {
            MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
               public void onSuccess(JSONArray jsonTweets) {
                   ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
                   ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
                   TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
                   lvTweets.setAdapter(adapter);
               }
           });
        }
    } 

}
