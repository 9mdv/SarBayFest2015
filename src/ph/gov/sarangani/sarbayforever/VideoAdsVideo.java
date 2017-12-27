package ph.gov.sarangani.sarbayforever;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoAdsVideo extends Activity {
//	MediaController mc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_ads_video);

		VideoView videoview = (VideoView) findViewById(R.id.vvVideoAds);
		Intent intent = getIntent();
		String uri = intent.getDataString();
		videoview.setVideoURI(Uri.parse(uri));
		videoview.start();
//		mc.show();

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
//						Intent i = new Intent(getApplicationContext(),
//								MainActivity.class);
//						startActivity(i);
						finish();
						overridePendingTransition(R.anim.pseudo_flip_in,
								R.anim.pseudo_flip_out);
					}
				});

	}
}