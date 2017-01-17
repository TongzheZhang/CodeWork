package com.jluo80.amazinggifter.ui.activity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.jluo80.amazinggifter.R;

import org.w3c.dom.Text;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private int year;
    private int month;
    private int day;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView selectDateTextView = (TextView) getActivity().findViewById(R.id.select_date);
        String YEAR = Integer.toString(view.getYear() - 2000);
        int temp = view.getMonth() + 1;
        String MONTH = Integer.toString(temp);
        String DAY = Integer.toString(view.getDayOfMonth());

        if(temp < 10)
        {
            MONTH = "0" + temp;
        }

        if(view.getDayOfMonth() < 10)
        {
            DAY = "0" + view.getDayOfMonth();
        }

        selectDateTextView.setText(MONTH + "/" + DAY + "/" + YEAR);
    }

}
