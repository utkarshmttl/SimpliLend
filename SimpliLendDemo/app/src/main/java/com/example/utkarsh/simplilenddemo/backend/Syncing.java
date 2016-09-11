package com.example.utkarsh.simplilenddemo.backend;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by utkarsh on 11/9/16.
 */
public class Syncing extends AsyncTask<String, Void, JSONObject> {
    Retrieve ret;
    public Syncing(Retrieve ret){
        this.ret= ret;
    }

    protected JSONObject doInBackground(String... message) {
        JSONObject ob = new JSONObject();


        Http ab = new Http();

        try {

            ob=new JSONObject(ab.read(message[0]));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }



        return ob;
    }

    protected void onPostExecute(JSONObject result){

        ret.retrieving(result);

    }
}
