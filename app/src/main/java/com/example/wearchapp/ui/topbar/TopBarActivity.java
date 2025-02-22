package com.example.wearchapp.ui.topbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.wearchapp.R;
import com.example.wearchapp.data.model.ClothesItem;
import com.example.wearchapp.ui.main.MainFragment;

public class TopBarActivity extends AppCompatActivity implements MainFragment.MainFragmentListener {

    public static Intent newIntent(Context context) {
        return new Intent(context, TopBarActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_bar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Fragment fragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commitNow();
    }

    @Override
    public void onClickCategoryItem(ClothesItem clothesItem) {

    }
}