package com.wytube.activity;

import android.app.Activity;
import android.os.Bundle;

import com.cqxb.yecall.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;
import com.wytube.utlis.AppValue;


@KActivity(R.layout.activity_image)
public class ImageActivity extends Activity {

    @KBind(R.id.image_show)
    private SimpleDraweeView mImageShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
        if (AppValue.tempImage.length() > 0) {
//            Utils.loadImage(mImageShow, AppValue.tempImage);
            mImageShow.setImageURI(AppValue.tempImage);
        }
    }

    @KListener({R.id.image_show, R.id.image_layout})
    private void image_showOnClick() {
        this.finish();
    }
}
