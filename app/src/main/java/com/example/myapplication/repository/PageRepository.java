package com.example.myapplication.repository;

import com.example.myapplication.MyApplication;
import com.example.myapplication.domain.Pet;
import com.example.myapplication.domain.PetType;
import com.example.myapplication.net.model.DataItem;
import com.example.myapplication.net.model.PetsResponse;
import com.example.myapplication.vm.PageViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageRepository {

    private List<Pet> pets = Collections.emptyList();
    private int id;

    public PageRepository(int id) {
        this.id = id;
    }

    public void fetchPets(PageViewModel.DataLoadingCallback callback) {
        if (!pets.isEmpty()) {
            callback.onLoaded(pets);
            return;
        }
        Call<PetsResponse> call;
        if (id == PetType.CAT.getId()) {
            call = MyApplication.getApi().getPets(PetType.CAT.getName());
        } else if (id == PetType.DOG.getId()) {
            call = MyApplication.getApi().getPets(PetType.DOG.getName());
        } else {
            throw new IllegalStateException("Unknown id of pet: " + id);
        }
        call.enqueue(new Callback<PetsResponse>() {
            @Override
            public void onResponse(Call<PetsResponse> call, Response<PetsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pet> pets = new ArrayList<>();
                    List<DataItem> items = response.body().getData();
                    for (int i = 0; i < items.size(); i++) {
                        DataItem item = items.get(i);
                        Pet pet = new Pet();
                        pet.setId(i + 1);
                        pet.setTitle(item.getTitle());
                        pet.setUrlImage(item.getUrl());
                        pets.add(pet);
                    }
                    PageRepository.this.pets = pets;
                    callback.onLoaded(pets);
                } else {
                    callback.onFailed("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PetsResponse> call, Throwable t) {
                callback.onFailed(t.getMessage());
            }
        });
    }

}
