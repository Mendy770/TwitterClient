package com.codepath.apps.javid;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	EditText etTweet = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		etTweet = (EditText)findViewById(R.id.etTweet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}
	
	public void onCancelClick() {
		Intent i = new Intent(this, TimelineActivity.class);
		startActivity(i);
	}
	
	public void onTweetClick(View v) {
		String tweet = etTweet.getText().toString();
        MyTwitterApp.getRestClient().params.put("status", tweet);
        MyTwitterApp.getRestClient().postTweet(new JsonHttpResponseHandler() {
                public void onSuccess(JSONObject response) {
                	Intent data = new Intent();
                    data.putExtra("mode",2);
                    setResult(RESULT_OK, data);                                
                    finish();
                }
        });
	}

}
