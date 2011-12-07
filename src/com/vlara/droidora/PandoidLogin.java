/* Pandoroid Radio - open source pandora.com client for android
 * Copyright (C) 2011  Andrew Regner <andrew@aregner.com>
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.vlara.droidora;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class PandoidLogin extends Activity {

	public static final String Password_Store = ".pandora_store_pers";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		SharedPreferences first_pref = getSharedPreferences(Password_Store, 0);
		String Pusername = first_pref.getString("pandora_username","");
		String Ppassword = first_pref.getString("pandora_password", "");
		if(Pusername != ""&& Ppassword !=""){
			((EditText)findViewById(R.id.login_username)).setText(Pusername, TextView.BufferType.EDITABLE);
			((EditText)findViewById(R.id.login_password)).setText(Ppassword, TextView.BufferType.EDITABLE);
			((CheckBox) findViewById(R.id.savePassCheck)).setChecked(true);
		}
		
		((Button)findViewById(R.id.login_button)).setOnClickListener(new OnClickListener() {
			public void onClick(View viewParam) {
				String sUserName = ((EditText)findViewById(R.id.login_username)).getText().toString();
				String sPassword = ((EditText)findViewById(R.id.login_password)).getText().toString();
				 final CheckBox checkBox = (CheckBox) findViewById(R.id.savePassCheck);
		         if (checkBox.isChecked()) {
		             savePassCodePref(sUserName,sPassword);
		         }else{
		        	 clearPassCodePref();
		         }
		         
				// this just catches the error if the program cant locate the GUI stuff
				if(sUserName != null && sPassword != null && sUserName.length() > 1 && sPassword.length() > 1) {
					boolean success = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit()
						.putString("pandora_username", sUserName)
						.putString("pandora_password", sPassword)
						.commit();

					if(success) {
						setResult(RESULT_OK);
						finish();
						//finishActivityFromChild(child, PandoidPlayer.REQUIRE_LOGIN_CREDS);
						//finishActivity(PandoidPlayer.REQUIRE_LOGIN_CREDS);
					}
				}
			}
		});
	}
	
	private void savePassCodePref(String userName, String passCode )
	{
		// We need an Editor object to make preference changes.
	      // All objects are from android.context.Context
	      SharedPreferences settings = getSharedPreferences(Password_Store, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString("pandora_username", userName);
	      editor.putString("pandora_password", passCode);
	      // Commit the edits!
	      editor.commit();

	}
	private void clearPassCodePref(){
		
		SharedPreferences settings = getSharedPreferences(Password_Store, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.clear();
	      // Commit the edits!
	      editor.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.player_menu, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		PandoidPlayer.dismissWaiting();
	}
}
