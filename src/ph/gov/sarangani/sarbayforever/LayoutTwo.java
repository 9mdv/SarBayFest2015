package ph.gov.sarangani.sarbayforever;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class LayoutTwo extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.layout_two, container, false);
		initialize(rootView);
		return rootView;
	}

	private void initialize(View view) {

		ImageView music = (ImageView) view.findViewById(R.id.ivMusic_Main);
		music.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isAppInstalled("com.spotify.music")) {

					AlertDialog d = new AlertDialog.Builder(getActivity())
							.create();

					d.setMessage("Listen to the soundtrack of the summer!\nGo to the SARBAYFOREVER playlist on Spotify");

					d.setButton(DialogInterface.BUTTON_POSITIVE,
							"Okay, got it!",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									Intent launchIntent = getActivity()
											.getPackageManager()
											.getLaunchIntentForPackage(
													"com.spotify.music");
									startActivity(launchIntent);
								}
							});
					d.show();

				} else {

					AlertDialog d = new AlertDialog.Builder(getActivity())
							.create();

					d.setMessage("Spotify is not installed on this device\nDownload on Play Store?");

					d.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									try {
										startActivity(new Intent(
												Intent.ACTION_VIEW,
												Uri.parse("market://details?id="
														+ "com.spotify.music")));
									} catch (android.content.ActivityNotFoundException anfe) {
										startActivity(new Intent(
												Intent.ACTION_VIEW,
												Uri.parse("https://play.google.com/store/apps/details?id="
														+ "com.spotify.music")));
									}
								}
							});

					d.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					d.show();

				}
			}
		});
		ImageView video = (ImageView) view.findViewById(R.id.ivVideo_Main);
		video.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), LiveStreamActivity.class);
				startActivity(i);
			}
		});
		ImageView environment = (ImageView) view
				.findViewById(R.id.ivEnvironment_Main);
		environment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(), InfoWebView.class);
				i.setData(Uri.parse("file:///android_asset/sarbay/environment.html"));
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.pseudo_flip_in,
						R.anim.pseudo_flip_out);
			}
		});
		ImageView social = (ImageView) view.findViewById(R.id.ivSocial_Main);
		social.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent i = new Intent(getActivity(),SocialActivity.class);
				// startActivity(i);
				viewDialog();
			}
		});
	}

	private void viewDialog() {

		Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialog = inflater.inflate(R.layout.social_dialog, null);
		builder.setView(dialog);

		ImageView fb = (ImageView) dialog.findViewById(R.id.ivFacebook_Dialog);
		fb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
			}
		});

		ImageView twitter = (ImageView) dialog
				.findViewById(R.id.ivTwitter_Dialog);
		twitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});

		builder.create();
		builder.show();

	}

	private boolean isAppInstalled(String uri) {
		PackageManager pm = getActivity().getPackageManager();
		boolean isAppInstalled = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			isAppInstalled = true;
		} catch (PackageManager.NameNotFoundException e) {
			isAppInstalled = false;
		}
		return isAppInstalled;
	}
}
