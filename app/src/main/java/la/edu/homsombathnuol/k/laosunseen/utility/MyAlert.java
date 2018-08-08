package la.edu.homsombathnuol.k.laosunseen.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import la.edu.homsombathnuol.k.laosunseen.R;

public class MyAlert {
    private Context context;
    public MyAlert(Context context){
        this.context=context;
    }
    public void nomalDialog(String titleString, String messegeString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_alert);
        builder.setTitle(titleString);
        builder.setMessage(messegeString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
