package com.yarsi.skripsi.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yarsi.skripsi.R;
import com.yarsi.skripsi.app.AppConfig;
import com.yarsi.skripsi.helper.SQLiteHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MedicationDataActivity extends AppCompatActivity {

    private InputStream mInputStream = null;
    private String mLine = null;

    private GridView gridView;
    private GridViewAdapter gridAdapter;

    JSONParser jsonParser = new JSONParser();
    private static final String MEDICATION_URL = new AppConfig().URL_GET_MEDICATION;

    private SQLiteHandler db;

    private ImageItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_data);

        db = new SQLiteHandler(this);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                item = (ImageItem) parent.getItemAtPosition(position);

                if (item.getId().equals("0")) {
                    showAlertDialog(v.getContext(), "Failed", "Tidak ada data", false);
                } else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:

                                    if (isInternetConnected(new AppConfig().con())) {
                                        delete(item.getIdTobeDeleted());
                                    } else {
                                        showAlertDialog(getBaseContext(), "Internet Connection",
                                                "Please check your internet connection", false);
                                    }

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder ab = new AlertDialog.Builder(v.getContext());
                    ab.setMessage("Are you sure want to delete?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
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
                    imageItems.add(new ImageItem(bitmap, mJsonArray.getJSONObject(i).getString("Medication_Name"),
                            mJsonArray.getJSONObject(i).getString("Time") + " (" + mJsonArray.getJSONObject(i).getString("Repeat_Every") + "x)"
                            , mJsonArray.getJSONObject(i).getString("ID")));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            showAlertDialog(this, "Internet Connection",
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
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
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
        ConnectivityManager mConnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

        if (message.equals("Delete Success")) {
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getBaseContext(), MedicationDataActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, 0);
                }
            });
        } else {
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }

        alertDialog.show();
    }

    public void delete(String id) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("id", id));

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(new AppConfig().URL_DELETE_MEDICATION);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            mInputStream = entity.getContent();
            Log.e("Pass 1", "Connection Success");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            showAlertDialog(this, "Failed", "Connection Failed", false);
        }

        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(mInputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while ((mLine = reader.readLine()) != null) {
                sb.append(mLine + "\n");
            }
            mInputStream.close();
            Log.e("Pass 2", "Connection Success");
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }

        showAlertDialog(this, "Success", "Delete Success", true);
    }
}
