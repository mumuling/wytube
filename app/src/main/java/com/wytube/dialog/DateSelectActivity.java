package com.wytube.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;

import com.cqxb.yecall.R;
import com.skyrain.library.k.BindClass;
import com.skyrain.library.k.api.KActivity;
import com.skyrain.library.k.api.KBind;
import com.skyrain.library.k.api.KListener;


@KActivity(R.layout.activity_date_select)
public class DateSelectActivity extends Activity {

    @KBind(R.id.datePicker)
    private DatePicker mDatePicker;
    private static DateSet dateSet;

    public static void setDateSet(DateSet date) {
        dateSet = date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindClass.bind(this);
    }

    @KListener(R.id.ok_but)
    private void ok_butOnClick() {
        if (dateSet != null) {
            dateSet.setDate(
                    mDatePicker.getYear() + "年" +
                            (mDatePicker.getMonth() + 1) + "月" +
                            mDatePicker.getDayOfMonth() + "日",
                    mDatePicker.getYear(),
                    mDatePicker.getMonth() + 1,
                    mDatePicker.getDayOfMonth());
        }
        this.finish();
    }

    public interface DateSet {
        void setDate(String date, int year, int month, int day);
    }
}
