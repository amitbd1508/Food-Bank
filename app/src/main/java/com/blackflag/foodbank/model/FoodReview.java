package com.blackflag.foodbank.model;

/**
 * Created by BlackFlag on 10/12/2016.
 */

public class FoodReview {
    private String authorName;
    private String resturentNname;
    private String foodNname;
    private String specialOffer;
    private String foodPrice;
    private String postTime;
    private String comment;
    private String serviceReview;
    private String testReview;
    private String placeReview;
    private String imageLink;

    public FoodReview() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getResturentNname() {
        return resturentNname;
    }

    public void setResturentNname(String resturentNname) {
        this.resturentNname = resturentNname;
    }

    public String getFoodNname() {
        return foodNname;
    }

    public void setFoodNname(String foodNname) {
        this.foodNname = foodNname;
    }

    public String getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(String specialOffer) {
        this.specialOffer = specialOffer;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getServiceReview() {
        return serviceReview;
    }

    public void setServiceReview(String serviceReview) {
        this.serviceReview = serviceReview;
    }

    public String getTestReview() {
        return testReview;
    }

    public void setTestReview(String testReview) {
        this.testReview = testReview;
    }

    public String getPlaceReview() {
        return placeReview;
    }

    public void setPlaceReview(String placeReview) {
        this.placeReview = placeReview;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
