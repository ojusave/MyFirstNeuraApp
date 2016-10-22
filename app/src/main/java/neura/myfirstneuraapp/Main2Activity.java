package neura.myfirstneuraapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.neura.sdk.callbacks.GetPermissionsRequestCallbacks;
import com.neura.sdk.object.Permission;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getPermissions();
        context = this;
//        FragmentPermissions fragmentPermissions = new FragmentPermissions();
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.activity_main2, fragmentPermissions);
//        fragmentTransaction.commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.activity_main2, fragmentPermissions).commit();

//        View view  = findViewById(R.id.permissions2);
//        ListView listView = (ListView) view;
//        ArrayAdapter<Permission> adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, permissions);
//        listView.setAdapter(adapter);

//        View view  = findViewById(R.id.permissions2);
//        ListView listView = (ListView) view;
//        List<String> strs = new ArrayList<String>();
//        strs.add("dfsdfsd");strs.add("sdfsdf");strs.add("sdfsdf");
//        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, strs);
//        listView.setAdapter(adapter);
    }

    private void openFragment(){
        FragmentPermissions fragmentPermissions = new FragmentPermissions();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.activity_main2, fragmentPermissions);
        fragmentTransaction.commit();
    }
    private void loadListView(){
        View view  = findViewById(R.id.permissions2);
        ListView listView = (ListView) view;
//        ArrayAdapter<Permission> adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, permissions);
//        listView.setAdapter(adapter);

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, permissionsDescriptions);
        listView.setAdapter(adapter);
    }

    private ArrayList<Permission> permissions = new ArrayList<>();
    private ArrayList<String> permissionsDescriptions = new ArrayList<>();
    Context context;

    private void getPermissions(){
        NeuraApp.getInstance().getClient().getAppPermissions(new GetPermissionsRequestCallbacks() {
            @Override
            public void onSuccess(final List<Permission> perms) throws RemoteException {
                if (context == null)
                    return;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        permissions.addAll(perms);
                        for(Permission permission: perms){
                            permissionsDescriptions.add(permission.getDisplayName());
                        }
                        loadListView();
                    }
                });

            }

            @Override
            public void onFailure(Bundle resultData, int errorCode) throws RemoteException {
                Toast.makeText(context, "No permissions available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        });

    }

}
