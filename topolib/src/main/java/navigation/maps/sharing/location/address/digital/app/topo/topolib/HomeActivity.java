package navigation.maps.sharing.location.address.digital.app.topo.topolib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import navigation.maps.sharing.location.address.digital.app.topo.topolib.adapter.TopoHistroryAdapter;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.holders.ResponseData;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.holders.TopoData;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.retrofit.APIClient;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.retrofit.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 3/15/2018.
 */

public class HomeActivity extends Activity {

    TextView usemap_textview;
    String apikey = "", usermobile = "", usercountry = "";
    RecyclerView histroy_recyclerview;
    ProgressBar progressbar;
    APIInterface apiInterface;
    TopoData topoData = null;
    int position = 0;
    Button setlocation_button;
    EditText topo_edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_activity);
        histroy_recyclerview = (RecyclerView) findViewById(R.id.histroy_recyclerview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        setlocation_button = (Button) findViewById(R.id.setlocation_button);
        topo_edittext = (EditText) findViewById(R.id.topo_edittext);
        if (getIntent() != null) {
            apikey = getIntent().getStringExtra("apikey");
            usermobile = getIntent().getStringExtra("usermobile");
            usercountry = getIntent().getStringExtra("usercountry");
        }
        histroy_recyclerview.setHasFixedSize(true);
        histroy_recyclerview.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        Utility.startPermissionActivity(HomeActivity.this, 124);
        usemap_textview = (TextView) findViewById(R.id.usemap_textview);
        usemap_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MapActivity.class);
                i.putExtra("apikey", apikey);
                i.putExtra("usermobile", usermobile);
                i.putExtra("usercountry", usercountry);
                startActivity(i);
            }
        });

        apiInterface = APIClient.getClient().create(APIInterface.class);
        progressbar.setVisibility(View.VISIBLE);
        doingHistroyApiCall();

        setlocation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topoData != null) {
                    if (TopoInitialization.topoAppNotifier != null) {
                        TopoInitialization.topoAppNotifier.topoAppSucess(topoData);
                        finish();
                    }
                } else {
                    progressbar.setVisibility(View.VISIBLE);
                    doingGetTopoDataCall();
                }
            }
        });
    }

    void doingHistroyApiCall() {
        Call<ResponseData> call = apiInterface.doGetHisrtory("androidsdk", usermobile, usercountry, apikey);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, final Response<ResponseData> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                        ResponseData data = response.body();
                        if (data != null && data.getData() != null && data.getData().size() > 0) {
                            TopoHistroryAdapter adapter = new TopoHistroryAdapter(listener, data.getData());
                            histroy_recyclerview.setAdapter(adapter);
                        }

                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                call.cancel();


            }
        });
    }


    void doingGetTopoDataCall() {
        Call<ResponseData> call = apiInterface.doGetTopo(topo_edittext.getText().toString(), apikey);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, final Response<ResponseData> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                        ResponseData data = response.body();
                        if (data != null && data.getTopo_data() != null && data.getTopo_data().size() > 0) {
                            if (TopoInitialization.topoAppNotifier != null) {
                                TopoInitialization.topoAppNotifier.topoAppSucess(data.getTopo_data().get(0));
                                finish();
                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "Topo  is not found", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                call.cancel();

            }
        });
    }

    TopoHistroryAdapter.OnItemSelectedListener listener = new TopoHistroryAdapter.OnItemSelectedListener() {
        @Override
        public void onItemSelected(TopoData data) {
            topoData = data;
        }

        @Override
        public void onItemRemoved(TopoData data) {
            topoData = null;
        }
    };

}
