package com.yarsi.skripsi.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yarsi.skripsi.R;
import com.yarsi.skripsi.activity.GridViewAdapter;
import com.yarsi.skripsi.activity.ImageItem;
import com.yarsi.skripsi.activity.JSONParser;
import com.yarsi.skripsi.app.AppConfig;
import com.yarsi.skripsi.helper.SQLiteHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MedicationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedicationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MedicationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicationFragment newInstance(String param1, String param2) {
        MedicationFragment fragment = new MedicationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    JSONParser jsonParser = new JSONParser();
    private static final String MEDICATION_URL = new AppConfig().URL_GET_MEDICATION;

    private SQLiteHandler db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_medication, container, false);

        db = new SQLiteHandler(rootView.getContext());

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(rootView.getContext(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

//                if (item.getId().equals("0")) {
//                    showAlertDialog(v.getContext(), "Failed", "Stock barang habis", false);
//                } else {
//                    Intent intent = new Intent(v.getContext(), DescriptionActivity.class);
//                    intent.putExtra("title", item.getTitle());
//                    intent.putExtra("tipe", "Dijual");
//
//                    startActivity(intent);
//                }
            }
        });

        return rootView;
    }

    /**
     * Prepare some dummy data for gridview
     */
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        if (isInternetConnected(new AppConfig().con())) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                // Fetching user details from sqlite
                HashMap<String, String> user = db.getUserDetails();

                String username = user.get("name");

                params.add(new BasicNameValuePair("username", username));

                JSONObject json = jsonParser.makeHttpRequest(
                        MEDICATION_URL, "POST", params);

                JSONArray mJsonArray = json.getJSONArray("medication");

                for (int i = 0; i < mJsonArray.length(); i++) {
                    Bitmap bitmap = capturedImage(mJsonArray.getJSONObject(i).getString("Image"));
                    imageItems.add(new ImageItem(bitmap, mJsonArray.getJSONObject(i).getString("Medication_Name"), mJsonArray.getJSONObject(i).getString("Time")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showAlertDialog(getContext(), "Internet Connection",
                    "Please check your internet connection", false);
        }

        return imageItems;
    }

    private Uri fileUri;

    private Bitmap capturedImage(String path) {
        try {
            // bitmap factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(path,
                    options);

            return bitmap;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Bitmap galeryImage(Uri path) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isInternetConnected(String mUrl) {
        final int CONNECTION_TIMEOUT = 5000;
        if (isNetworksAvailable()) {
            try {
                HttpURLConnection mURLConnection = (HttpURLConnection) (new URL(mUrl).openConnection());
                mURLConnection.setRequestProperty("User-Agent", "ConnectionTest");
                mURLConnection.setRequestProperty("Connection", "close");
                mURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                mURLConnection.setReadTimeout(CONNECTION_TIMEOUT);
                mURLConnection.connect();
                return (mURLConnection.getResponseCode() == 200);
            } catch (IOException ioe) {
                Log.e("isInternetConnected", "Exception occured while checking for Internet connection: ", ioe);
            }
        } else {
            Log.e("isInternetConnected", "Not connected to WiFi/Mobile and no Internet available.");
        }
        return false;
    }

    public boolean isNetworksAvailable() {
        ConnectivityManager mConnMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMgr != null) {
            NetworkInfo[] mNetInfo = mConnMgr.getAllNetworkInfo();
            if (mNetInfo != null) {
                for (int i = 0; i < mNetInfo.length; i++) {
                    if (mNetInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
