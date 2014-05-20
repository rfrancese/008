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

    private static String registerURL = "http://www.dodgeholes.com/DodgeHoles/";

    private static String register_tag = "register";
    private static String insert_tag = "inserisci_punteggio";
    private static String imei_tag = "controlla_imei";
    private static String aggiorna_tag="carica_tutto";
    
   
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

 

     /**
      * Function to  Register
      **/
    public JSONObject registerUser(String nickname,String imei){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("nickname",nickname));
        params.add(new BasicNameValuePair("imei",imei));
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
	        return json;
	}



	public JSONObject controllaIMEI(String deviceIMEI) 
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag",imei_tag ));
        params.add(new BasicNameValuePair("imei",deviceIMEI));

        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
        System.out.println("Valore oggetto json:"+json);
        return json;
	}



	public JSONObject caricaPunteggi(ArrayList<String> livelli,ArrayList<Integer> livello_punteggio,String imei)
	{
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("tag", aggiorna_tag));
		 for(int i=0;i<livelli.size();i++)
		 {
			 params.add(new BasicNameValuePair(livelli.get(i),""+livello_punteggio.get(i)));
		 }
		 params.add(new BasicNameValuePair("imei",imei));
	     JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
	     return json;
	}


}

