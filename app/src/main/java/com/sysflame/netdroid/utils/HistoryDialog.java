package com.sysflame.netdroid.utils;

import android.app.Activity;
import android.widget.Button;

public class HistoryDialog {

    private Activity activity;
    private Button btnTestAgain;


    public HistoryDialog (Activity activity) {
        this.activity = activity;
    }

    public void historyResultDialogue (){

        /*LayoutInflater li = LayoutInflater.from (activity);
        View view = li.inflate (R.layout.dialog_buttom_result_sheet, null);
        Context wrapper = new ContextThemeWrapper(activity, R.style.MyDialogTheme);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder (
                wrapper);
        alertDialogBuilder.setView (view);

        btnTestAgain = view.findViewById(R.id.btn_testagain);

        btnTestAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(wrapper, "hello", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create ();
        alertDialog.show ();*/



    }
}
