package com.fiaformulae.wayfinder.network;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class WayfinderInterceptor implements Interceptor {

  @Override public Response intercept(Chain chain) throws IOException {
    Request originalRequest = chain.request();
    Request.Builder builder = originalRequest.newBuilder();
    originalRequest = builder.build();
    return chain.proceed(originalRequest);
  }
}
