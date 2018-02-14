package com.blackflag.foodbank.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BlackFlag on 10/12/2016.
 */

public class App {
    public static String username="username";
    public  static List<FoodReview> reviewList;
    public static void init(){
        reviewList=new ArrayList<FoodReview>();
    }
    public static String UserLogIn="login";
    public static String position="position";

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
