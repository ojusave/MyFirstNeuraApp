package neura.myfirstneuraapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import com.neura.resources.authentication.AuthenticateCallback;
import com.neura.resources.authentication.AuthenticateData;
import com.neura.resources.data.PickerCallback;
import com.neura.sdk.callbacks.GetPermissionsRequestCallbacks;
import com.neura.sdk.object.AuthenticationRequest;
import com.neura.sdk.object.Permission;
import com.neura.standalonesdk.service.NeuraApiClient;
import com.neura.standalonesdk.util.Builder;
import com.neura.standalonesdk.util.SDKUtils;
import java.util.ArrayList;
import android.util.Log;
import java.lang.String;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button button;
    Button checkPermissions;
    Context context;
    final String prefsData = "NeuraPrefs";

    ArrayList<Permission> mPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        button = (Button) findViewById(R.id.signIn);
        checkPermissions = (Button) findViewById(R.id.checkPermissions);

        NeuraApp.getInstance().initNeura(this);

        setSignInVisibility();
        setAction();
    }
    private boolean isSignedIn(){
        SharedPreferences settings = getSharedPreferences(prefsData, 0);
        return settings.getBoolean("isSignedIn", false);
    }

    private void setSignInVisibility(){
        if(isSignedIn()){
            button.setVisibility(View.GONE);
        }else{
            button.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        setSignInVisibility();
    }
    @Override
    protected void onResume(){
        super.onResume();
        setSignInVisibility();
    }
    private void NeuraAuthentication() {

        mPermissions = Permission.list(new String[]{"userStartedRunning", "userStartedWalking", "userFinishedRunning", "userFinishedWalking"});
        AuthenticationRequest request = new AuthenticationRequest(mPermissions);
        NeuraApp.getInstance().getClient().authenticate(request, new AuthenticateCallback() {
            @Override
            public void onSuccess(AuthenticateData authenticateData) {
                Log.i(getClass().getSimpleName(), "Successfully authenticate with neura. "
                        + "NeuraUserId = " + authenticateData.getNeuraUserId() + " "
                        + "AccessToken = " + authenticateData.getAccessToken());
                button.setVisibility(button.GONE);
                SharedPreferences settings = getSharedPreferences(prefsData, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("isSigned", true);
                editor.commit();
            }

            @Override
            public void onFailure(int i) {
                Log.e(getClass().getSimpleName(), "Failed to authenticate with neura. "
                        + "Reason : " + SDKUtils.errorCodeToString(i));
            }
        });
    }
    private void setAction() {
        /**boolean userWalking = SDKUtils.isConnected(this, mNeuraApiClient) &&
                !mNeuraApiClient.isMissingDataForEvent(mPermissions.get(0).getName());
        button.setText(getResources().getString(!SDKUtils.isConnected(this, mNeuraApiClient)
                ? R.string.neura_login : userWalking ? R.string.events_history : R.string.user_walking));
**/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeuraAuthentication();
            }
        });
        checkPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentPermissions fragmentPermissions = new FragmentPermissions();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.add(R.id.activity_main, fragmentPermissions);
//                fragmentTransaction.commit();

//                getSupportFragmentManager().beginTransaction().add(R.id.activity_main, fragmentPermissions).commit();
                Intent intent = new Intent();
                intent.setClass(context, Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
//        Fragment fragmentPermissions = new FragmentPermissions();
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.remove(fragmentPermissions);
//        fragmentTransaction.commit();

        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.fragment_permissions)).commit();
    }


}
