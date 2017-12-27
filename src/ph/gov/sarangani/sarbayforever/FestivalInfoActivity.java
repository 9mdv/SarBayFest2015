package ph.gov.sarangani.sarbayforever;

import java.util.ArrayList;

import ph.gov.sarangani.sarbayforever.adapters.Channel;
import ph.gov.sarangani.sarbayforever.adapters.InfoList_Adapter;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FestivalInfoActivity extends Activity {

	InfoList_Adapter adapter;
	ListView lv;
	ArrayList<Channel> list = new ArrayList<Channel>();
	String[] info_labels;
	TypedArray info_icons;
	private ActionBar ab;

	String[] info = new String[] { "History", "Calendar of Activities",
			"How to get there", "Where to stay", "Hotlines",
			"Festival Bulletin", "Survival Guide", "Videos" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
		setContentView(R.layout.activity_festivalinfo);
		ab = getActionBar();
		ab.setTitle("FESTIVAL INFO");
		ab.setHomeButtonEnabled(false);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b5218d")));

		info_labels = getResources().getStringArray(
				R.array.festival_info_labels);
		info_icons = getResources().obtainTypedArray(
				R.array.festival_info_icons);

		lv = (ListView) findViewById(R.id.lvFestivalInfo);
		for (int i = 0; i < info_labels.length; i++) {
			Channel c = new Channel(info_labels[i], info_icons.getResourceId(i,
					-1));
			list.add(c);
		}
		adapter = new InfoList_Adapter(FestivalInfoActivity.this, list);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					Intent iH = new Intent(FestivalInfoActivity.this,
							InfoWebView.class);
					iH.setData(Uri
							.parse("file:///android_asset/sarbay/history.html"));
					startActivity(iH);
					overridePendingTransition(R.anim.pseudo_flip_in,
							R.anim.pseudo_flip_out);
					break;
				case 1:
					Intent iCOA = new Intent(FestivalInfoActivity.this,
							InfoCOA.class);
					startActivity(iCOA);
					overridePendingTransition(R.anim.pseudo_flip_in,
							R.anim.pseudo_flip_out);
					break;
				case 2:
					Intent iHTGT = new Intent(FestivalInfoActivity.this,
							InfoWebView.class);
					iHTGT.setData(Uri
							.parse("file:///android_asset/sarbay/howtogetthere.html"));
					startActivity(iHTGT);
					overridePendingTransition(R.anim.pseudo_flip_in,
							R.anim.pseudo_flip_out);
					break;
				case 3:
					Intent iWTS = new Intent(FestivalInfoActivity.this,
							InfoWebView.class);
					iWTS.setData(Uri
							.parse("file:///android_asset/where_to_stay.jpg"));
					startActivity(iWTS);
					overridePendingTransition(R.anim.pseudo_flip_in,
							R.anim.pseudo_flip_out);
					break;
				case 4:
					break;
				case 5:
					Intent iB = new Intent(FestivalInfoActivity.this,
							BulletinActivity.class);
					startActivity(iB);
					overridePendingTransition(R.anim.pseudo_flip_in,
							R.anim.pseudo_flip_out);
					break;
				case 6:
					Intent iSG = new Intent(FestivalInfoActivity.this,
							InfoWebView.class);
					iSG.setData(Uri
							.parse("file:///android_asset/sarbay/survivaltips.html"));
					startActivity(iSG);
					overridePendingTransition(R.anim.pseudo_flip_in,
							R.anim.pseudo_flip_out);
					break;
				case 7:
					Intent iV = new Intent(FestivalInfoActivity.this,
							VideoAds.class);
					startActivity(iV);
					overridePendingTransition(R.anim.pseudo_flip_in,
							R.anim.pseudo_flip_out);
					break;

				}

			}

		});

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
	}

}
