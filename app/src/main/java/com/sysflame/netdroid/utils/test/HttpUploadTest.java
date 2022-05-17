package com.sysflame.netdroid.utils.test;

import android.util.Log;

import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpUploadTest extends Thread {

	static int uploadedKByte = 0;
	public String fileURL = "";
	double uploadElapsedTime = 0;
	boolean finished = false;
	double elapsedTime = 0;
	double finalUploadRate = 0.0;
	long startTime;

	public HttpUploadTest (String fileURL) {
		this.fileURL = fileURL;
	}

	private double round (double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		if (value > 0) {

			Log.d("BigDecimalValue", "value: " + value);
			BigDecimal bd = BigDecimal.valueOf(value);
			bd = bd.setScale(places, RoundingMode.HALF_UP);
			return bd.doubleValue();
		} else {
			return 0;
		}
	}

	public boolean isFinished () {
		return finished;
	}
	public double getInstantUploadRate () {
		if (uploadedKByte >= 0) {
			long now = System.currentTimeMillis ();
			elapsedTime = (now - startTime) / 1000.0;
			return round (((uploadedKByte / 1000.0) * 8) / elapsedTime, 2);
		} else {
			return 0.0;
		}
	}
	public double getFinalUploadRate () {
		return round (finalUploadRate, 2);
	}

	@Override
	public void run () {
		try {
			URL url = new URL (fileURL);
			uploadedKByte = 0;
			startTime = System.currentTimeMillis ();
			ExecutorService executor = Executors.newFixedThreadPool (4);
			for (int i = 0 ; i < 4 ; i++) {
				executor.execute (new HandlerUpload (url));
			}
			executor.shutdown ();
			while (!executor.isTerminated ()) {
				try {
					Thread.sleep (100);
				} catch (InterruptedException ex) {
					ex.printStackTrace ();
					Thread.currentThread ().interrupt ();
				}
			}
			long now = System.currentTimeMillis ();
			uploadElapsedTime = (now - startTime) / 1000.0;
			finalUploadRate = ((uploadedKByte / 1000.0) * 8) / uploadElapsedTime;
		} catch (Exception ex) {
			ex.printStackTrace ();
		}
		finished = true;
	}
}

class HandlerUpload extends Thread {

	URL url;
	public HandlerUpload (URL url) {
		this.url = url;
	}

	public void run () {
		byte[] buffer = new byte[ 150 * 1024 ];
		long startTime = System.currentTimeMillis ();
		int timeout = 10;
		while (true) {
			try {
				HttpURLConnection conn = null;
				conn = (HttpURLConnection) url.openConnection ();
				conn.setDoOutput (true);
				conn.setRequestMethod ("POST");
				conn.setRequestProperty ("Connection", "Keep-Alive");
				DataOutputStream dos = new DataOutputStream (conn.getOutputStream ());
				dos.write (buffer, 0, buffer.length);
				dos.flush ();
				conn.getResponseCode ();
				HttpUploadTest.uploadedKByte += buffer.length / 1024.0;
				long endTime = System.currentTimeMillis ();
				double uploadElapsedTime = (endTime - startTime) / 1000.0;
				if (uploadElapsedTime >= timeout) {
					break;
				}
				dos.close ();
				conn.disconnect ();
			} catch (Exception ex) {
				ex.printStackTrace ();
			}
		}
	}
}
