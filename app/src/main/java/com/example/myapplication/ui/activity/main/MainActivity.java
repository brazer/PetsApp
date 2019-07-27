package com.example.myapplication.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.domain.PetType;
import com.example.myapplication.ui.activity.detail.DetailActivity;
import com.example.myapplication.ui.fragment.pets.PlaceholderFragment;
import com.example.myapplication.vm.main.MainViewModel;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_activity_tabs)
    TabLayout tabs;

    private Unbinder unbinder;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getSelectedPet().observe(this, pet -> {
            if (pet != null) {
                Intent intent = new Intent(this, DetailActivity.class);
                intent.putExtra(DetailActivity.KEY_ARGS, pet);
                startActivity(intent);
                viewModel.setSelectedPet(null);
            }
        });
        addTabs();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(PlaceholderFragment.TAG);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_container, fragment, PlaceholderFragment.TAG)
                    .commit();
        } else {
            showPage(PetType.CAT.getId());
        }
    }

    private void addTabs() {
        addTab("Tab #1", PetType.CAT.getId(), true);
        addTab("Tab #2", PetType.DOG.getId(), false);
    }

    private void addTab(String name, int position, boolean selected) {
        TabLayout.Tab tab = tabs.newTab();
        TextView textView = new TextView(this);
        textView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(v -> {
            tab.select();
            showPage(position);
        });
        textView.setText(name);
        tab.setCustomView(textView);
        tabs.addTab(tab, position, selected);
    }

    private void showPage(int position) {
        PlaceholderFragment fragment = PlaceholderFragment.newInstance(position);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_container, fragment, PlaceholderFragment.TAG)
                .commit();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

}