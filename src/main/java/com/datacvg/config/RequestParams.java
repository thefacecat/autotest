package com.datacvg.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

public class RequestParams {

    public static String userId;
    public static String token;
    public static CookieStore cookieStore = new BasicCookieStore();

}
