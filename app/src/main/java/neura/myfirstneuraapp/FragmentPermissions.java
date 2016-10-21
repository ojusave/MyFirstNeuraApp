package neura.myfirstneuraapp;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.neura.sdk.callbacks.GetPermissionsRequestCallbacks;
import com.neura.sdk.object.Permission;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by ojusa_000 on 10/20/2016.
 */

public class FragmentPermissions extends Fragment{ // implements OnItemClickListener {

    private ArrayList<Permission> permissions = new ArrayList<>();

    private void getPermissions(){
        NeuraApp.getInstance().getClient().getAppPermissions(new GetPermissionsRequestCallbacks() {
            @Override
            public void onSuccess(final List<Permission> perms) throws RemoteException {
                if (getActivity() == null)
                    return;
                getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    permissions.addAll(perms);
                                                }
                                            });

            }

            @Override
            public void onFailure(Bundle resultData, int errorCode) throws RemoteException {
                Toast.makeText(getActivity(), "No permissions available", Toast.LENGTH_SHORT).show();
            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getPermissions();
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_perms_layout, container, false);
        ListView listView = (ListView) view.findViewById(R.id.permissions);
        ArrayAdapter<Permission> adapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, permissions);
        listView.setAdapter(adapter);
        return view;
    }
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getPermissions();
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        ArrayAdapter<Permission> adapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, permissions);
//        setListAdapter(adapter);
//    }
    public FragmentPermissions() {
        // Required empty public constructor
    }



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_perms_layout, container, false);
//        ArrayAdapter<Permission> adapter= new ArrayAdapter<>()
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);
//        setListAdapter(adapter);
//        getListView().setOnItemClickListener(this);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
////        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
//    }

}
