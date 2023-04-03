package videostatus.setcallertunewallpaper.ringtone.ui;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.saved.ui.QuotesSavedListActivity;
import videostatus.setcallertunewallpaper.quotestatus.ui.QuotesMainActivity;
import videostatus.setcallertunewallpaper.ringtone.adapter.RingtoneCategoryListAdapter;
import videostatus.setcallertunewallpaper.ringtone.model.RingtoneCategoryData;
import videostatus.setcallertunewallpaper.ringtone.saved.ui.RingtoneSavedListActivity;

public class RingtoneCallerMainActivity extends AppCompatActivity {

    RecyclerView recyclerViewlist;

    DatabaseReference reference;
    private List<RingtoneCategoryData> list;
    RingtoneCategoryListAdapter ringtoneCategoryListAdapter;
    RelativeLayout backarrow, Gallery;

    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone_caller_main);


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

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        Gallery = (RelativeLayout) findViewById(R.id.gallery);

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RingtoneCallerMainActivity.this, RingtoneSavedListActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(RingtoneCallerMainActivity.this, intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });


        loading = (LottieAnimationView) findViewById(R.id.img_internet);
        loading.setVisibility(View.VISIBLE);


        recyclerViewlist = (RecyclerView) findViewById(R.id.list);

        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Ringtones").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("0").child("Category").getChildren()) {

                    String catename = snapshot.child("catename").getValue().toString();
                    //int size = Integer.parseInt(snapshot.child("size").getValue().toString());

                    list.add(new RingtoneCategoryData(catename));
                }
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(RingtoneCallerMainActivity.this, 1);
                recyclerViewlist.setLayoutManager(mLayoutManager);
                ringtoneCategoryListAdapter = new RingtoneCategoryListAdapter(RingtoneCallerMainActivity.this, list);
                recyclerViewlist.setAdapter(ringtoneCategoryListAdapter);
                loading.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                loading.setVisibility(View.GONE);

                Toast.makeText(RingtoneCallerMainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


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

