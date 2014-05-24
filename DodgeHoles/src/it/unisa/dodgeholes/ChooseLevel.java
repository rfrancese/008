package it.unisa.dodgeholes;



import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import it.unisa.dodgeholes.framework.Game;

public class ChooseLevel extends Activity {
	
	
	
	private static final String[] items={"Level 1", "Level 2","Level 3","Level 4","Level 5","Level 6"};
	private static final Integer[] imageId = {
		      R.drawable.liv1,
		      R.drawable.liv2,
		      R.drawable.liv3,
		      R.drawable.liv4,
		      R.drawable.liv5,
		      R.drawable.liv6
		  
		  };
	private SingletonParametersBridge bridge;
	private Game game;
	private ListView list;
	
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		bridge =SingletonParametersBridge.getInstance();
		game = (Game) bridge.getParameter("game");
		bridge.removeParameter("game");
		
		setContentView(R.layout.choose_lev);
	
		
		//setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items));
		//setListAdapter(new ArrayAdapter<String>(this,R.id.list_liv,items));
		list=(ListView)findViewById(R.id.list_liv);
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,items,imageId,"font.ttf");
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
                {
                	game.setScreen(new GameScreen(game,position+1));
                	
                	Intent myIntent = new Intent(getApplication(), Advertise.class);
                	startActivity(myIntent);
                	
            		finish();
                }
            });
		
		
	}
	
	/*public void onListItemClick(ListView parent, View v, int position,
																long id) {

		game.setScreen(new GameScreen(game,position+1));
		finish();
	}*/
	
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
			//Log.d("back", "entro !");
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
