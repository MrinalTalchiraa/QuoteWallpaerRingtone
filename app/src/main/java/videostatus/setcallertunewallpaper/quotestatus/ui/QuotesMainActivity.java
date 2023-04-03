package videostatus.setcallertunewallpaper.quotestatus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.MyOneApplication;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.adapter.QuotesCategoryListAdapter;
import videostatus.setcallertunewallpaper.quotestatus.model.QuotesCategoryData;
import videostatus.setcallertunewallpaper.quotestatus.saved.ui.QuotesSavedListActivity;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QuotesMainActivity extends AppCompatActivity {


    RecyclerView recyclerViewlist;

    DatabaseReference reference;
    private List<QuotesCategoryData> list;
    QuotesCategoryListAdapter quotesCategoryListAdapter;
    RelativeLayout backarrow, Gallery;

    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_main);


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

                Intent intent = new Intent(QuotesMainActivity.this, QuotesSavedListActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(QuotesMainActivity.this, intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });


        loading = (LottieAnimationView) findViewById(R.id.img_internet1);
        loading.setVisibility(View.VISIBLE);


        recyclerViewlist = (RecyclerView) findViewById(R.id.list);

        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("QuotesCate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("0").child("Category").getChildren()) {

                    String catename = snapshot.child("catename").getValue().toString();
                    //int size = Integer.parseInt(snapshot.child("size").getValue().toString());

                    list.add(new QuotesCategoryData(catename));
                }

                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(QuotesMainActivity.this, 1);
                recyclerViewlist.setLayoutManager(mLayoutManager);
                quotesCategoryListAdapter = new QuotesCategoryListAdapter(QuotesMainActivity.this, list);
                recyclerViewlist.setAdapter(quotesCategoryListAdapter);
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                loading.setVisibility(View.GONE);

                Toast.makeText(QuotesMainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
