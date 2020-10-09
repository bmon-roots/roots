package ar.edu.ort.bmon.rootsapp.service;

import java.util.List;

import ar.edu.ort.bmon.rootsapp.model.Especie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface APIEspecieService {

//    @Headers("user-key: 9900a9720d31dfd5fdb4352700c")
    @GET("/especies")
    Call<List<Especie>> getAllEspecies(@Header("user-key") String userKey);

}
