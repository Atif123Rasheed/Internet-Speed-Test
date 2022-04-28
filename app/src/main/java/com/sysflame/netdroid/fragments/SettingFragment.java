package com.sysflame.netdroid.fragments;

import static com.sysflame.netdroid.utils.LogUtils.LOGE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.sysflame.netdroid.R;
import com.sysflame.netdroid.utils.RateDialog;

/**
 * The type Setting fragment.
 */
public class SettingFragment extends Fragment {
	private RadioButton rbMBps;
	private RadioButton rbkBps;
	private RadioButton rbMbps;
	private RadioButton rbkbps;

	private RadioButton scale100;
	private RadioButton scale500;
	private RadioButton scale1000;

	private RadioButton englishLanguage;
	private RadioButton chineseLanguage;
	private RadioButton arabicLanguage;

	private FrameLayout fLAds;


	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {

		View rootView = inflater.inflate (R.layout.fragment_setting, container, false);


	/*	fLAds = rootView.findViewById (R.id.v_ad_unified);
		fLAds.setVisibility (View.GONE);*/

		TextView tvScaleRateUnits = rootView.findViewById(R.id.tv_setting_scale);

		TextView tvLanguage = rootView.findViewById(R.id.tv_setting_language);

		TextView tvDataRateUnits = rootView.findViewById (R.id.tv_data_rate_label);
		tvDataRateUnits.post (() -> {
			int length = tvDataRateUnits.getMeasuredWidth ();
			float angle = 45;
			Shader textShader = new LinearGradient (0, 0, (int) (Math.sin (Math.PI * angle / 180) * length),
					(int) (Math.cos (Math.PI * angle / 180) * length),
					new int[] {0xFFFDFDFD, 0xFFFDFDFD},
					null,
					Shader.TileMode.CLAMP);
			tvDataRateUnits.getPaint ().setShader (textShader);
			tvDataRateUnits.invalidate ();
		});

		TextView tvRateUs = rootView.findViewById (R.id.tv_rate_us_label);
		tvRateUs.post (() -> {
			int length = tvRateUs.getMeasuredWidth ();
			float angle = 45;
			Shader textShader = new LinearGradient (0, 0, (int) (Math.sin (Math.PI * angle / 180) * length),
					(int) (Math.cos (Math.PI * angle / 180) * length),
					new int[] {0xFFFDFDFD, 0xFFFDFDFD},
					null,
					Shader.TileMode.CLAMP);
			tvRateUs.getPaint ().setShader (textShader);
			tvRateUs.invalidate ();
		});

		TextView tvPrivacyPolicy = rootView.findViewById (R.id.tv_privacy_policy_label);
		tvPrivacyPolicy.post (() -> {
			int length = tvPrivacyPolicy.getMeasuredWidth ();
			float angle = 45;
			Shader textShader = new LinearGradient (0, 0, (int) (Math.sin (Math.PI * angle / 180) * length),
					(int) (Math.cos (Math.PI * angle / 180) * length),
					new int[] {0xFFFDFDFD, 0xFFFDFDFD},
					null,
					Shader.TileMode.CLAMP);
			tvPrivacyPolicy.getPaint ().setShader (textShader);
			tvPrivacyPolicy.invalidate ();
		});

		ConstraintLayout constraintLayout = rootView.findViewById (R.id.cl_data_rate_units);
		constraintLayout.setOnClickListener (v -> showAlertDialogButtonClicked ());

		ConstraintLayout constraintLayoutScale = rootView.findViewById(R.id.cl_scale);
		constraintLayoutScale.setOnClickListener(v -> showAlertDialogButtonClickedScale());

		ConstraintLayout constraintLayoutLanguage = rootView.findViewById(R.id.cl_language);
		constraintLayoutLanguage.setOnClickListener(v -> showAlertDialogButtonClickedLanguage());

		ConstraintLayout rateUs = rootView.findViewById (R.id.cl_rate_us);
		rateUs.setOnClickListener (v -> {
			RateDialog rateDialog = new RateDialog (getActivity ());
			rateDialog.displayRatingDialogue ();
		});

		ConstraintLayout cLPrivacyPolicy = rootView.findViewById (R.id.cl_privacy_policy);
		cLPrivacyPolicy.setOnClickListener (v -> {
			Intent browserIntent = new Intent (Intent.ACTION_VIEW, Uri.parse ("https://github.com/Atif123Rasheed"));
			startActivity (browserIntent);
		});
		return rootView;
	}
	private void showAlertDialogButtonClickedLanguage(){
		AlertDialog.Builder builder
				= new AlertDialog.Builder (getContext (), R.style.MyDialogTheme);
		final View customLayoutLanguage = getLayoutInflater().inflate(R.layout.dialog_language,null);
		builder.setView(customLayoutLanguage);

		englishLanguage = customLayoutLanguage.findViewById(R.id.rb_english);
		chineseLanguage = customLayoutLanguage.findViewById(R.id.rb_chinese);
		arabicLanguage = customLayoutLanguage.findViewById(R.id.rb_arabic);


		SharedPreferences sharedPref = getActivity ().getSharedPreferences (
				"setting", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit ();

		switch (sharedPref.getString("LANGUAGE","ENGLISH")){
			case "English":
				englishLanguage.setChecked(true);
				chineseLanguage.setChecked(false);
				arabicLanguage.setChecked(false);

			case "Chinese":
				englishLanguage.setChecked(false);
				chineseLanguage.setChecked(true);
				arabicLanguage.setChecked(false);

			case "Arabic":
				englishLanguage.setChecked(false);
				chineseLanguage.setChecked(false);
				arabicLanguage.setChecked(true);
			default:
				LOGE ("TAG", "ERROR");
				break;
		}
		englishLanguage.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("LANGUAGE");
				editor.putString ("LANGUAGE","English");
				editor.apply ();
				chineseLanguage.setChecked (false);
				arabicLanguage.setChecked (false);
			}
		});
		chineseLanguage.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("LANGUAGE");
				editor.putString ("LANGUAGE","Chinese");
				editor.apply ();
				englishLanguage.setChecked (false);
				arabicLanguage.setChecked (false);
			}
		});
		arabicLanguage.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("SCALE");
				editor.putString ("SCALE","Arabic");
				editor.apply ();
				englishLanguage.setChecked (false);
				chineseLanguage.setChecked (false);
			}
		});
		builder
				.setPositiveButton (
						"OK",
						(dialog, which) -> {
						});
		builder.setNegativeButton ("Cancel",
				(dialog, which) -> {
				});
		AlertDialog dialog
				= builder.create ();
		dialog.show ();
	}

	private void showAlertDialogButtonClickedScale(){
		AlertDialog.Builder builder
				= new AlertDialog.Builder (getContext (), R.style.MyDialogTheme);
		final View customLayoutScale = getLayoutInflater().inflate(R.layout.dialog_scale_units,null);
		builder.setView(customLayoutScale);

		scale100 = customLayoutScale.findViewById(R.id.rb_scale_100);
		scale500 = customLayoutScale.findViewById(R.id.rb_scale_500);
		scale1000 = customLayoutScale.findViewById(R.id.rb_scale_1000);


		SharedPreferences sharedPref = getActivity ().getSharedPreferences (
				"setting", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit ();

		switch (sharedPref.getString("SCALE","Units")){
			case "100":
				scale100.setChecked(true);
				scale500.setChecked(false);
				scale1000.setChecked(false);

			case "500":
				scale100.setChecked(false);
				scale500.setChecked(true);
				scale1000.setChecked(false);

			case "1000":
				scale100.setChecked(false);
				scale500.setChecked(false);
				scale1000.setChecked(true);
			default:
				LOGE ("TAG", "ERROR");
				break;
		}
		scale100.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("SCALE");
				editor.putString ("SCALE","100");
				editor.apply ();
				scale500.setChecked (false);
				scale1000.setChecked (false);
			}
		});
		scale500.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("SCALE");
				editor.putString ("SCALE","500");
				editor.apply ();
				scale100.setChecked (false);
				scale1000.setChecked (false);
			}
		});
		scale1000.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("SCALE");
				editor.putString ("SCALE","1000");
				editor.apply ();
				scale100.setChecked (false);
				scale500.setChecked (false);
			}
		});
		builder
				.setPositiveButton (
						"OK",
						(dialog, which) -> {
						});
		builder.setNegativeButton ("Cancel",
				(dialog, which) -> {
				});
		AlertDialog dialog
				= builder.create ();
		dialog.show ();
	}


	private void showAlertDialogButtonClicked () {
		AlertDialog.Builder builder
				= new AlertDialog.Builder (getContext (), R.style.MyDialogTheme);
		final View customLayout
				= getLayoutInflater ()
				.inflate (R.layout.dialog_datarate_units, null);
		builder.setView (customLayout);

		rbMBps = customLayout.findViewById (R.id.rb_MBps);
		rbkBps = customLayout.findViewById (R.id.rb_kBps);
		rbMbps = customLayout.findViewById (R.id.rb_Mbps);
		rbkbps = customLayout.findViewById (R.id.rb_kbps);

		SharedPreferences sharedPref = getActivity ().getSharedPreferences (
				"setting", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit ();

		switch (sharedPref.getString ("UNIT", "Mbps")) {
			case "MBps":
				rbMBps.setChecked (true);
				rbkBps.setChecked (false);
				rbMbps.setChecked (false);
				rbkbps.setChecked (false);
				break;
			case "kBps":
				rbMBps.setChecked (false);
				rbkBps.setChecked (true);
				rbMbps.setChecked (false);
				rbkbps.setChecked (false);
				break;
			case "Mbps":
				rbMBps.setChecked (false);
				rbkBps.setChecked (false);
				rbMbps.setChecked (true);
				rbkbps.setChecked (false);
				break;
			case "kbps":
				rbMBps.setChecked (false);
				rbkBps.setChecked (false);
				rbMbps.setChecked (false);
				rbkbps.setChecked (true);
				break;
			default:
				LOGE ("TAG", "ERROR");
				break;
		}
		rbMBps.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("UNIT");
				editor.putString ("UNIT", "MBps");
				editor.apply ();
				rbkBps.setChecked (false);
				rbMbps.setChecked (false);
				rbkbps.setChecked (false);
			}
		});
		rbkBps.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("UNIT");
				editor.putString ("UNIT", "kBps");
				editor.apply ();
				rbMBps.setChecked (false);
				rbMbps.setChecked (false);
				rbkbps.setChecked (false);
			}
		});
		rbMbps.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("UNIT");
				editor.putString ("UNIT", "Mbps");
				editor.apply ();
				rbkBps.setChecked (false);
				rbMBps.setChecked (false);
				rbkbps.setChecked (false);
			}
		});
		rbkbps.setOnCheckedChangeListener ((buttonView, isChecked) -> {
			if (isChecked) {
				editor.remove ("UNIT");
				editor.putString ("UNIT", "kbps");
				editor.apply ();
				rbkBps.setChecked (false);
				rbMbps.setChecked (false);
				rbMBps.setChecked (false);
			}
		});
		builder
				.setPositiveButton (
						"OK",
						(dialog, which) -> {
						});
		builder.setNegativeButton ("Cancel",
				(dialog, which) -> {
				});
		AlertDialog dialog
				= builder.create ();
		dialog.show ();
	}

	@Override
	public void onPause () {
		super.onPause ();
	}

	@Override
	public void onResume () {
		super.onResume ();
	}

	@Override
	public void onDestroy () {
		super.onDestroy ();
	}

	@Override
	public void onStart () {
		super.onStart ();
	}

	@Override
	public void onStop () {
		super.onStop ();
	}
}
