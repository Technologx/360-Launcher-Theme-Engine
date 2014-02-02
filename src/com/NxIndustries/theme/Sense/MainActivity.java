package com.NxIndustries.theme.Sense;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	final static String TARGET_BASE_PATH = "/sdcard/360launcher/theme/custom/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	final Button iButton = (Button) findViewById(R.id.install);
    iButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	copyFileOrDir("");
        	showProgress("");
    }
    });
    final Button eButton = (Button) findViewById(R.id.exit);
    eButton.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View v) {
    		MainActivity.this.finish();
			
		}
	});
	}
	
	public void showProgress(String string){

		  TextView textview = (TextView) findViewById(R.id.complete);
		  textview.setVisibility(View.VISIBLE);
		}

		private void copyFileOrDir(String path) {
			AssetManager assetManager = this.getAssets();
		    String assets[] = null;
		    try {
		        Log.i("tag", "copyFileOrDir() "+path);
		        assets = assetManager.list(path);
		        if (assets.length == 0) {
		            copyFile(path);
		        } else {
		            String fullPath =  TARGET_BASE_PATH + path;
		            Log.i("tag", "path="+fullPath);
		            File dir = new File(fullPath);
		            if (!dir.exists() && !path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
		                if (!dir.mkdirs());
		                    Log.i("tag", "could not create dir "+fullPath);
		            for (int i = 0; i < assets.length; ++i) {
		                String p;
		                if (path.equals(""))
		                    p = "";
		                else 
		                    p = path + "/";

		                if (!path.startsWith("images") && !path.startsWith("sounds") && !path.startsWith("webkit"))
		                    copyFileOrDir( p + assets[i]);
		            }
		        }
		    } catch (IOException ex) {
		        Log.e("tag", "I/O Exception", ex);
		    }
		}

		private void copyFile(String filename) {
			AssetManager assetManager = this.getAssets();

		    InputStream in = null;
		    OutputStream out = null;
		    String newFileName = null;
		    try {
		        Log.i("tag", "copyFile() "+filename);
		        in = assetManager.open(filename);
		        if (filename.endsWith(".jpg"))
		            newFileName = TARGET_BASE_PATH + filename.substring(0, filename.length()-4);
		        else
		            newFileName = TARGET_BASE_PATH + filename;
		        out = new FileOutputStream(newFileName);

		        byte[] buffer = new byte[1024];
		        int read;
		        while ((read = in.read(buffer)) != -1) {
		            out.write(buffer, 0, read);
		        }
		        in.close();
		        in = null;
		        out.flush();
		        out.close();
		        out = null;
		    } catch (Exception e) {
		        Log.e("tag", "Exception in copyFile() of "+newFileName);
		        Log.e("tag", "Exception in copyFile() "+e.toString());
		    }
		}
}
