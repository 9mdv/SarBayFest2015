package ph.gov.sarangani.sarbayforever;

import ph.gov.sarangani.sarbayforever.adapters.TabsPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class InfoCOA extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar ab;
	// Tab titles
	private String[] tabs = { "DAY 1", "DAY 2" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
		setContentView(R.layout.activity_info_coa);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.infoCOA);
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		ab = getActionBar();
		// ab.setDisplayShowCustomEnabled(true);
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		ab.setHomeButtonEnabled(false);
//		ab.setTitle("CALENDAR OF ACTIVITIES");
		ab.setDisplayShowTitleEnabled(false);
//		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
//		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b5218d")));
		ab.setStackedBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#b5218d")));
		// ab.setCustomView(R.layout.actionbar_centered);

		// Adding Tabs
		for (String tab_name : tabs) {
			ab.addTab(ab.newTab().setText(tab_name).setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				ab.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
	}

}
