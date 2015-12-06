package com.example.audiorecorderbutton.view;

import com.example.audiorecorderbutton.MediaManager;
import com.example.audiorecorderbutton.R;
import com.example.audiorecorderbutton.view.AudioManager.AudioStateListener;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class AudioRecorderButton extends Button implements AudioStateListener {
	private static final int STATE_NORMAL = 1;// Ĭ��״̬
	private static final int STATE_RECORDING = 2;// ¼��״̬
	private static final int STATE_WANT_TO_CANCEL = 3;// ȡ��״̬
	private static final int DISTANCE_Y_CANCEL = 50;

	private int mCurState = 1;
	private boolean isRecording = false;
	private DialogManager dialogManager;
	private AudioManager audioManager;
	private float time;
	// �Ƿ������longclick�ı�־
	private boolean ready;

	public AudioRecorderButton(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public AudioRecorderButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		dialogManager = new DialogManager(getContext());
//		String dir = Environment.getExternalStorageDirectory()
//				+ "/recorder_audio";
//		audioManager = AudioManager.getInstance(dir);
//		audioManager.setOnAudioStateListener(this);

		setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				
				ready = true;
				audioManager.prepareAudio();
				return false;
			}
		});
	}

	// ¼����ɺ�Ļص�
	public interface AudioRecorderFinishListener {
		void onFinish(Float seconds, String filePath);
	}

	private AudioRecorderFinishListener finishListener;

	public void setAudioRecorderFinishListener(
			AudioRecorderFinishListener finishListener) {
		this.finishListener = finishListener;
	}

	// ��ȡ������С��Runnable
	private Runnable getVoiceLevelRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (isRecording) {
				try {
					Thread.sleep(100);
					time += 0.1f;
					handler.sendEmptyMessage(MSG_VOICE_CHANGE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	};

	private static final int MSG_AUDIO_PREPARE = 0X110;
	private static final int MSG_VOICE_CHANGE = 0X111;
	private static final int MSG_DIALOG_DISMISS = 0X112;

	private Handler handler = new Handler() {
		public void handlerMessage(android.os.Message Msg) {
			switch (Msg.what) {
			case MSG_AUDIO_PREPARE:
				// ������ʾӦ����audio end prepared�Ժ�
				dialogManager.showRecordingDialog();
				isRecording = true;
				new Thread(getVoiceLevelRunnable).start();
				break;
			case MSG_VOICE_CHANGE:
				dialogManager.updateVoiceLevel(audioManager.getVoiceLevel(7));
				break;
			case MSG_DIALOG_DISMISS:
				dialogManager.dismissDialog();
				break;

			}

		}
	};

	// ��ȫ׼�����Ժ��׼���ص�
	@Override
	public void wellPrepared() {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(MSG_AUDIO_PREPARE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			 isRecording = true;
			changeState(STATE_RECORDING);
			break;
		case MotionEvent.ACTION_MOVE:
			if (isRecording) {
				// ����x,y�����꣬�ж��Ƿ���Ҫȡ��
				if (wantToCancel(x, y)) {
					changeState(STATE_WANT_TO_CANCEL);
				} else {
					changeState(STATE_RECORDING);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (!ready) {
				reset();
				return super.onTouchEvent(event);
			}
			if (!isRecording || time < 0.6f) {
				dialogManager.tooShort();
				audioManager.cancel();
				handler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS, 1300);// �ӳ�
			} else if (mCurState == STATE_RECORDING) {// ����¼�ƽ���
				dialogManager.dismissDialog();

				if (finishListener != null) {
					finishListener.onFinish(time,
							audioManager.getCurrentFilePath());
				}
				audioManager.release();
			} else if (mCurState == STATE_WANT_TO_CANCEL) {

				dialogManager.dismissDialog();
				audioManager.cancel();
			}
			reset();
			break;

		}
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	// �ָ�״̬����־λ
	private void reset() {
		// TODO Auto-generated method stub
		isRecording = false;
		ready = false;
		time = 0;
		changeState(STATE_NORMAL);
	}

	private boolean wantToCancel(int x, int y) {
		// TODO Auto-generated method stub
		// �ж���ָ�ĺ�������û�г�����ť��Χ
		if (x < 0 || x > getWidth()) {
			return true;
		}
		if (y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL) {
			return true;
		}
		return false;
	}

	private void changeState(int state) {
		// TODO Auto-generated method stub
		if (mCurState != state) {
			mCurState = state;
			switch (state) {
			case STATE_NORMAL:
				setBackgroundResource(R.drawable.normal);
				setText(R.string.STATE_NORMAL);
				break;
			case STATE_RECORDING:
				setBackgroundResource(R.drawable.recording);
				setText(R.string.STATE_RECORDING);
				if (isRecording) {
					dialogManager.recording();
				}
				break;
			case STATE_WANT_TO_CANCEL:
				setBackgroundResource(R.drawable.recording);
				setText(R.string.STATE_WANT_TO_CANCEL);
				
					dialogManager.wantToCancel();
				
				break;

			}
		}
	}

}
