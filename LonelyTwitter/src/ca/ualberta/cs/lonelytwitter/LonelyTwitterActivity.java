package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import ca.ualberta.cs.lonelytwitter.data.GsonDataManager;
import ca.ualberta.cs.lonelytwitter.data.IDataManager;

public class LonelyTwitterActivity extends Activity {

	private IDataManager dataManager;

	private EditText bodyText;

	private ListView oldTweetsList;

	private ArrayList<Tweet> tweets;

	private ArrayAdapter<Tweet> tweetsViewAdapter;
	
	private Summary mysummary;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		dataManager = new GsonDataManager(this);

		bodyText = (EditText) findViewById(R.id.body);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);
		mysummary = new Summary();
	}

	@Override
	protected void onStart() {
		super.onStart();

		tweets = dataManager.loadTweets();
		tweetsViewAdapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweets);
		oldTweetsList.setAdapter(tweetsViewAdapter);
	}

	public void save(View v) {

		String text = bodyText.getText().toString();

		Tweet tweet = new Tweet(new Date(), text);
		tweets.add(tweet);

		tweetsViewAdapter.notifyDataSetChanged();

		bodyText.setText("");
		dataManager.saveTweets(tweets);
	}

	public void clear(View v) {

		tweets.clear();
		tweetsViewAdapter.notifyDataSetChanged();
		dataManager.saveTweets(tweets);
	}
	
	public void showSummary(View v){
		createSummary();
		
		//intent to new activity
		Intent summaryIntent = new Intent(this, SummaryActivity.class);
		summaryIntent.putExtra("numtweets", String.valueOf(mysummary.getNumoftweets()));
		summaryIntent.putExtra("lentweets", String.valueOf(mysummary.getLenoftweets()));
		//Bundle sumBundle = new Bundle();
		//sumBundle.putSerializable("mysummary", mysummary);
		//summaryIntent.putExtras(sumBundle);
		//Initialize
		startActivity(summaryIntent);
	}
	
	private void createSummary(){
		mysummary.setLenoftweets(getAverageLength());
		mysummary.setNumoftweets(getAverageNumber());
		//dataManager.saveSummary();
	}
	
	private long getAverageNumber(){
		return tweets.size();
	}
	
	private long getAverageLength(){
		int result = 0;
		for (int i = tweets.size()-1;i==0;i--){
			result = result + tweets.get(i).getTweetBody().length();
		}
		return result / getAverageNumber();
	}

}