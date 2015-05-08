package com.skAndroid.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.skAndroid.R;

/**
 * Created by khangtnse60992 on 11/5/2014.
 */
public class Util {

//    public String[] getWord
      public AlertDialog.Builder dialogConnection(Context context,View.OnClickListener onClickListener) {
          AlertDialog.Builder builder = new AlertDialog.Builder(context);
          builder.setTitle("Error");
          builder.setMessage("Lost Connection");
          builder.setPositiveButton("Try Again",(DialogInterface.OnClickListener)onClickListener);
          builder.setNegativeButton("Back", (DialogInterface.OnClickListener) onClickListener);
//          builder.create();
//          builder.show();
          return builder;
      }
}
