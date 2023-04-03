package videostatus.setcallertunewallpaper.ringtone.ui;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.ringtone.adapter.RingtoneListAdapter;
import videostatus.setcallertunewallpaper.ringtone.model.RingtoneData;

public class RingtoneCallerCategoryListActivity extends AppCompatActivity {

    TextView titleText;
    LinearLayout backarrow;
    int position;
    public static String Catename;

    RecyclerView recyclerviewRingtone;
    RingtoneListAdapter ringtoneListAdapter;

    public static List<RingtoneData> list;

    LottieAnimationView loading;

    String aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringtone_caller_category_list);


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


        loading = (LottieAnimationView) findViewById(R.id.img_internet);
        loading.setVisibility(View.VISIBLE);


        list = new ArrayList<>();

        recyclerviewRingtone = (RecyclerView) findViewById(R.id.list);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("Ringtones/" + Catename);

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


                            list.add(new RingtoneData(uri.toString(), filename));

                        }
                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(RingtoneCallerCategoryListActivity.this, 1);
                            recyclerviewRingtone.setLayoutManager(mLayoutManager);
                            ringtoneListAdapter = new RingtoneListAdapter(RingtoneCallerCategoryListActivity.this, Catename);
                            recyclerviewRingtone.setAdapter(ringtoneListAdapter);
                            loading.setVisibility(View.GONE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            loading.setVisibility(View.GONE);
                            //Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                loading.setVisibility(View.GONE);
                // Toast.makeText(getActivity(), "No Data 111", Toast.LENGTH_SHORT).show();
                Log.e("Error", e.getMessage());

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



