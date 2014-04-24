package it.unisa.dodgeholes;



import android.app.ListActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import it.unisa.dodgeholes.framework.Game;

public class ChooseLevel extends ListActivity {
	
	
	
	private TextView selection;
	private static final String[] items={"Level 1", "Level 2","Level 3","Level 4","Level 5","Level 6"};
	private SingletonParametersBridge bridge;
	private Game game;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		bridge =SingletonParametersBridge.getInstance();
		game = (Game) bridge.getParameter("game");
		bridge.removeParameter("game");
		
		setContentView(R.layout.choose_lev);
		setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));
		//selection=(TextView)findViewById(R.id.selection);
	}
	
	public void onListItemClick(ListView parent, View v, int position,
																long id) {
	 	//selection.setText(items[position]);
		game.setScreen(new GameScreen(game,position+1));
		finish();
	}
	
	public void onPause()
	{
		super.onPause();
		if(Assets.musicActive)
			Assets.music.stop();
	}
	
	public void onResume()
	{
		super.onResume();
		if(Assets.musicActive)
		{
			Assets.music.play();
		}
	}
	
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
