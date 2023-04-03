package videostatus.setcallertunewallpaper.wallpaper.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.wallpaper.adapter.WallpaperCategoryListAdapter;
import videostatus.setcallertunewallpaper.wallpaper.saved.ui.WallpaperSavedListActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WallpaperMainActivity extends AppCompatActivity {


    private List<Object> mDataSet;
    RecyclerView recyclerViewlist;

    DatabaseReference reference;
    WallpaperCategoryListAdapter wallpaperCategoryListAdapter;
    RelativeLayout backarrow, Gallery;

    LottieAnimationView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_main);


        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


        //Create Folder
        SplashLaunchActivity.createFolder();


        backarrow = (RelativeLayout) findViewById(R.id.menu);
        Gallery = (RelativeLayout) findViewById(R.id.gallery);

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WallpaperMainActivity.this, WallpaperSavedListActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(WallpaperMainActivity.this, intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

        //ads code
        mDataSet = new ArrayList<>();


        loading = (LottieAnimationView) findViewById(R.id.img_internet1);
        loading.setVisibility(View.VISIBLE);


        recyclerViewlist = (RecyclerView) findViewById(R.id.list);

        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("WallpaperCate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("0").child("Category").getChildren()) {

                    String catename = snapshot.child("catename").getValue().toString();
                    //int size = Integer.parseInt(snapshot.child("size").getValue().toString());

                    //list.add(new WallpaperCategoryData(catename));
                    mDataSet.add(catename);
                }

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(WallpaperMainActivity.this, 1);
                recyclerViewlist.setLayoutManager(mLayoutManager);
                wallpaperCategoryListAdapter = new WallpaperCategoryListAdapter(WallpaperMainActivity.this, mDataSet);
                recyclerViewlist.setAdapter(wallpaperCategoryListAdapter);
                loading.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                loading.setVisibility(View.GONE);

                Toast.makeText(WallpaperMainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

}
