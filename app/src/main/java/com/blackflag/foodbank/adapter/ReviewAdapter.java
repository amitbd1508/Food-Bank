package com.blackflag.foodbank.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blackflag.foodbank.R;
import com.blackflag.foodbank.model.App;
import com.blackflag.foodbank.model.FoodReview;
import com.blackflag.foodbank.ui.ReviewDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by BlackFlag on 10/12/2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<FoodReview>foodReviews;

    public ReviewAdapter(List<FoodReview> foodReviews, Context context) {
        this.foodReviews = foodReviews;
        this.context = context;
    }

    private Context context;
    private int lastPosition = -1;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_review, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        setAnimation(holder.container, position);
        holder.place.setText(foodReviews.get(position).getResturentNname());
        holder.author_name.setText(foodReviews.get(position).getAuthorName());
        holder.food_name.setText(foodReviews.get(position).getFoodNname());
        holder.food_price.setText("TK "+foodReviews.get(position).getFoodPrice());
        holder.rating.setRating(getAvgReview(position));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ReviewDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(App.position,position);
                context.startActivity(intent);
            }
        });

        Picasso.with(context)
                .load(foodReviews.get(position).getImageLink())
                .error(R.drawable.erroer_img)
                .into(holder.food_image);
        Log.d("Image",foodReviews.get(position).getImageLink());

    }

    private float getAvgReview(int position) {
        try {
            Float service,test,place;
            service=Float.valueOf(foodReviews.get(position).getServiceReview());
            test=Float.valueOf(foodReviews.get(position).getTestReview());
            place=Float.valueOf(foodReviews.get(position).getPlaceReview());
            Float total=service+test+place;
            return (float) (total/3.0);

            //return Float.valueOf(foodReviews.get(position).getTestReview()); //next time
        }catch (Exception e)
        {
            Log.e("Adapter_concersion",e.getMessage());
        }
        return 0;

    }

    @Override
    public int getItemCount() {
        return foodReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView food_name;
        TextView food_price;
        TextView author_name;
        TextView place;
        RatingBar rating;
        ImageView food_image;


        CardView container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = (CardView) itemView.findViewById(R.id.container);

            //locationFrom = (TextView) itemView.findViewById(R.id.tv_from_trip_history);
            food_name= (TextView) itemView.findViewById(R.id.food_name);
            place= (TextView) itemView.findViewById(R.id.place);
            author_name= (TextView) itemView.findViewById(R.id.user_first_name);
            food_price= (TextView) itemView.findViewById(R.id.food_price);
            rating= (RatingBar) itemView.findViewById(R.id.ratingBar);
            food_image= (ImageView) itemView.findViewById(R.id.food_image);



        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
