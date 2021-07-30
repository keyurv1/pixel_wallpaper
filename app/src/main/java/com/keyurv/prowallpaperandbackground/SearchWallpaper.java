package com.keyurv.prowallpaperandbackground;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchWallpaper extends AppCompatActivity {

    public int pagenumber = 1;
    RecyclerView recyclerView;
    ScrollView scrollView;
    WallpaperAdapter wallpaperAdapter;
    List<WallpaperModel> wallpaperModelList;
    String url ;
    ConstraintLayout layout;
    EditText searchname;
    CardView cardView;


    Boolean isscrolling = false;
    int currentItem, totalItem, scrollOutItem;
    ImageView search,searcImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_wallpaper);

        search = findViewById(R.id.search);
        searcImg = findViewById(R.id.searchImg);
        recyclerView = findViewById(R.id.recyclerview);
        wallpaperModelList = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(this, wallpaperModelList);
        searchname = findViewById(R.id.searchname);
        cardView = findViewById(R.id.cardView);

        recyclerView.setAdapter(wallpaperAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchWallpaper.class);
                startActivity(intent);
            }
        });

        searcImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query = searchname.getText().toString().toLowerCase();

                wallpaperModelList.clear();
                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=" + query,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i = 0; i < length; i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id, protraitUrl, mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                } catch (JSONException e) {

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Authorization", "563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);

               


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem + scrollOutItem == totalItem)) {
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=" + query,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i = 0; i < length; i++) {
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id, protraitUrl, mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            } catch (JSONException e) {

                                            }


                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("Authorization", "563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=" + query,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i = 0; i < length; i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id, protraitUrl, mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                } catch (JSONException e) {

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("Authorization", "563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);

            }
        });


    }







    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),WallpaperList.class);
        startActivity(intent);
    }
}