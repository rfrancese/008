package it.unisa.dodgeholes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class ScoresService extends Service {
	
	/** indicates how to behave if the service is killed */
	   int mStartMode;
	   /** interface for clients that bind */
	   IBinder mBinder;     
	   /** indicates whether onRebind should be used */
	   boolean mAllowRebind;

	   /** Called when the service is being created. */
	   @Override
	   public void onCreate() {
		   /*ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
           NetworkInfo netInfo = cm.getActiveNetworkInfo();*/
		   Log.d("serviceeee", "entro");
		   this.onDestroy();
	     
	   }

	   /** The service is starting, due to a call to startService() */
	   @Override
	   public int onStartCommand(Intent intent, int flags, int startId) {
	      return mStartMode;
	   }

	   /** A client is binding to the service with bindService() */
	   @Override
	   public IBinder onBind(Intent intent) {
	      return mBinder;
	   }

	   /** Called when all clients have unbound with unbindService() */
	   @Override
	   public boolean onUnbind(Intent intent) {
	      return mAllowRebind;
	   }

	   /** Called when a client is binding to the service with bindService()*/
	   @Override
	   public void onRebind(Intent intent) {

	   }

	   /** Called when The service is no longer used and is being destroyed */
	   @Override
	   public void onDestroy() {
		   Log.d("destroyservice", "destroy");
		   super.onDestroy();
		   
	   }

}
