package navigation.maps.sharing.location.address.digital.app.topo.topolib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;


/**
 * Created by Manoj on 4/19/2017.
 */

public class Utility {


    public static Typeface fontTitlereguler;
    public static void loadFonts(Context con) {
        //fontTitlereguler =  getTypefaceInAsset(R.string.montserrat, con);

    }

    public static Typeface getTypefaceInAsset(int font_name, Context con) {
        return Typeface.createFromAsset(con.getAssets(), "fonts/" + con.getResources().getString(font_name));
    }

    public static boolean isEmpty(EditText editText) {
        boolean flag = false;
        if (editText.getText().toString().equals("") || editText.getText().toString().length() == 0) {
            flag = true;
        }
        return flag;
    }
    public static void startPermissionActivity(Activity context, int type) {
        Intent permissionIntent = new Intent(context, PermissionsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        permissionIntent.putExtras(bundle);
        permissionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(permissionIntent);
    }
}