package it.unisa.dodgeholes;




import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Setting extends Activity {
	
	private ToggleButton bM,bS;
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.setting);
		
		bM = (ToggleButton) findViewById(R.id.buttMsc);
		
		//bM.setChecked(Assets.musicActive);
		if(Assets.musicActive && !bM.isChecked())
			bM.setChecked(true);
		
		 bM.setOnClickListener(new OnClickListener()
		 {
		 public void onClick(View v)
		 {
			 
			 boolean checked = ((ToggleButton) v).isChecked();
			 
			 
			 if(checked)
			 {
				 Assets.music.play();
				 //scrivo sul database e modifico Assets.musicActive
				 Assets.musicActive=true;
			 }
			 else
			 {
				 Assets.music.stop();
				 //Scrivo sul database e modifico il valore di Assets.musicActive
				 Assets.musicActive=false; 
			 }
		 }
		                                 });
		 bS = (ToggleButton) findViewById(R.id.buttSnd);
		if(Assets.soundActive && !bS.isChecked())
			bS.setChecked(true);
		 
		 bS.setOnClickListener(new OnClickListener()
		 {
		 public void onClick(View v)
		 {
			 
			 boolean checked = ((ToggleButton) v).isChecked();
			 
			 
			 if(checked)
			 {
				 Assets.soundActive=true;
			 }
			 else
			 {
				 Assets.soundActive=false; 
			 }
		 }
		                                 });
		 
	}
	
	public void onPause()
	{
		super.onPause();
		if(bM.isChecked())
			Assets.music.stop();
		if(Assets.musicActive)
		{
			//
		}
	}
	
	public void onResume()
	{
		super.onResume();
		if(bM.isChecked())
			Assets.music.play();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK))
		{
			//Log.d("back", "entro !");
		    this.finish(); 
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	  
}


