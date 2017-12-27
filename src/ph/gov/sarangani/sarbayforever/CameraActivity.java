package ph.gov.sarangani.sarbayforever;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {

	int status = 200;
	final int TAKE_PHOTO = 101;
	final int SELECT_PHOTO = 102;
	public Uri imageUri, ivGalleryName;
	String ivCamName;
	private ActionBar ab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
		setContentView(R.layout.activity_camera);
		ab = getActionBar();
		ab.setTitle("Share photo on:");
		ab.setHomeButtonEnabled(false);
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayShowHomeEnabled(false);
		// ab.setDisplayShowCustomEnabled(true);
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b5218d")));

		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/Pictures/SarBayFest");
		if (!folder.exists()) {
			folder.mkdir();
		}

		Button takePhoto = (Button) findViewById(R.id.btnTakePhoto);
		Button fromGallery = (Button) findViewById(R.id.btnFromGallery);

		takePhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				takePhoto();
			}
		});
		fromGallery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectPhoto();
			}
		});

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.pseudo_flip_in, R.anim.pseudo_flip_out);
	}

	public void takePhoto() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				java.util.Locale.getDefault()).format(new Date());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String name = "SarBayFest_" + timeStamp + ".jpg";

		File photo = new File(Environment.getExternalStorageDirectory()
				+ "/Pictures/SarBayFest", name);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		imageUri = Uri.fromFile(photo);
		startActivityForResult(intent, TAKE_PHOTO);

		ivCamName = name;
	}

	public void selectPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Photo"),
				SELECT_PHOTO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				Uri selectedImage = imageUri;
				getContentResolver().notifyChange(selectedImage, null);
				ImageView imageView = (ImageView) findViewById(R.id.ivPhoto);
				ContentResolver cr = getContentResolver();
				Bitmap bitmap;
				try {
					bitmap = android.provider.MediaStore.Images.Media
							.getBitmap(cr, selectedImage);

					imageView.setImageBitmap(bitmap);
					status = 201;
					Toast.makeText(this, "Image saved in /Pictures/SarBayFest",
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
							.show();
					Log.e("SarBayCam", e.toString());
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
			}
			break;
		case SELECT_PHOTO:
			if (resultCode == Activity.RESULT_OK) {
				if (data != null && data.getData() != null) {

					Uri selectedImage = data.getData();
					getContentResolver().notifyChange(selectedImage, null);
					ImageView imageView = (ImageView) findViewById(R.id.ivPhoto);
					ContentResolver cr = getContentResolver();
					Bitmap bitmap;
					try {
						bitmap = android.provider.MediaStore.Images.Media
								.getBitmap(cr, selectedImage);

						imageView.setImageBitmap(bitmap);
					} catch (Exception e) {
						Toast.makeText(this, "Failed to load",
								Toast.LENGTH_SHORT).show();
						Log.e("SarBayCam", e.toString());
					}
					ivGalleryName = selectedImage;
					status = 202;
					Log.i("SarBayCam", ivGalleryName.toString());
					super.onActivityResult(requestCode, resultCode, data);
				} else if (resultCode == Activity.RESULT_CANCELED) {

				}

			}
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.camera, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		switch (id) {
		case R.id.share_facebook:
			if (isAppInstalled("com.facebook.katana")) {

				if (status == 201) {

					AlertDialog d = new AlertDialog.Builder(this).create();

					d.setMessage("Let us trend SarBay Festival!\nUse our official hashtag #sarbayforever");

					d.setButton(DialogInterface.BUTTON_POSITIVE,
							"Okay, got it!",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String type = "image/*";
									String filename = ivCamName;
									String mediaPath = Environment
											.getExternalStorageDirectory()
											+ "/Pictures/SarBayFest/"
											+ filename;
									String hashtag = "#sarbayforever ";

									Intent share = new Intent(
											Intent.ACTION_SEND);

									share.setType(type);

									File media = new File(mediaPath);
									Uri uri = Uri.fromFile(media);

									share.putExtra(Intent.EXTRA_STREAM, uri);
									share.putExtra(Intent.EXTRA_TEXT, hashtag);

									share.setPackage("com.facebook.katana");
									startActivity(share);
								}
							});
					d.show();

				} else if (status == 202) {

					AlertDialog d = new AlertDialog.Builder(this).create();

					d.setMessage("Let us trend SarBay Festival!\nUse our official hashtag #sarbayforever");

					d.setButton(DialogInterface.BUTTON_POSITIVE,
							"Okay, got it!",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String type = "image/*";
									String hashtag = "#sarbayforever ";

									Intent share = new Intent(
											Intent.ACTION_SEND);

									share.setType(type);

									Uri uri = ivGalleryName;

									share.putExtra(Intent.EXTRA_STREAM, uri);
									share.putExtra(Intent.EXTRA_TEXT, hashtag);

									share.setPackage("com.facebook.katana");
									startActivity(share);
								}
							});
					d.show();

				} else if (status == 200) {
					Toast.makeText(CameraActivity.this,
							"Take a photo to share or select from gallery",
							Toast.LENGTH_LONG).show();
				}

				break;
			} else {

				AlertDialog d = new AlertDialog.Builder(this).create();

				d.setMessage("Facebook is not installed.\nDownload on Play Store?");

				d.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("market://details?id="
													+ "com.facebook.katana")));
								} catch (android.content.ActivityNotFoundException anfe) {
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("https://play.google.com/store/apps/details?id="
													+ "com.facebook.katana")));
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

				break;
			}

		case R.id.share_twitter:
			if (isAppInstalled("com.twitter.android")) {

				if (status == 201) {
					String type = "image/*";
					String filename = ivCamName;
					String mediaPath = Environment
							.getExternalStorageDirectory()
							+ "/Pictures/SarBayFest/" + filename;
					String hashtag = "#sarbayforever ";

					Intent share = new Intent(Intent.ACTION_SEND);

					share.setType(type);

					File media = new File(mediaPath);
					Uri uri = Uri.fromFile(media);

					share.putExtra(Intent.EXTRA_STREAM, uri);
					share.putExtra(Intent.EXTRA_TEXT, hashtag);

					share.setPackage("com.twitter.android");
					startActivity(share);

				} else if (status == 202) {

					String type = "image/*";
					String hashtag = "#sarbayforever ";

					Intent share = new Intent(Intent.ACTION_SEND);

					share.setType(type);

					Uri uri = ivGalleryName;

					share.putExtra(Intent.EXTRA_STREAM, uri);
					share.putExtra(Intent.EXTRA_TEXT, hashtag);

					share.setPackage("com.twitter.android");
					startActivity(share);

				} else if (status == 200) {
					Toast.makeText(CameraActivity.this,
							"Take a photo to share or select from gallery",
							Toast.LENGTH_LONG).show();
				}

				break;
			} else {

				AlertDialog d = new AlertDialog.Builder(this).create();

				d.setMessage("Twitter is not installed.\nDownload on Play Store?");

				d.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("market://details?id="
													+ "com.twitter.android")));
								} catch (android.content.ActivityNotFoundException anfe) {
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("https://play.google.com/store/apps/details?id="
													+ "com.twitter.android")));
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

				break;
			}

		case R.id.share_instagram:
			if (isAppInstalled("com.instagram.android")) {
				if (status == 201) {
					String type = "image/*";
					String filename = ivCamName;
					String mediaPath = Environment
							.getExternalStorageDirectory()
							+ "/Pictures/SarBayFest/" + filename;
					String hashtag = "#sarbayforever ";

					Intent share = new Intent(Intent.ACTION_SEND);

					share.setType(type);

					File media = new File(mediaPath);
					Uri uri = Uri.fromFile(media);

					share.putExtra(Intent.EXTRA_STREAM, uri);
					share.putExtra(Intent.EXTRA_TEXT, hashtag);

					share.setPackage("com.instagram.android");
					startActivity(share);

				} else if (status == 202) {

					String type = "image/*";
					String hashtag = "#sarbayforever ";

					Intent share = new Intent(Intent.ACTION_SEND);

					share.setType(type);

					Uri uri = ivGalleryName;

					share.putExtra(Intent.EXTRA_STREAM, uri);
					share.putExtra(Intent.EXTRA_TEXT, hashtag);

					share.setPackage("com.instagram.android");
					startActivity(share);

				} else if (status == 200) {
					Toast.makeText(CameraActivity.this,
							"Take a photo to share or select from gallery",
							Toast.LENGTH_LONG).show();
				}

				break;
			} else {

				AlertDialog d = new AlertDialog.Builder(this).create();

				d.setMessage("Instagram is not installed.\nDownload on Play Store?");

				d.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								try {
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("market://details?id="
													+ "com.instagram.android")));
								} catch (android.content.ActivityNotFoundException anfe) {
									startActivity(new Intent(
											Intent.ACTION_VIEW,
											Uri.parse("https://play.google.com/store/apps/details?id="
													+ "com.instagram.android")));
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

				break;
			}

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean isAppInstalled(String uri) {
		PackageManager pm = getPackageManager();
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