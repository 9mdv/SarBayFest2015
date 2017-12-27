package ph.gov.sarangani.sarbayforever;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class LiveStreamActivity extends Activity {

	ListView lv;
	ActionBar ab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
		setContentView(R.layout.activity_livestream);
		ab = getActionBar();
		ab.setTitle("LIVE STREAM");
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b5218d")));

		lv = (ListView) findViewById(R.id.listView_Video);
		String[] areas = new String[] { "Area 1", "Area 2", "Area 3", "Area 4",
				"Area 5" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.tv_livestream, areas);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				switch (position) {
				case 0:
					Toast.makeText(getApplicationContext(), "Area 1",
							Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), "Area 2",
							Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(getApplicationContext(), "Area 3",
							Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(getApplicationContext(), "Area 4",
							Toast.LENGTH_SHORT).show();
					break;
				case 4:
					Toast.makeText(getApplicationContext(), "Area 5",
							Toast.LENGTH_SHORT).show();
					break;
				default:
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
