package com.lab4.demo;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String BOOKSTORE = API_PATH + "/bookstore";
    public static final String ENTITY = "/{id}";
    public static final String EXPORT_REPORT = "/export/{type}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String USERS = API_PATH + "/users";

    public static final String BOOKS = BOOKSTORE + "/books";
    public static final String SELL = "/sell";
    public static final String SEARCH = "/search";

}
