package navigation.maps.sharing.location.address.digital.app.topo.topolib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import navigation.maps.sharing.location.address.digital.app.topo.topolib.notifier.TopoAppNotifier;


/**
 * Created by mobiikey-dilip on 3/20/2018.
 */

public class TopoInitialization {

    public static final String TAG = TopoInitialization.class.getSimpleName();
    public static TopoInitialization topoInitialization = null;
    public static TopoAppNotifier topoAppNotifier;
    Context mContext;

    public TopoInitialization(Context con) {
        mContext = con;
    }

    public static TopoInitialization getInstance(Context context) {
        if (topoInitialization == null) {
            topoInitialization = new TopoInitialization(context);
        }
        return topoInitialization;
    }

    public void callingTopoHome(Activity activity, String apikey, String usermobile, String usercountry) {
        Intent homeItent = new Intent(mContext, HomeActivity.class);
        homeItent.putExtra("apikey", apikey);
        homeItent.putExtra("usermobile", usermobile);
        homeItent.putExtra("usercountry", usercountry);
        activity.startActivity(homeItent);
    }

    public void setAppNotifier(TopoAppNotifier topoAppNotifier) {
        this.topoAppNotifier = topoAppNotifier;
    }
}
