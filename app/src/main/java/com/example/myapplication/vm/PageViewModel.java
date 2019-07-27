package com.example.myapplication.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.domain.Pet;
import com.example.myapplication.repository.PageRepository;

import java.util.List;

public class PageViewModel extends ViewModel {

    private static final String KEY_INDEX = "index";

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<List<Pet>> pets = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private PageRepository repository;

    public void init(int index) {
        mIndex.setValue(index);
        repository = new PageRepository(index);
    }

    public LiveData<List<Pet>> getPets() {
        return pets;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchPets() {
        repository.fetchPets(new DataLoadingCallback() {
            @Override
            public void onLoaded(List<Pet> pets) {
                PageViewModel.this.pets.setValue(pets);
            }

            @Override
            public void onFailed(String error) {
                PageViewModel.this.error.setValue(error);
            }
        });
    }

    public interface DataLoadingCallback {
        void onLoaded(List<Pet> pets);
        void onFailed(String error);
    }

}