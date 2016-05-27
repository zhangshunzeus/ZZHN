package com.zzhn.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class VerifyTools {
	String message;
	public int status = 0;

	public void verifyAccount(String tel) {
		new AnyTask().execute(tel);

	}

	@SuppressWarnings("unused")
	private String setverify(String tel) {
		
		StringBuilder builder = new StringBuilder();
		
		String verifycode = "http://192.168.0.3/";//---------------------------------------
		
		String urltel = "tel=";

		URL url;

		try {
			String urlverify = verifycode + "?" + urltel + tel;
			url = new URL(urlverify);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			connection.setRequestMethod("GET");
			connection.setReadTimeout(5000);
			connection.connect();

			if (connection.getResponseCode() == 200) {
				InputStream inputStream = connection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream));
				String line = bufferedReader.readLine();
				while (line != null && line.length() > 0) {
					builder.append(line);
					line = bufferedReader.readLine();
				}
				inputStream.close();
				bufferedReader.close();
				return builder.toString();
			}
			return "mror";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "error";

	}

	OnverifyListener listener;

	public void setOnverifyListener(OnverifyListener listener) {
		this.listener = listener;
	}

	@SuppressLint("NewApi")
	class AnyTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return setverify(arg0[0]);
		}

		protected void onPostExecute(String result) {
			if (listener != null) {
				listener.end(result);
			}
		}

	}

	public interface OnverifyListener {
		void start();

		void end(String result);
	}

	public String getMessage() {
		return message;
	}
}
