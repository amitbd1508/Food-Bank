package com.blackflag.foodbank.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.blackflag.foodbank.R;
import com.blackflag.foodbank.model.App;
import com.blackflag.foodbank.model.FoodReview;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class NewReviewActivity extends AppCompatActivity {

    private int PICK_IMAGE_REQUEST = 1;


    private String UPLOAD_URL ="http://iftiarahmed.com/hackathon/foodbank/fileUpload.php";
    private Bitmap bitmap;

    private String KEY_author_name = "author_name";
    private String KEY_place = "place";
    private String KEY_offer = "offer";
    private String KEY_price = "price";
    private String KEY_food_name = "food_name";
    private String KEY_food_image = "food_image";
    private String KEY_comment = "comment";
    private String KEY_service_rating = "service_rating";
    private String KEY_test_rating = "test_rating";
    private String KEY_place_rating = "place_rating";
    private String KEY_post_time = "post_time";



    EditText place;
    EditText offer;
    EditText price;
    EditText foodName;
    EditText comment;

    Button post;

    RatingBar servicerating,testRating,placeRating;




    ImageView food_image;

    String getUser()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return preferences.getString(App.username,"Anonymous");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        food_image= (ImageView) findViewById(R.id.food_image);
        food_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
        place= (EditText) findViewById(R.id.place);
        offer= (EditText) findViewById(R.id.offer);
        price= (EditText) findViewById(R.id.price);
        foodName= (EditText) findViewById(R.id.food_name);
        comment= (EditText) findViewById(R.id.cooment);

        servicerating= (RatingBar) findViewById(R.id.serviceRating);
        testRating= (RatingBar) findViewById(R.id.testRating);
        placeRating= (RatingBar) findViewById(R.id.placeRating);
        post= (Button) findViewById(R.id.post);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });


    }

    void post()
    {

        FoodReview newfood=new FoodReview();
        if (stringValidation(place.getText().toString())
                &&stringValidation(price.getText().toString())
                &&stringValidation(foodName.getText().toString())
                )
        {

            if(comment.getText().toString().equals("")||comment.getText().toString().equals(null))
            {
                newfood.setComment(getString(R.string.no_comment));
            }
            else
                newfood.setComment(comment.getText().toString());
            if(offer.getText().toString().equals("")||offer.getText().toString().equals(null))
            {
                newfood.setSpecialOffer("No Offer");
            }
            else newfood.setSpecialOffer(offer.getText().toString());
            newfood.setAuthorName(getUser());
            newfood.setFoodNname(foodName.getText().toString());
            newfood.setPostTime(getCurrentTimeStamp());
            newfood.setFoodPrice(price.getText().toString());
            newfood.setSpecialOffer(offer.getText().toString());
            newfood.setTestReview(String.valueOf(testRating.getRating()));
            newfood.setServiceReview(String.valueOf(servicerating.getRating()));
            newfood.setPlaceReview(String.valueOf(placeRating.getRating()));
            newfood.setResturentNname(place.getText().toString());


            uploadPost(newfood);



        }
        else Toast.makeText(this, "Fill up required field", Toast.LENGTH_SHORT).show();
    }
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    boolean stringValidation(String s)
    {
        if(s.equals("")
                || s.equals(null)
                ||s==null
                ||s.length()<1
                )
        {
            return false;
        }

        return true;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    private void uploadPost(final FoodReview foodReview){


        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(getApplicationContext(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getApplicationContext(), R.string.upload_faild, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                       // Log.e("Error",volleyError.getMessage().toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
               String image = getStringImage();

                //Getting Image Name


                Log.d("all",foodReview.getComment()+" "+foodReview.getResturentNname());



                String name = foodReview.getAuthorName();
                name.replaceAll(" ","%20");
                String offer = foodReview.getSpecialOffer();
                offer.replaceAll(" ","%20");

                String comment = foodReview.getComment();
               comment.replaceAll(" ","%20");



                String place = foodReview.getResturentNname();
                place.replaceAll("","%20");


                String foodname = foodReview.getFoodNname();
                foodname.replaceAll(" ","%20");
                String price = foodReview.getFoodPrice();
                price.replaceAll(" ","%20");

                String servicerat = foodReview.getServiceReview();
                servicerat.replaceAll(" ","%20");

                String testRating = foodReview.getTestReview();
                testRating.replaceAll(" ","%20");

                String placerating = foodReview.getPlaceReview();
                placerating.replaceAll(" ","%20");

                String time = foodReview.getPostTime();
                time.replaceAll(" ","%20");




                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                 params.put(KEY_food_image, image);

                params.put(KEY_author_name, name);
                params.put(KEY_place, place);
                params.put(KEY_comment, comment);
                params.put(KEY_offer, offer);
                params.put(KEY_price ,price );
                params.put(KEY_food_name , foodname);
                params.put(KEY_service_rating ,servicerat );
                params.put(KEY_post_time ,time );
                params.put(KEY_test_rating ,testRating );
                params.put(KEY_place_rating ,placerating );

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    public String getStringImage(){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;

        BitmapDrawable drawable = (BitmapDrawable) food_image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] image=stream.toByteArray();


        String img_str = Base64.encodeToString(image, Base64.DEFAULT);
        return  img_str;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                food_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
