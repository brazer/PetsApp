package com.example.myapplication.ui.fragment.pets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.domain.Pet;
import com.example.myapplication.vm.PageViewModel;
import com.example.myapplication.vm.main.MainViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceholderFragment extends Fragment implements PetsAdapter.OnItemClickListener {

    public static final String TAG = PlaceholderFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String KEY_POSITION = "position";
    private static final int UNSELECTED = -1;

    @BindView(R.id.main_pet_list)
    RecyclerView mainPetList;

    private MainViewModel mainViewModel;
    private PageViewModel pageViewModel;
    private PetsAdapter adapter;
    private int selectedPosition = UNSELECTED;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.init(index);
        pageViewModel.getPets().observe(this, pets -> adapter.submitList(pets));
        pageViewModel.getError().observe(this, error ->
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show());
        pageViewModel.fetchPets();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION, selectedPosition);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pets, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new PetsAdapter(getContext(), this);
        mainPetList.setAdapter(adapter);
        if (savedInstanceState != null) {
            selectedPosition = savedInstanceState.getInt(KEY_POSITION);
        }
        if (selectedPosition != UNSELECTED) {
            view.post(() -> mainPetList.scrollToPosition(selectedPosition));
        }
    }

    @Override
    public void onItemClick(Pet pet, int position) {
        mainViewModel.setSelectedPet(pet);
        selectedPosition = position;
    }
}