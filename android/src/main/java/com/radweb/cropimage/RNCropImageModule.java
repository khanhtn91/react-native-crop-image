package com.radweb.cropimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Promise;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RNCropImageModule extends ReactContextBaseJavaModule {

	private final ReactApplicationContext reactContext;

	public RNCropImageModule(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
	}

	@Override
	public String getName() {
		return "RNCropImage";
	}

	@ReactMethod
	public void crop(String path, ReadableMap options, Promise promise) {
		BitmapFactory factory = new BitmapFactory();
		Bitmap image = factory.decodeFile(path);
		Bitmap cropped = Bitmap.createBitmap(
				image,
				options.getInt("left"),
				options.getInt("top"),
				options.getInt("width"),
				options.getInt("height")
		);

		FileOutputStream out = null;
		File dest = getOutputPath();

		try {
			out = new FileOutputStream(dest);
			cropped.compress(Bitmap.CompressFormat.JPEG, 100, out);
			promise.resolve(dest.getAbsolutePath());
		} catch (Exception e) {
				promise.reject("Failed to save", "Failed to save to file", null);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				promise.reject("Failed to save", "Failed to close saved file stream", null);
			}
		}
	}

	private File getOutputPath() {
		String randomName = getSaltString(12);
		File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		String fileName = String.format("IMG_%s_%s.jpg", new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), randomName);
		return new File(String.format("%s%s%s", root.getPath(), File.separator, fileName));
	}

	private String getSaltString(int length) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < length) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}
}
