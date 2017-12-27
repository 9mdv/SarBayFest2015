package ph.gov.sarangani.sarbayforever;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		VideoView videoview = (VideoView) findViewById(R.id.videoview);
		Button skip = (Button) findViewById(R.id.btnSkip);
		skip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.pseudo_flip_in,
						R.anim.pseudo_flip_out);
			}
		});

		// Uri uri = Uri.parse("file:///android_asset/sarbayvid2.mp4");
		Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
				+ R.raw.sarbayvid2);

		videoview.setVideoURI(uri);
		videoview.start();

		// videoview.setOnPreparedListener (new OnPreparedListener() {
		// @Override
		// public void onPrepared(MediaPlayer mp) {
		// mp.setLooping(true);
		// }
		// });
		videoview
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						Intent i = new Intent(getApplicationContext(),
								MainActivity.class);
						startActivity(i);
						finish();
						overridePendingTransition(R.anim.pseudo_flip_in,
								R.anim.pseudo_flip_out);
					}
				});

	}
}