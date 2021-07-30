package com.keyurv.prowallpaperandbackground;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
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

public class CategoriesWallpaper extends AppCompatActivity {

    TextView wallpaperList,popularWallpaper,recentWallpaper,category;

    ImageView search,abs,alone,animal,anime,art,bird,beach,black,bike,car,city,fantasy,flower,food,feathers,god,love,macro,nature,others,predators,space,sport,texture,vector,words;
    CardView recyclerViewCardView,categoryCardView;
    RecyclerView recyclerView;



    WallpaperAdapter wallpaperAdapter;
    List<WallpaperModel> wallpaperModelList;

    int pagenumber = 1;

    Boolean isscrolling = false;
    int currentItem, totalItem, scrollOutItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_wallpaper);


        wallpaperList = findViewById(R.id.wallpaperlist);
        popularWallpaper = findViewById(R.id.popularWallpaper);
        recentWallpaper = findViewById(R.id.recentWallpaper);
        category = findViewById(R.id.categories);
        search = findViewById(R.id.search);
        recyclerView = findViewById(R.id.recyclerview);

        abs = findViewById(R.id.abs);
        alone = findViewById(R.id.alone);
        animal = findViewById(R.id.animal);
        anime = findViewById(R.id.anime);
        art = findViewById(R.id.art);
        bird = findViewById(R.id.bird);
        beach = findViewById(R.id.beach);
        black = findViewById(R.id.black);
        bike = findViewById(R.id.bike);
        car = findViewById(R.id.car);
        city = findViewById(R.id.city);
        fantasy = findViewById(R.id.fantasy);
        flower = findViewById(R.id.flower);
        food = findViewById(R.id.food);
        feathers = findViewById(R.id.feathers);
        god = findViewById(R.id.god);
        love = findViewById(R.id.love);
        macro  = findViewById(R.id.macro);
        nature = findViewById(R.id.nature);
        others = findViewById(R.id.others);
        predators = findViewById(R.id.predators);
        space = findViewById(R.id.space);
        sport = findViewById(R.id.sport);
        texture = findViewById(R.id.texture);
        vector = findViewById(R.id.vector);
        words = findViewById(R.id.words);


        categoryCardView = findViewById(R.id.categoryCardView);

        wallpaperModelList = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(this, wallpaperModelList);

        recyclerView.setAdapter(wallpaperAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        wallpaperList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WallpaperList.class);
                startActivity(intent);
            }
        });

        popularWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PopularWallpaper.class);
                startActivity(intent);
            }
        });

        recentWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RecentWallpaper.class);
                startActivity(intent);
            }
        });



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SearchWallpaper.class);
                startActivity(intent);
            }
        });

        abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=abstract" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=abstract" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=abstract",
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

        alone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=alone" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=alone" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=alone",
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

        animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=animal" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=animal" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=animal",
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


        anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=anime" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=anime" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=anime",
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


        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=art" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=art" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=art",
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

        bird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=bird" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=bird" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=bird",
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



        beach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=beach" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=beach" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=beach",
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


        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=black" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=black" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=black",
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

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=bike" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=bike" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=bike",
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


  car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=car" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=car" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=car",
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


       city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=city" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=city" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=city",
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


        fantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=fantasy" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=fantasy" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=fantasy",
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



        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=flower" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=flower" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=flower",
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

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=food" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=food" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=food",
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

        feathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=feather" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=feather" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=feather",
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

       god.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=hindugod" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=hindugod" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=hindugod",
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

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=love" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=love" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=love",
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


       macro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=macro" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=macro" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=macro",
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


       nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=nature" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=nature" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=nature",
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


        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=3d" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=3d" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=3d",
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


       predators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=predator" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=predator" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=predator",
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


        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=space" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=space" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=space",
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


        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=sport" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=sport" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=sport",
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


        texture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=texture" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=texture" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=texture",
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


        vector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=vector" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=vector" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=vector",
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


        words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryCardView.setVisibility(View.GONE);


                recyclerView.setVisibility(View.VISIBLE);
                wallpaperModelList.clear();

                StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=words" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                    int length = jsonArray.length();

                                    for (int i=0; i<length;i++){
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        int id = object.getInt("id");

                                        JSONObject objectImages = object.getJSONObject("src");

                                        String protraitUrl = objectImages.getString("portrait");
                                        String mediuUrl = objectImages.getString("medium");

                                        WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                        wallpaperModelList.add(wallpaperModel);
                                    }

                                    wallpaperAdapter.notifyDataSetChanged();
                                    pagenumber++;

                                }catch (JSONException e)
                                {

                                }



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                            isscrolling = true;
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        currentItem = gridLayoutManager.getChildCount();
                        totalItem = gridLayoutManager.getItemCount();
                        scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();

                        if (isscrolling && (currentItem+scrollOutItem==totalItem)){
                            isscrolling = false;
                            StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page="+pagenumber+"&per_page=80&query=words" ,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("photos");

                                                int length = jsonArray.length();

                                                for (int i=0; i<length;i++){
                                                    JSONObject object = jsonArray.getJSONObject(i);

                                                    int id = object.getInt("id");

                                                    JSONObject objectImages = object.getJSONObject("src");

                                                    String protraitUrl = objectImages.getString("portrait");
                                                    String mediuUrl = objectImages.getString("medium");

                                                    WallpaperModel wallpaperModel = new WallpaperModel(id,protraitUrl,mediuUrl);
                                                    wallpaperModelList.add(wallpaperModel);
                                                }

                                                wallpaperAdapter.notifyDataSetChanged();
                                                pagenumber++;

                                            }catch (JSONException e)
                                            {

                                            }



                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String,String> params = new HashMap<>();
                                    params.put("Authorization","563492ad6f91700001000001edd548c9a64d4c71afe37f8425fc3910");

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(request);

                        }

                    }


                });

                request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search/?page=" + pagenumber + "&per_page=80&query=words",
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
        Intent intent = new Intent(getApplicationContext(),CategoriesWallpaper.class);
        startActivity(intent);
    }
}