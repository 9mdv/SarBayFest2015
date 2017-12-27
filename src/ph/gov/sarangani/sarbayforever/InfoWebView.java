package ph.gov.sarangani.sarbayforever;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class InfoWebView extends Activity {
	WebView wv;
	ProgressBar pb;

	@Override
	public void onBackPressed() {
		if (wv.canGoBack()) {
			wv.goBack();
		} else {
			super.onBackPressed();
			overridePendingTransition(R.anim.pseudo_flip_in,
					R.anim.pseudo_flip_out);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
		setContentView(R.layout.web_view);

		Intent intent = getIntent();
		String url = intent.getDataString();
		pb = (ProgressBar) findViewById(R.id.pbBulletin);
		wv = (WebView) findViewById(R.id.wvBulletin);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setDisplayZoomControls(false);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl(url);
		wv.setWebViewClient(new WebViewClient());
		wv.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				pb.setProgress(newProgress);
				if (newProgress == 100) {
					pb.setVisibility(View.GONE);

				} else {
					pb.setVisibility(View.VISIBLE);
				}
			}
		});
	}
}
