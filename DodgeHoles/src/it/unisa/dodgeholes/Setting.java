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
	private DbLocale database=null;
	
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.setting);
		database=new DbLocale(getApplicationContext());
		
		bM = (ToggleButton) findViewById(R.id.buttMsc);
		
		bM.setChecked(Assets.getAudio(database));
		/*
		if(Assets.musicActive && !bM.isChecked())
			bM.setChecked(true);
		*/
		 bM.setOnClickListener(new OnClickListener()
		 {
		 public void onClick(View v)
		 {
			 
			 boolean checked = ((ToggleButton) v).isChecked();
			 
			 
			 if(checked)
			 {
				 Assets.music.play();
				 //scrivo sul database e modifico Assets.musicActive
				 Assets.setAudio(1, database);
				 Assets.musicActive=Assets.getAudio(database);
				 
			 }
			 else
			 {
				 Assets.music.stop();
				 //Scrivo sul database e modifico il valore di Assets.musicActive
				 Assets.setAudio(0, database);
				 Assets.musicActive=Assets.getAudio(database); 
			 }
		 }
		                                 });
		 bS = (ToggleButton) findViewById(R.id.buttSnd);
		 
		 bS.setChecked(Assets.getSound(database));
		 /*
		if(Assets.soundActive && !bS.isChecked())
			bS.setChecked(true);
		 */
		 bS.setOnClickListener(new OnClickListener()
		 {
		 public void onClick(View v)
		 {
			 
			 boolean checked = ((ToggleButton) v).isChecked();
			 
			 
			 if(checked)
			 {
				 Assets.setSound(1, database);
				 Assets.soundActive=Assets.getSound(database);
				 
			 }
			 else
			 {
				 Assets.setSound(0, database);
				 Assets.soundActive=Assets.getSound(database);
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


