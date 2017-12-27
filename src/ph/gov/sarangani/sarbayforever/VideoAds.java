package ph.gov.sarangani.sarbayforever;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class VideoAds extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
		setContentView(R.layout.activity_videoads);
		ImageButton ib1 = (ImageButton) findViewById(R.id.ib1);
		ImageButton ib2 = (ImageButton) findViewById(R.id.ib2);
		ImageButton ib3 = (ImageButton) findViewById(R.id.ib3);
		ImageButton ib4 = (ImageButton) findViewById(R.id.ib4);
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
		ib4.setOnClickListener(this);
		// ab = getActionBar();
		// ab.setTitle("VIDEO ADS");
		// ab.setHomeButtonEnabled(false);
		// ab.setDisplayHomeAsUpEnabled(false);
		// ab.setDisplayShowHomeEnabled(false);
		// ab.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#b5218d")));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib1:
			Intent i1 = new Intent(VideoAds.this, VideoAdsVideo.class);
			i1.setData(Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.sarbayvid1));
			startActivity(i1);
			overridePendingTransition(R.anim.pseudo_flip_in,
					R.anim.pseudo_flip_out);
			break;
		case R.id.ib2:
			Intent i2 = new Intent(VideoAds.this, VideoAdsVideo.class);
			i2.setData(Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.sarbayvid2));
			startActivity(i2);
			overridePendingTransition(R.anim.pseudo_flip_in,
					R.anim.pseudo_flip_out);
			break;
		case R.id.ib3:
			Intent i3 = new Intent(VideoAds.this, VideoAdsVideo.class);
			i3.setData(Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.sarbayvid3));
			startActivity(i3);
			overridePendingTransition(R.anim.pseudo_flip_in,
					R.anim.pseudo_flip_out);
			break;
		case R.id.ib4:
			Intent i4 = new Intent(VideoAds.this, VideoAdsVideo.class);
			i4.setData(Uri.parse("android.resource://" + getPackageName() + "/"
					+ R.raw.sarbayvid4));
			startActivity(i4);
			overridePendingTransition(R.anim.pseudo_flip_in,
					R.anim.pseudo_flip_out);
			break;
		default:
			break;
		}
	}
}
