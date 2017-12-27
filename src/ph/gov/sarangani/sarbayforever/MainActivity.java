package ph.gov.sarangani.sarbayforever;

import ph.gov.sarangani.sarbayforever.adapters.ViewPager_Adapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends FragmentActivity {
	boolean doubleBackToExitPressedOnce = false;
	private ViewPager _mViewPager;
	private ViewFlipper mViewFlipper;
	private ViewPager_Adapter _adapter;
	private Button _btn1, _btn2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
		setUpView();

		setTab();

	}

	private void setUpView() {
		mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper_Main);
		mViewFlipper.startFlipping();
		_mViewPager = (ViewPager) findViewById(R.id.viewPager);

		_adapter = new ViewPager_Adapter(getSupportFragmentManager());
		_mViewPager.setAdapter(_adapter);
		_mViewPager.setCurrentItem(0);
		initButton();
	}

	private void setTab() {
		_mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int position) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				btnAction(position);
			}

		});

	}

	private void btnAction(int action) {
		switch (action) {
		case 0:
			setButton(_btn1, "", 25, 25);
			setButton(_btn2, "", 15, 15);
			break;

		case 1:
			setButton(_btn2, "", 25, 25);
			setButton(_btn1, "", 15, 15);
			break;
		}
	}

	private void initButton() {
		_btn1 = (Button) findViewById(R.id.btn1);
		_btn2 = (Button) findViewById(R.id.btn2);
		setButton(_btn1, "", 25, 25);
		setButton(_btn2, "", 15, 15);
	}

	private void setButton(Button btn, String text, int h, int w) {
		btn.setWidth(w);
		btn.setHeight(h);
		btn.setText(text);

	}

	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Press again to Exit.", Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}

}
