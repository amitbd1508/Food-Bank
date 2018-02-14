package com.blackflag.foodbank.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blackflag.foodbank.R;
import com.blackflag.foodbank.adapter.ReviewAdapter;
import com.blackflag.foodbank.model.App;
import com.blackflag.foodbank.model.FoodReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment {

    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    private SwipeRefreshLayout swipeContainer;


    private List<FoodReview> foodReviews = new ArrayList<FoodReview>();
    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        reviewAdapter=new ReviewAdapter(foodReviews,getActivity().getApplicationContext());
        recyclerView= (RecyclerView) view.findViewById(R.id.review_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reviewAdapter);
        new AddAllItemAsyn(getActivity().getApplicationContext()).execute();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    public void fetchTimelineAsync(int page) {
        foodReviews.clear();
        App.reviewList.clear();
        new AddAllItemAsyn(getActivity().getApplicationContext()).execute();




    }

    private void addItems() {
        String url = "http://iftiarahmed.com/hackathon/foodbank/getallreview.php";

        StringRequest sr = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                FoodReview food = new FoodReview();
                                food.setAuthorName(jsonObject.getString("author_name"));
                                food.setResturentNname(jsonObject.getString("place"));
                                food.setSpecialOffer(jsonObject.getString("offer"));
                                food.setFoodPrice(jsonObject.getString("price"));
                                food.setImageLink(jsonObject.getString("food_image"));
                                food.setComment(jsonObject.getString("comment"));
                                food.setServiceReview(jsonObject.getString("service_rating"));
                                food.setTestReview(jsonObject.getString("test_rating"));
                                food.setPlaceReview(jsonObject.getString("place_rating"));
                                food.setPostTime(jsonObject.getString("post_time"));
                                food.setFoodNname(jsonObject.getString("food_name"));

                                foodReviews.add(food);
                                App.reviewList.add(food);
                                reviewAdapter.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());
        rq.add(sr);
    }


    class  AddAllItemAsyn extends AsyncTask<Void,Void,Void> {

        Context context;

        public AddAllItemAsyn(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {




            String url = "http://iftiarahmed.com/hackathon/foodbank/getallreview.php";

            StringRequest sr = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    FoodReview food = new FoodReview();
                                    food.setAuthorName(jsonObject.getString("author_name"));
                                    food.setResturentNname(jsonObject.getString("place"));
                                    food.setSpecialOffer(jsonObject.getString("offer"));
                                    food.setFoodPrice(jsonObject.getString("price"));
                                    food.setImageLink(jsonObject.getString("food_image"));
                                    food.setComment(jsonObject.getString("comment"));
                                    food.setServiceReview(jsonObject.getString("service_rating"));
                                    food.setTestReview(jsonObject.getString("test_rating"));
                                    food.setPlaceReview(jsonObject.getString("place_rating"));
                                    food.setPostTime(jsonObject.getString("post_time"));
                                    food.setFoodNname(jsonObject.getString("food_name"));

                                    foodReviews.add(food);
                                    App.reviewList.add(food);
                                    reviewAdapter.notifyDataSetChanged();
                                }


                            } catch (JSONException e) {

                            }
                            swipeContainer.setRefreshing(false);


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue rq = Volley.newRequestQueue(context);
            rq.add(sr);
            return null;
        }
    }
}


