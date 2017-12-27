package ph.gov.sarangani.sarbayforever;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class LayoutOne extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.layout_one, container,false);
		initialize(rootView);
		
		return rootView;
	}
	
	private void initialize(View view) {

		ImageView chat = (ImageView) view.findViewById(R.id.ivChat_Main);
		chat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(),
						SignInActivity.class);
				startActivity(i);
			}
		});
		ImageView cam = (ImageView) view.findViewById(R.id.ivCam_Main);
		cam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(),
						CameraActivity.class);
				startActivity(i);
			}
		});
		ImageView info = (ImageView) view.findViewById(R.id.ivInfo_Main);
		info.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(),
						FestivalInfoActivity.class);
				startActivity(i);
			}
		});
		ImageView map = (ImageView) view.findViewById(R.id.ivMap_Main);
		map.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(),
						MapActivity.class);
				startActivity(i);
			}
		});
			}
}
