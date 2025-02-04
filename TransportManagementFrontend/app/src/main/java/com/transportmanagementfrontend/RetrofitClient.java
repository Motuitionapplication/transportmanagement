package com.transportmanagementfrontend;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost:8080/api/")
                    .addConverterFactory(GsonConverterFactory.create()) // Correct initialization here
                    .build();
        }
        return retrofit;
    }
}
