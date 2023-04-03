package videostatus.setcallertunewallpaper.wallpaper.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import videostatus.setcallertunewallpaper.R;

import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.wallpaper.adapter.WallpaperListAdapter;
import videostatus.setcallertunewallpaper.wallpaper.model.WallpaperData;

import java.util.ArrayList;
import java.util.List;

public class WallpaperCategoryListActivity extends AppCompatActivity {

    TextView titleText;
    LinearLayout backarrow;
    int position;
    public static String Catename;

    RecyclerView recyclerviewWallpaper;
    WallpaperListAdapter wallpaperListAdapter;

    List<WallpaperData> list;

    String aa;

    LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_category_list);



        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


            Catename = getIntent().getStringExtra("catename");
            position = getIntent().getIntExtra("position", 0);
            //size = getIntent().getIntExtra("size", 0);

            titleText = (TextView) findViewById(R.id.toolbar_title);
            titleText.setText(Catename);

            backarrow = (LinearLayout) findViewById(R.id.menu);

            backarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onBackPressed();

                }
            });

            loading = (LottieAnimationView) findViewById(R.id.img_internet11);
            loading.setVisibility(View.VISIBLE);

            list = new ArrayList<>();

            recyclerviewWallpaper = (RecyclerView) findViewById(R.id.wallpaperimage);

            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("Wallpaper/" + Catename);

            storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {

                    for (StorageReference fileRef : listResult.getItems()) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                aa = fileRef.getName();
                                String newString = aa.replace("T", " ");
                                String filename = newString.substring(0, newString.lastIndexOf("."));

                                list.add(new WallpaperData(uri.toString(), filename));

                            }
                        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(WallpaperCategoryListActivity.this, 2);
                                recyclerviewWallpaper.setLayoutManager(mLayoutManager);
                                wallpaperListAdapter = new WallpaperListAdapter(WallpaperCategoryListActivity.this, Catename, list);
                                recyclerviewWallpaper.setAdapter(wallpaperListAdapter);
                                loading.setVisibility(View.GONE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                loading.setVisibility(View.GONE);

                            }
                        });
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {

                @Override
                public void onFailure(@NonNull Exception e) {

                    loading.setVisibility(View.GONE);
                    //NoData.setVisibility(View.VISIBLE);
                    // Toast.makeText(getActivity(), "No Data 111", Toast.LENGTH_SHORT).show();

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
