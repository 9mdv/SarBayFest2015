package ph.gov.sarangani.sarbayforever;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapActivity extends FragmentActivity implements
		OnItemSelectedListener {
	private class DownloadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {

			String data = "";

			try {
				data = MapActivity.this.downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			parserTask.execute(result);

		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {
			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			String distance = "";
			String duration = "";

			if (result.size() < 1) {
				Toast.makeText(MapActivity.this.getBaseContext(), "No Points",
						Toast.LENGTH_SHORT).show();
				return;
			}

			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				List<HashMap<String, String>> path = result.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					if (j == 0) {
						distance = point.get("distance");
						continue;
					} else if (j == 1) {
						duration = point.get("duration");
						continue;
					}
					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);
					points.add(position);
				}

				lineOptions.addAll(points);
				lineOptions.width(10);
				lineOptions.color(Color.GREEN);
			}

			MapActivity.this.map.addPolyline(lineOptions);

			new AlertDialog.Builder(MapActivity.this)
					.setMessage(
							"Distance: " + distance.replace("km", "kilometers")
									+ "\nDriving time: "
									+ duration.replace("mins", "minutes"))
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).show();

		}
	}

	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	GoogleMap map;
	static final LatLng SARBAYFEST = new LatLng(5.789919799762351,
			125.18942121416332);
	GroundOverlay go;
	boolean govVisible = false;
	ArrayList<LatLng> markerPoints;

	TextView tvDistanceDuration;

	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.connect();

			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	private String getDirectionsUrl(LatLng origin, LatLng dest) {
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		String sensor = "sensor=false";

		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		String output = "json";

		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vb.vibrate(30);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#bfb5218d")));
		actionBar.setIcon(R.drawable.ic_map);

		Spinner s = new Spinner(this);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.map_type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(MapActivity.this);
		actionBar.setCustomView(s, new ActionBar.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		actionBar.setDisplayShowCustomEnabled(true);
		if (servicesOK()) {
			Toast.makeText(this, "Tap two location to get directions.",
					Toast.LENGTH_LONG).show();

			setContentView(R.layout.activity_map);
			overridePendingTransition(R.anim.pseudo_flip_in,
					R.anim.pseudo_flip_out);

			map = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			map.setPadding(0, 80, 0, 0);

			Marker sbfMarker = map.addMarker(new MarkerOptions()
					.position(SARBAYFEST)
					.title("SarBay Fest")/*
					.snippet("Population: 300,000")*/
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

			map.setMyLocationEnabled(true);
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(SARBAYFEST).bearing(225).tilt(75).zoom(16).build();

			map.animateCamera(
					CameraUpdateFactory.newCameraPosition(cameraPosition),
					6000, null);

			sbfMarker.showInfoWindow();
			this.markerPoints = new ArrayList<LatLng>();
			markerPoints.add(SARBAYFEST);

			this.map.setOnMapClickListener(new OnMapClickListener() {
				@Override
				public void onMapClick(LatLng point) {

					MapActivity.this.markerPoints.add(point);

					MarkerOptions options = new MarkerOptions();

					options.position(point);

					if (MapActivity.this.markerPoints.size() == 2) {
						options.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_RED));

						LatLng origin = MapActivity.this.markerPoints.get(1);
						LatLng dest = MapActivity.this.markerPoints.get(0);

						String url = MapActivity.this.getDirectionsUrl(origin,
								dest);

						DownloadTask downloadTask = new DownloadTask();

						downloadTask.execute(url);

					} else if (MapActivity.this.markerPoints.size() > 2) {
						markerPoints.clear();
						map.clear();
						MapActivity.this.markerPoints.add(point);
					}

					map.addMarker(options);

				}
			});
		} else if (!isNetworkAvailable()) {
			new AlertDialog.Builder(MapActivity.this)
					.setMessage("No Connection")
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
								}
							}).show();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			map.setMapType(MAP_TYPE_HYBRID);
			break;
		case 1:
			map.setMapType(MAP_TYPE_NORMAL);
			break;
		case 2:
			map.setMapType(MAP_TYPE_SATELLITE);
			break;
		case 3:
			map.setMapType(MAP_TYPE_TERRAIN);
			break;

		default:
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.clear_markers) {
			if (markerPoints.size() > 0) {
				markerPoints.clear();
				map.clear();
				Toast.makeText(this, "Markers Cleared", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(this, "No Markers", Toast.LENGTH_SHORT).show();
			}
		} else if (item.getItemId() == R.id.show_sarbayfest) {

			if (markerPoints.size() <= 1) {
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(SARBAYFEST).bearing(225).tilt(75).zoom(16)
						.build();
				map.animateCamera(
						CameraUpdateFactory.newCameraPosition(cameraPosition),
						3500, null);
				Marker sbfMarker = map
						.addMarker(new MarkerOptions()
								.position(SARBAYFEST)
								.title("SarBay Fest")/*
								.snippet("Population: 300,000")*/
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
				sbfMarker.showInfoWindow();
				markerPoints.add(SARBAYFEST);
			} else if (markerPoints.size() == 2) {
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(SARBAYFEST).bearing(225).tilt(75).zoom(16)
						.build();
				map.animateCamera(
						CameraUpdateFactory.newCameraPosition(cameraPosition),
						2000, null);
				markerPoints.add(SARBAYFEST);
				Marker cityhall = map
						.addMarker(new MarkerOptions()
								.position(SARBAYFEST)
								.title("SarBay Fest")
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
				cityhall.showInfoWindow();
				LatLng origin = markerPoints.get(0);
				LatLng dest = markerPoints.get(1);

				String url = getDirectionsUrl(origin, dest);

				DownloadTask downloadTask = new DownloadTask();

				downloadTask.execute(url);

			} else if (markerPoints.size() > 2) {
				markerPoints.clear();
				map.clear();
			}

		} else if (item.getItemId() == R.id.show_poi) {

			GroundOverlayOptions sarbayOverlay = new GroundOverlayOptions()
					.image(BitmapDescriptorFactory
							.fromResource(R.drawable.sarbayfestmap))
					.bearing(240).anchor(0.5f, 0.5f)
					.position(SARBAYFEST, 700, 700);
			if (govVisible == false) {

				go = map.addGroundOverlay(sarbayOverlay);
				govVisible = true;
			} else if (govVisible == true) {
				go.remove();
				govVisible = false;
			}

		}
		return true;
	}

	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		} else {
			Toast.makeText(this, "Can't connect to Google Play services",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}
}