package com.team13.doctorclient.repositories.apis;

import com.team13.doctorclient.models.PatientPageModel;
import com.team13.doctorclient.models.Treatment;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PatientApi {
    @GET("/treatments?populate=patient+service+prescription+prescription.medicine_list.medicine")
    Call<Treatment[]> getMedicalHistory(@Query("patient_id") String patientId);

    @GET("patients?per_page=15")
    Call<PatientPageModel> getPatientsByPage(@Query("page") int page);

    @GET("patients?per_page=15")
    Call<PatientPageModel> searchPatients(@Query("search") String searchKey, @Query("page") int page );

    @POST("/patients/cancel/{id}")
    @FormUrlEncoded
    Call<Void> removeCurrentAppointment(@Path("id") String id, @Field("appointment_id") String appointmentId);

    @GET("/users/phone/{phone}")
    Call<Map<String,String>> getByPhoneNum(@Path("phone") String phone);
}
