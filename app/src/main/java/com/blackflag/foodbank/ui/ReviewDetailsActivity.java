package com.blackflag.foodbank.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blackflag.foodbank.R;
import com.blackflag.foodbank.model.App;
import com.squareup.picasso.Picasso;

public class ReviewDetailsActivity extends AppCompatActivity {

    TextView place;
    TextView authorname;
    TextView offer;
    TextView price;
    TextView foodName;
    TextView comment;
    TextView time;

    ImageView food_image;
    Button post;

    RatingBar servicerating,testRating,placeRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        food_image= (ImageView) findViewById(R.id.food_image);
        place= (TextView) findViewById(R.id.place);
        authorname= (TextView) findViewById(R.id.authorname);
        offer= (TextView) findViewById(R.id.offer);
        price= (TextView) findViewById(R.id.price);
        foodName= (TextView) findViewById(R.id.food_name);
        comment= (TextView) findViewById(R.id.cooment);
        time= (TextView) findViewById(R.id.time);

        servicerating= (RatingBar) findViewById(R.id.serviceRating);
        testRating= (RatingBar) findViewById(R.id.testRating);
        placeRating= (RatingBar) findViewById(R.id.placeRating);

        int pos=getIntent().getExtras().getInt(App.position);

        Picasso.with(this)
                .load(App.reviewList.get(pos).getImageLink())
                .error(R.drawable.erroer_img)
                .into(food_image);
        place.setText(App.reviewList.get(pos).getResturentNname());
        authorname.setText(App.reviewList.get(pos).getAuthorName());
        offer.setText(App.reviewList.get(pos).getSpecialOffer());
        price.setText(App.reviewList.get(pos).getFoodPrice());
        foodName.setText(App.reviewList.get(pos).getFoodNname());
        comment.setText(App.reviewList.get(pos).getComment());
        time.setText(App.reviewList.get(pos).getPostTime());

        servicerating.setRating(Float.valueOf(App.reviewList.get(pos).getServiceReview()));
        testRating.setRating(Float.valueOf(App.reviewList.get(pos).getTestReview()));
        placeRating.setRating(Float.valueOf(App.reviewList.get(pos).getPlaceReview()));




    }
}
