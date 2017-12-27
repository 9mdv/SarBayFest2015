package ph.gov.sarangani.sarbayforever;

import java.io.StringReader;
import java.net.URI;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.client.methods.HttpGet;
import org.xml.sax.InputSource;

import ph.gov.sarangani.sarbayforever.utils.RSSHandler;
import ph.gov.sarangani.sarbayforever.utils.RSSHandler.NewsItem;
import ph.gov.sarangani.sarbayforever.utils.RestTask;
import ph.gov.sarangani.sarbayforever.utils.RestTask.ResponseCallback;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BulletinActivity extends Activity implements ResponseCallback {
	private static final String TAG = "FeedReader";
	private static final String FEED_URI = "http://192.168.0.100/wordpress/feed/";
	private ListView mList;
	private ArrayAdapter<NewsItem> mAdapter;
	private ProgressDialog mProgress;
	private ActionBar ab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
		ab = getActionBar();
		ab.setTitle("EVENT BULLETIN");
		ab.setHomeButtonEnabled(false);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b5218d")));

		mList = new ListView(this);
		mAdapter = new ArrayAdapter<NewsItem>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1);
		mList.setAdapter(mAdapter);
		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				NewsItem item = mAdapter.getItem(position);
				Intent intent = new Intent(BulletinActivity.this,
						InfoWebView.class);
				intent.setData(Uri.parse(item.link));
				startActivity(intent);
				overridePendingTransition(R.anim.pseudo_flip_in,
						R.anim.pseudo_flip_out);
			}

		});
		setContentView(mList);
	}

	@Override
	public void onResume() {
		super.onResume();
		// Retrieve the RSS feed
		try {
			HttpGet feedRequest = new HttpGet(new URI(FEED_URI));
			RestTask task = new RestTask();
			task.setResponseCallback(this);
			task.execute(feedRequest);
			mProgress = ProgressDialog.show(this, null, "Please wait...",
					false, true);
		} catch (Exception e) {
			Log.w(TAG, e);
		}
	}

	@Override
	public void onRequestSuccess(String response) {
		if (mProgress != null) {
			mProgress.dismiss();
			mProgress = null;
		}
		// Process the response data
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser p = factory.newSAXParser();
			RSSHandler parser = new RSSHandler();
			p.parse(new InputSource(new StringReader(response)), parser);
			mAdapter.clear();
			for (NewsItem item : parser.getParsedItems()) {
				mAdapter.add(item);
			}
			mAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			Log.w(TAG, e);
		}
	}

	@Override
	public void onRequestError(Exception error) {
		if (mProgress != null) {
			mProgress.dismiss();
			mProgress = null;
		}
		// Display the error
		mAdapter.clear();
		mAdapter.notifyDataSetChanged();
		Toast.makeText(this, /* error.getMessage() */
		"Problem loading page, please try again later.", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
	}
}