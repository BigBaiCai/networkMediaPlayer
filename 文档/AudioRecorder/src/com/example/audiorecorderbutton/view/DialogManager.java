package com.example.audiorecorderbutton.view;

import java.util.zip.Inflater;

import com.example.audiorecorderbutton.R;

import android.R.integer;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogManager {
	private Dialog dialog;
	private Context context;
	private ImageView img_v1;
	private ImageView img_recorder;
	private TextView textView_recorder;
	private LayoutInflater layoutInflater;
	private View view;

	public DialogManager(Context context) {
		this.context = context;
	}

	public void showRecordingDialog() {
		// �ڶ������� ���Զ�����һ��dialog����style���Զ����
		dialog = new Dialog(context, R.style.theme_audioDialog);
		layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.dialog_recording,null);
		dialog.setContentView(view);
		img_recorder = (ImageView) dialog.findViewById(R.id.img_recorder);
		img_v1 = (ImageView) dialog.findViewById(R.id.img_v1);
		textView_recorder = (TextView) dialog
				.findViewById(R.id.textView_recorder);
		dialog.show();
	}

	public void recording() {
		if (dialog != null && dialog.isShowing()) {
			img_recorder.setVisibility(View.VISIBLE);
			img_v1.setVisibility(View.VISIBLE);
			textView_recorder.setVisibility(View.VISIBLE);

			img_recorder.setImageResource(R.drawable.recorder);
			textView_recorder.setText("��ָ�ϻ���ȡ������");
		}
	}

	public void wantToCancel() {
		if (dialog != null && dialog.isShowing()) {
			img_recorder.setVisibility(View.VISIBLE);
			img_v1.setVisibility(View.GONE);// ȥ��ͼƬ
			textView_recorder.setVisibility(View.VISIBLE);

			img_recorder.setImageResource(R.drawable.cancel);
			textView_recorder.setText("�ɿ���ָ��ȡ������");
		}
	}

	public void tooShort() {
		if (dialog != null && dialog.isShowing()) {
			img_recorder.setVisibility(View.VISIBLE);
			img_v1.setVisibility(View.GONE);// ȥ��ͼƬ
			textView_recorder.setVisibility(View.VISIBLE);

			img_recorder.setImageResource(R.drawable.voice_to_short);
			textView_recorder.setText("¼��ʱ��̫��!");
		}
	}

	public void dismissDialog() {
		if(dialog!=null&&dialog.isShowing()){
		dialog.dismiss();
		dialog=null;
		}
	}
	//ͨ��levelȥ����voice�ϵ�ͼƬ
	public void updateVoiceLevel(int level) {
		if (dialog != null && dialog.isShowing()) {
//			img_recorder.setVisibility(View.VISIBLE);
//			img_v1.setVisibility(View.VISIBLE);
//			textView_recorder.setVisibility(View.VISIBLE);
			//ͨ���������ҵ���Դ
			int resId =context.getResources().getIdentifier("v"+level, "drawable", context.getPackageName());
			img_v1.setImageResource(resId);
			
		}
	}

}
