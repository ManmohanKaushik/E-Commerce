package com.bikkadit.electronicstore.constants;

public class UriConstants {
    public static final String ORDER_URI = "/apiOr";
    public static final String CREATE_ORDER = "/order/{userId}/{cartId}";
    public static final String REMOVE_ORDER = "/order/{orderId}";
    public static final String GET_ORDER_BY_ID = "/order/{userId}";
    public static final String AUTH_ROLE = "hasRole('ADMIN')";
    public static final String GET_ALL_ORDER = "/orders";
    public static final String USER_URI = "/api";
    public static final String SAVE_USER = "/user";
    public static final String UPDATE_USER = "/user/{userId}";
    public static final String GET_USER_BY_ID = "/user/{userId}";
    public static final String GET_USER_BY_EMAIL = "/userEmail/{email}";
    public static final String GET_ALL_USER = "/userAll";
    public static final String DELETE_USER = "/user/{userId}";
    public static final String SEARCH_USER = "/search/{keyword}";
    public static final String UPLOAD_USER_IMAGE = "/image/{userId}";
    public static final String SERVER_USER_IMAGE = "/image/{userId}";
    public static final String  CATEGORY_URI="/apiCat";
    public static final String CREATE_CATEGORY="/category";
    public static final String UPDATE_CATEGORY="/category/{categoryId}";
    public static final String DELETE_CATEGORY="/category/{categoryId}";
   public static final String GET_CATEGORY_BY_ID="/category/{categoryId}";
   public static final String GET_ALL_CATEGORY="/category";
    public static final String  PRODUCT_URI="/apiPro";
    public static final String CREATE_PRODUCT="/product";
    public static final String UPDATE_PRODUCT="/product/{productId}";
    public static final String GET_PRODUCT_BY_ID="/product/{productId}";
    public static final String GET_ALL_PRODUCT="/products";
    public static final String DELETE_PRODUCT="/product/{productId}";
    public static final String GET_LIVE="/live";
    public static final String SEARCH_TITLE="/search";
    public static final String UPLOAD_PRODUCT_IMAGE="/image/{productId}";
    public static final String SERVER_PRODUCT_IMAGE="/image/{productId}";
    public static final String CREATE_WITH_CATEGORY="/products/{categoryId}/products";
    public static final String UPDATE_WITH_CATEGORY="/products/{categoryId}/products/{productId}";
    public static final String GET_ALL_PRODUCT_OF_CATEGORY="/getPro/{categoryId}";
    public static final String CART_URI="/apiCarts";
    public static final String ADD_CART="/cart/{userId}";
    public static final String REMOVE_CART_ITEM="/cart/{userId}/item/{itemId}";
    public static final String CLEAR_CART="/cart/{userId}";
    public static final String GET_CART_BY_USER ="/cart/{userId}";
    public static final String AUTH_URI="/auth";
    public static final String AUTH_LOGIN="/login";
    public static final String GET_CURRENT_USER="/current";


}
