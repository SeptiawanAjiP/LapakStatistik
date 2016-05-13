package com.example.ajipradana.lapak.Session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;


import com.example.ajipradana.lapak.UserInterface.Navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("CommitPrefEdits")
public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// nama sharepreference
	private static final String PREF_NAME = "Sesi";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	public static final String KEY_NIM = "nim";
	public static final String KEY_NAMA = "nama";
	public static final String KEY_ID_JABATAN ="id_jabatan";
	public static final String KEY_NIM_KORTIM ="nim_kortim";
	public static final String KEY_PASSWORD ="password";
	public static final ArrayList<String> KEY_BLOK_SENSUS = new ArrayList<>();


	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String nim, String nama, String id_jabatan, String nim_kortim, String password){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		editor.putString(KEY_NIM, nim);
		editor.putString(KEY_NAMA, nama);
		editor.putString(KEY_ID_JABATAN, id_jabatan);
		editor.putString(KEY_NIM_KORTIM,nim_kortim);
		editor.putString(KEY_PASSWORD, password);
		Log.d("nimkortim", nim_kortim);

		editor.commit();
	}

	public void createListSession (List<String> blokSensus,int hasil){
		ArrayList<String> a = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		for(int i=0; i<hasil;i++) {

			ArrayList<String> blok = new ArrayList<>();

			sb.append(blokSensus.get(i)).append(",");

			editor.putString(KEY_BLOK_SENSUS.get(i),sb.toString());
			//editor.putString(KEY_BLOK_SENSUS, blokSensus[i]);
			//editor.commit();
		}
	}
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			Intent i = new Intent(_context, Navigation.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(i);
			//((Activity)_context).finish();
		}

	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();

		user.put(KEY_NIM, pref.getString(KEY_NIM, null));
		user.put(KEY_NAMA, pref.getString(KEY_NAMA, null));
		user.put(KEY_ID_JABATAN, pref.getString(KEY_ID_JABATAN,null));
		user.put(KEY_NIM_KORTIM, pref.getString(KEY_NIM_KORTIM,null));
		user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD,null));
		return user;
	}
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear().commit();

		Intent i = new Intent(_context, Navigation.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		_context.startActivity(i);
	}

	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
