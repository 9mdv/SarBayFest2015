package ph.gov.sarangani.sarbayforever;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class EnvironmentActivity extends Activity{
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment);
		
		WebView webView = (WebView) findViewById(R.id.webView_Facebook);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.loadUrl("https://www.facebook.com/sarbayfestival?fref=ts");
		finish();
	}

}
