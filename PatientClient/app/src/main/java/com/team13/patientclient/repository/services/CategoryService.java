package com.team13.patientclient.repository.services;

import com.team13.patientclient.models.Category;
import com.team13.patientclient.repository.OnResponse;
import com.team13.patientclient.repository.RetrofitSingleton;
import com.team13.patientclient.repository.apis.CategoryApi;

public class CategoryService {
    CategoryApi api = RetrofitSingleton.getInstance().create(CategoryApi.class);
    public void getCategoryList (OnResponse<Category[]> callback ){api.get().enqueue(callback);}
}
