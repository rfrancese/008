package it.unisa.dodgeholes;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;


public class UserFunctions {

    private JSONParser jsonParser;

    private static String registerURL = "http://www.depiano.it/DodgeHoles/";

    private static String register_tag = "register";
    private static String insert_tag = "inserisci_punteggio";
   
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

 

     /**
      * Function to  Register
      **/
    public JSONObject registerUser(String nickname){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("nickname",nickname));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
        return json;
    }



	public JSONObject salvoPunteggio(String nickname, String livello,int punteggio)
	{
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("tag",insert_tag ));
	        params.add(new BasicNameValuePair("nickname",nickname));

	        params.add(new BasicNameValuePair("livello",livello));

	        params.add(new BasicNameValuePair("punteggio",""+punteggio));
	        
	        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
	        System.out.println("Valore oggetto json:"+json);
	        return json;
	}

}

