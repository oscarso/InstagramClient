package co.oscarsoft.instagramclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;

import co.oscarsoft.instagramclient.R;
import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    static final String RESTAPI_INSTAGRAM_CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    static final String RESTAPI_INSTAGRAM = "https://api.instagram.com/v1/media/popular?client_id=" +
            PhotosActivity.RESTAPI_INSTAGRAM_CLIENT_ID;
    private ArrayList<InstagramPhoto> arrayPhotos;
    private InstagramPhotosAdapter photosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // send out API request to get popular photos
        arrayPhotos = new ArrayList<>();
        photosAdapter = new InstagramPhotosAdapter(this, arrayPhotos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(photosAdapter);
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(PhotosActivity.RESTAPI_INSTAGRAM, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.i("INFO", response.toString());
                JSONArray jsonPhotos = null;
                try {
                    jsonPhotos = response.getJSONArray("data");
                    for (int i=0; i < jsonPhotos.length(); i++) {
                        JSONObject jsonPhoto = jsonPhotos.getJSONObject(i);
                        InstagramPhoto instaPhoto = new InstagramPhoto();
                        instaPhoto.username = jsonPhoto.getJSONObject("user").getString("username");
                        instaPhoto.caption = jsonPhoto.getJSONObject("caption").getString("text");
                        instaPhoto.imageUrl = jsonPhoto.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        instaPhoto.imageHeight = jsonPhoto.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        instaPhoto.likesCount = jsonPhoto.getJSONObject("likes").getInt("count");
                        arrayPhotos.add(instaPhoto);
                    }
                } catch (JSONException jsone) {
                    jsone.printStackTrace();
                }
                photosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", "Status Code: " + statusCode + " - " + responseString);
            }
        });

    }
}
