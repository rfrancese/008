package it.unisa.dodgeholes;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;

import android.os.Bundle;

import android.widget.RelativeLayout;

import com.adsdk.sdk.Ad;
import com.adsdk.sdk.AdListener;
import com.adsdk.sdk.AdManager;
import com.adsdk.sdk.Gender;
import com.adsdk.sdk.banner.AdView;



public class Advertise extends Activity implements AdListener {
	private RelativeLayout layout;
	private AdView mAdView;
	private AdManager mManager;
	
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advertise);
		layout = (RelativeLayout) findViewById(R.id.adsdkContent);
		mManager = new AdManager(this,"http://my.mobfox.com/request.php",
				"64a7a0c22dcc6535379daf17bebea662", true);
		
		mManager.setInterstitialAdsEnabled(true); //enabled by default. Allows the SDK to request static interstitial ads.
		mManager.setVideoAdsEnabled(true); //disabled by default. Allows the SDK to request video fullscreen ads.
		mManager.setPrioritizeVideoAds(true); //disabled by default. If enabled, indicates that SDK should request video ads first, and only if there is no video request a static interstitial (if they are enabled).
		
		/*ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("cars");
		keywords.add("money");
		mManager.setKeywords(keywords); //optional, to send list of keywords (user interests) to ad server.
		mManager.setUserAge(37); //optional, sends user's age
		mManager.setUserGender(Gender.FEMALE); //optional, sends user's gender
		*/
		mManager.setListener(this);
		mManager.requestAd();
		
		progressDialog = ProgressDialog.show(Advertise.this, "", "Loading...");
	}



	@Override
	public void adClicked() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void adClosed(Ad arg0, boolean arg1) {
		progressDialog.dismiss();
		this.finish();
		
	}



	@Override
	public void adLoadSucceeded(Ad arg0) {
		// TODO Auto-generated method stub
		if (mManager != null && mManager.isAdLoaded())
			mManager.showAd();
		
	}



	@Override
	public void adShown(Ad arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void noAdFound() {
		// TODO Auto-generated method stub
		progressDialog.dismiss();
		this.finish();
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mManager.release();
		if(mAdView!=null)
			mAdView.release();
	}




	
}
