package co.edu.eci.ieti.android.network;

import java.io.IOException;

import co.edu.eci.ieti.android.network.service.AuthService;
import co.edu.eci.ieti.android.network.service.TaskService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetwork {

    private AuthService authService;
    private TaskService taskService;
    private final static String BASE_URL = "https://taskplanner-apirest.herokuapp.com";

    public RetrofitNetwork(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL) //localhost for emulator
            .addConverterFactory( GsonConverterFactory.create() ).build();

        authService = retrofit.create( AuthService.class );
    }

    public AuthService getAuthService()
    {
        return authService;
    }

    public RetrofitNetwork( final String token ){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( new Interceptor(){
            @Override
            public okhttp3.Response intercept( Chain chain )throws IOException{
                Request original = chain.request();
                Request request = original.newBuilder().
                        header( "Accept", "application/json" ).header( "Authorization",
                        "Bearer "+ token ).method(
                        original.method(), original.body() ).build();
                return chain.proceed( request );
            }
        } );
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl( BASE_URL ).addConverterFactory( GsonConverterFactory.create() ).client(
                        httpClient.build() ).build();
        taskService = retrofit.create(TaskService.class);
    }

    public TaskService getTaskService(){
        return taskService;
    }


}