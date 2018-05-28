package navigation.maps.sharing.location.address.digital.app.topo.topoapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import navigation.maps.sharing.location.address.digital.app.topo.topolib.TopoInitialization;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.holders.TopoData;
import navigation.maps.sharing.location.address.digital.app.topo.topolib.notifier.TopoAppNotifier;

public class MainActivity extends AppCompatActivity implements TopoAppNotifier {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button gototopo = (Button) findViewById(R.id.gototopo);
        gototopo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopoInitialization.getInstance(MainActivity.this).callingTopoHome(MainActivity.this, "35c6af907ceb7b5df8e2a1f321d82fb5", "7022858863", "91");
                TopoInitialization.getInstance(MainActivity.this).setAppNotifier(MainActivity.this);
            }
        });
    }

    @Override
    public void topoAppSucess(TopoData data) {
        Toast.makeText(MainActivity.this, "Topo Data is Got" + data.getTopo_name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void topoAppFailure() {

    }
}
