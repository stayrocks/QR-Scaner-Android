package com.example.qrcodegenrate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class GenerateQRCodeActivity extends Activity implements OnClickListener{
	 
	 private String LOG_TAG = "GenerateQRCode";
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
	 
	  Button button1 = (Button) findViewById(R.id.button1);
	  Button button2 = (Button) findViewById(R.id.button2);
	  button1.setOnClickListener(this);
	  button2.setOnClickListener(this);
	 
	 }
	 
	 public void onClick(View v) {
	 
	  switch (v.getId()) {
	  case R.id.button1:
	   EditText qrInput = (EditText) findViewById(R.id.qrInput);
	   String qrInputText = qrInput.getText().toString();
	   Log.v(LOG_TAG, qrInputText);
	 
	   //Find screen size
	   WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
	   Display display = manager.getDefaultDisplay();
	   Point point = new Point();
	   display.getSize(point);
	   int width = point.x;
	   int height = point.y;
	   int smallerDimension = width < height ? width : height;
	   smallerDimension = smallerDimension * 3/4;
	 
	   //Encode with a QR Code image
	   QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
	             null,
	             Contents.Type.TEXT, 
	             BarcodeFormat.QR_CODE.toString(),
	             smallerDimension);
	   try {
	    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
	    ImageView myImage = (ImageView) findViewById(R.id.imageView1);
	    myImage.setImageBitmap(bitmap);
	 
	   } catch (WriterException e) {
	    e.printStackTrace();
	   }
	 
	 
	   break;
	   
	  case R.id.button2:
		  
		  Intent intent = new Intent("com.google.zxing.client.android.SCAN");
          intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
          startActivityForResult(intent, 0);
		  
		  
		  
	   // More buttons go here (if any) ...
	 
	  }
	  
	  
	  
	  
	 }
	 
	 public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		    if (requestCode == 0) {
		        if (resultCode == RESULT_OK) {
		           String contents = intent.getStringExtra("SCAN_RESULT");
		            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
		            Log.i("Barcode Result", contents);
		            //Intent i1 = new Intent(QRCodeSampleActivity.this, webclass.class);
		            //startActivity(i1);
		            // Handle successful scan
		        } else if (resultCode == RESULT_CANCELED) {
		            // Handle cancel
		            Log.i("Barcode Result","Result canceled");
		        }
		    }
		}
	 
	 
	 
	 
	 
	}