package co.edu.eci.ieti.android.network.service;

import co.edu.eci.ieti.android.data.entities.LoginWrapper;
import co.edu.eci.ieti.android.data.entities.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST( "/users/login" )
    Call<Token> login( @Body LoginWrapper loginWrapper );
}
