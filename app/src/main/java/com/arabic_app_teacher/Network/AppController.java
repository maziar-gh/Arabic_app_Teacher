package com.arabic_app_teacher.Network;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.arabic_app_teacher.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;



public class AppController  extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;


    public final static String URL = "http://iava.in/arabi/app/index.php/api/";

    public final static String URL_LOGIN= URL + "auth/loginostad";
    public final static String URL_SIGNUP_OSTAD= URL + "auth/signupostad";

    public final static String URL_ALL_USERS= URL + "student/";
    public final static String URL_DELETE_USERS= URL + "student/delete/";

    public final static String URL_CLASS_ADD= URL + "clas/add/";
    public final static String URL_CLASS_DETAIL= URL + "clas/detail/";
    public final static String URL_CLASS= URL + "clas/";


    public final static String URL_OSTAD= URL + "config/ostad";
    public final static String URL_COURCE= URL + "config/course";



    public final static String SAVE_LOGIN = "LOGIN";
    public final static String SAVE_USER_ID = "SAVE_USER_ID";
    public final static String SAVE_CLASS_ID = "SAVE_CLASS_ID";

    public final static String SAVE_USER_ID_FOR_SENDFILE = "SAVE_USER_ID_FOR_SENDFILE";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("IRANSansWeb_Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}