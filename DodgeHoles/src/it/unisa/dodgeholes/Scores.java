package it.unisa.dodgeholes;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;


public class Scores extends Activity {
	WebView browser;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.scores);
		browser=(WebView)findViewById(R.id.webkit);
		
		browser.loadUrl("http://www.google.it");
	}
	
	public void onPause()
	{
		super.onPause();
		if(Assets.musicActive)
			Assets.music.pause();
	}
	
	public void onResume()
	{
		super.onResume();
		if(Assets.musicActive)
		{
			Assets.music.play();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{
		    this.finish(); 
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{

			case android.R.id.home:
				this.finish();
			    onBackPressed();
			    return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
