package it.unisa.dodgeholes;

/**
 * Author :Raj Amal
 * Email  :raj.amalw@learn2crack.com
 * Website:www.learn2crack.com
 **/

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;


public class UserFunctions {

    private JSONParser jsonParser;

    private static String registerURL = "http://www.depiano.it/DodgeHoles/";

    private static String register_tag = "register";
   
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

}

