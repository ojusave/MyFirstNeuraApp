package neura.myfirstneuraapp;

import android.content.Context;

import com.neura.standalonesdk.service.NeuraApiClient;
import com.neura.standalonesdk.util.Builder;

/**
 * Created by ojusa_000 on 10/20/2016.
 */

public class NeuraApp {

    private static NeuraApp instance = new NeuraApp();

    private NeuraApiClient apiClient;

    private NeuraApp() {}

    static NeuraApp getInstance() {
        return instance;
    }
    //
    public NeuraApiClient getClient() {
        return apiClient;
    }

    public void initNeura(Context context){
        Builder builder = new Builder(context);
        apiClient = builder.build();
        apiClient.setAppUid(context.getResources().getString(R.string.app_uid));
        apiClient.setAppSecret(context.getResources().getString(R.string.app_secret));
        apiClient.connect();
    }
}
