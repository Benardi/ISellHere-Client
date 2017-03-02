package com.example.grupoes.projetoes.util;

/**
 * Created by Wesley on 19/02/2017.
*/

public class FrontendConstants {
    /* Request URLs */
    public static final String SOURCE_REQUEST_URL = "https://isellhere.herokuapp.com";
    //public static final String SOURCE_REQUEST_URL = "http://192.168.2.107:8080";
    public static final String SERVER_REQUEST_URL = SOURCE_REQUEST_URL + "/server";

    public static final String AUTHENTICATION_REQUEST_URL = SERVER_REQUEST_URL + "/authentication";
    public static final String LOGIN_REQUEST_URL = AUTHENTICATION_REQUEST_URL + "/login";

    public static final String USERS_REQUEST_URL = SERVER_REQUEST_URL + "/users";
    public static final String SIGNUP_REQUEST_URL = USERS_REQUEST_URL + "/new";
    public static final String EDIT_USER_REQUEST_URL = USERS_REQUEST_URL + "/edit";

    public static final String POINT_OF_SALE_REQUEST_URL = SERVER_REQUEST_URL + "/pointofsale";
    public static final String ADD_POINT_OF_SALE_REQUEST_URL = POINT_OF_SALE_REQUEST_URL + "/new";
    public static final String EDIT_POINT_OF_SALE_REQUEST_URL = POINT_OF_SALE_REQUEST_URL + "/edit";
    public static final String DELETE_POINT_OF_SALE_REQUEST_URL = POINT_OF_SALE_REQUEST_URL + "/delete";
    public static final String GET_ALL_POINTS_OF_SALE_REQUEST_URL = POINT_OF_SALE_REQUEST_URL + "/getAllPoints";

    public static final String PRODUCT_REQUEST_URL = SERVER_REQUEST_URL + "/product";
    public static final String ADD_PRODUCT_REQUEST_URL = PRODUCT_REQUEST_URL + "/new";
    public static final String EDIT_PRODUCT_REQUEST_URL = PRODUCT_REQUEST_URL + "/edit";
    public static final String DELETE_PRODUCT_REQUEST_URL = PRODUCT_REQUEST_URL + "/delete";
    public static final String GET_PRODUCTS_REQUEST_URL = PRODUCT_REQUEST_URL + "/getProducts";

    public static final String SEARCH_REQUEST_URL = SERVER_REQUEST_URL + "/search";
    public static final String SEARCH_POINTOFSALE_URL = SEARCH_REQUEST_URL + "/searchpoint";
    public static final String SEARCH_PRODUCT_URL = SEARCH_REQUEST_URL + "/searchproductgeneral";
}
