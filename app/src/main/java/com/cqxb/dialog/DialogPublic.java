package com.cqxb.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cqxb.yecall.R;

public class DialogPublic extends Dialog {

	private static Context context;
	private String content = null;
	private String title = null;
	private boolean bCancel = false;

	public DialogPublic(Context context, String title, String content, boolean bCancel) {
		this(context);
		this.title = title;
		this.content = content;
		this.bCancel = bCancel;
	}

	public DialogPublic(Context context) {
		super(context, R.style.AppDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_public);
		if (title != null) {
			((TextView) findViewById(R.id.textViewCaption)).setText(title);
		}
		if (content != null) {
			((TextView) findViewById(R.id.textViewContent)).setText(content);
		}

		if (bCancel) {
			((Button) findViewById(R.id.buttonCancel)).setVisibility(View.GONE);
		}

		((Button) findViewById(R.id.buttonCancel)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogPublic.this.dismiss();
			}
		});

		((Button) findViewById(R.id.buttonOK)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogPublic.this.dismiss();
			}
		});

	}

	public void setOKBtn(String name, View.OnClickListener listener) {
		((Button) findViewById(R.id.buttonOK)).setText(name);
		((Button) findViewById(R.id.buttonOK)).setOnClickListener(listener);
	}

	public void setCancelBtn(String name, View.OnClickListener listener) {
		((Button) findViewById(R.id.buttonCancel)).setText(name);
		((Button) findViewById(R.id.buttonCancel)).setOnClickListener(listener);
	}

}
