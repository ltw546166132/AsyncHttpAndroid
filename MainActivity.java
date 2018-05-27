package com.example.httpclienttest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {

	private EditText edname;
	private EditText edpassword;
	private Button buttonget;
	private Button buttonpost;
	private String path = "http://192.168.0.100:8080/Weblogin/Upload";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edname = (EditText) findViewById(R.id.ed_name);
		edpassword = (EditText) findViewById(R.id.password);
		buttonget = (Button) findViewById(R.id.btnget);
		buttonpost = (Button) findViewById(R.id.btnpost);
		
		buttonget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String name = edname.getText().toString();
					String password = edpassword.getText().toString();
					String url = path+"?username="+URLEncoder.encode(name, "utf-8")+"&password="+URLEncoder.encode(password,"utf-8");
					AsyncHttpClient client = new AsyncHttpClient();
					client.get(url, new MyHttpHandler());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		buttonpost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = edname.getText().toString();
				String password = edpassword.getText().toString();
				AsyncHttpClient client = new AsyncHttpClient();
				Map<String,String> source = new HashMap<String, String>();
				source.put("username", name);
				source.put("password", password);
				RequestParams res = new RequestParams(source);
				client.post(path, res, new MyHttpHandler());
			}
		});
	}
	
	private void showTost(final String str) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private class MyHttpHandler extends AsyncHttpResponseHandler{

		@Override
		public void onFailure(int statusCode, Header[] arg1, byte[] arg2, Throwable arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(int statusCode, Header[] arg1, byte[] arg2) {
			// TODO Auto-generated method stub
			if(statusCode==200) {
				try {
					String string = new String(arg2,"utf-8");
					showTost(string);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
	}

}

