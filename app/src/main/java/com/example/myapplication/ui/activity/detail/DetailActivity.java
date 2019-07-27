package com.example.myapplication.ui.activity.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.domain.Pet;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY_ARGS = "args";

    @BindView(R.id.image_detail)
    ImageView imageDetail;
    @BindView(R.id.text_index_detail)
    TextView textIndexDetail;
    @BindView(R.id.text_detail)
    TextView textDetail;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        unbinder = ButterKnife.bind(this);
        Pet pet = getIntent().getParcelableExtra(KEY_ARGS);
        textIndexDetail.setText(String.valueOf(pet.getId()));
        textDetail.setText(pet.getTitle());
        Picasso.get().load(pet.getUrlImage()).placeholder(R.mipmap.ic_launcher).into(imageDetail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

}
