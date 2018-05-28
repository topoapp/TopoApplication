package navigation.maps.sharing.location.address.digital.app.topo.topolib;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import navigation.maps.sharing.location.address.digital.app.topo.topolib.holders.ResponseData;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.retrofit.APIClient;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 3/14/2018.
 */

public class CreateTopoActivity extends Activity {

    EditText flat_edittext;
    Button submit_button;
    ProgressBar progressbar;
    String lat = "", lon = "", address = "", city = "", country = "", postalCode = "", topoId = null, usermobile = null, usercountry = null, apiKey = null;
    APIInterface apiInterface;
    int apicall = 0;
    String TAG = CreateTopoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.createtopo_activity);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        submit_button = (Button) findViewById(R.id.submit_button);
        flat_edittext = (EditText) findViewById(R.id.flat_edittext);

        if (getIntent() != null) {
            lat = getIntent().getStringExtra("lat");
            lon = getIntent().getStringExtra("lon");
            address = getIntent().getStringExtra("address");
            city = getIntent().getStringExtra("city");
            country = getIntent().getStringExtra("country");
            postalCode = getIntent().getStringExtra("postalCode");
            topoId = getIntent().getStringExtra("topoId");
            apiKey = getIntent().getStringExtra("apikey");
            usermobile = getIntent().getStringExtra("usermobile");
            usercountry = getIntent().getStringExtra("usercountry");
        }


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isEmpty(flat_edittext)) {
                    Toast.makeText(CreateTopoActivity.this, "Please Enter Flat Number", Toast.LENGTH_SHORT).show();
                } else {
                    if (apicall == 0) {
                        progressbar.setVisibility(View.VISIBLE);
                        apicall = 1;
                        callingMapApi();
                    }
                }
            }
        });
    }

    void callingMapApi() {
        Call<ResponseData> call = apiInterface.doCreateTopo("androidsdk", topoId, usermobile, lat, lon, flat_edittext.getText().toString(), address, country, city, postalCode, usercountry, apiKey);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, final Response<ResponseData> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                        apicall = 0;
                        ResponseData data = response.body();
                        if (data != null && data.getTopo_data() != null && data.getTopo_data().size() > 0) {
                            if (TopoInitialization.topoAppNotifier != null) {
                                TopoInitialization.topoAppNotifier.topoAppSucess(data.getTopo_data().get(0));
                                finish();
                            }
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                apicall = 0;
                Logger.i(TAG, "forgot Response failure::" + t.getMessage());
                call.cancel();


            }
        });
    }
}
