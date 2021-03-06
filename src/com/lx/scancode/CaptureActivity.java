package com.lx.scancode;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.hzh.savefoodies.*;
import com.fada.sellsteward.scalecode.camera.CameraManager;
import com.fada.sellsteward.scalecode.decoding.CaptureActivityHandler;
import com.fada.sellsteward.scalecode.decoding.InactivityTimer;
import com.fada.sellsteward.scalecode.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.bottle.ourApp.*;
import com.hzh.savefoodies.R;
public class CaptureActivity extends Activity implements Callback,OnClickListener {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private TextView txtResult;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private TextView codeOk;
	private TextView codeCancel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_code);
		CameraManager.init(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		txtResult = (TextView) findViewById(R.id.txtResult);
		codeOk = (TextView) findViewById(R.id.codeOk);
		codeCancel = (TextView) findViewById(R.id.codeCancel);
		codeOk.setOnClickListener(this);
		codeCancel.setOnClickListener(this);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.codeCancel:
			//finish();
			//back(v);
			startActivity(new Intent(CaptureActivity.this,com.hzh.savefoodies.MainActivity.class));
			break;
		case R.id.codeOk:
			Intent intent=getIntent();
			if (intent!=null) {
				intent.putExtra("code", code+"");
				MyApp.app.obj=code;
			}
			setResult(200,intent);
			finish();
			break;

		default:
			break;
		}
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		viewfinderView.drawResultBitmap(barcode);
		playBeepSoundAndVibrate();
		code = obj.getText();
		//example="123456789";
		if (code != null) {
			if (code.startsWith("http://")) {
				Uri myBlogUri = Uri.parse(code); 
				Intent returnIt = new Intent(Intent.ACTION_VIEW, myBlogUri); 
				startActivity(returnIt);
				finish();
			}else{
				Intent i=new Intent(CaptureActivity .this,com.hzh.savefoodies. MainActivity.class);
				Bundle b=new Bundle();
				b.putString("CODE",code);
				i.putExtras(b);
				this.setResult(RESULT_OK, i);
				finish();
				//startActivity(new Intent(CaptureActivity .this, MainActivity.class));  
				//txtResult.setText(code);
			}
		}

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	private String code;
	//private String example;

	  public void back(View v){  
	    Intent intent=new Intent();  
		      //第一种跳回的方法  
	       intent.setClassName("com.lx.scancode.CaptureActivity", "com.bottle.ourApp.MainActivity");  
		      //第二种  
		
	        startActivity(intent);  
	  }  

	
	    public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    	 startActivity(new Intent(CaptureActivity.this,com.hzh.savefoodies.MainActivity .class));
	        return false; 
	    }
}