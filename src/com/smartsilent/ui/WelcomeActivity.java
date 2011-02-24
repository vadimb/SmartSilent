package com.smartsilent.ui;

import com.smartsilent.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.smartsilent.ui.ChoseAccountActivity;
/**
 * @author vadim
 * 
 */
public class WelcomeActivity extends Activity {

	public static final String ACTION_WELCOME="com.smartsilent.WELCOME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.welcome_activity);	
		findViewById(R.id.next).setOnClickListener(new OnNextClickListener());
	}

	class OnNextClickListener implements View.OnClickListener {
		public void onClick(View v) {				
			startActivity(new Intent(ChoseAccountActivity.ACTION_CHOSE_ACCOUNT));
		
		}

	}
}

 