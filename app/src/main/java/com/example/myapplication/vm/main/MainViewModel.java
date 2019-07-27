package com.example.myapplication.vm.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.domain.Pet;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Pet> selectedPet = new MutableLiveData<>();

    public LiveData<Pet> getSelectedPet() {
        return selectedPet;
    }

    public void setSelectedPet(Pet pet) {
        selectedPet.setValue(pet);
    }

}
