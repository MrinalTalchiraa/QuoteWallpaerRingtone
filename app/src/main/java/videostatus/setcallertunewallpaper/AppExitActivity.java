package videostatus.setcallertunewallpaper;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class AppExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_app);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


        //Mix Native Ads Call
        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_300);
        FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);
        ImageView image = (ImageView) findViewById(R.id.banner_image);
        CardView qurekanative = (CardView) findViewById(R.id.qurekanative);
        RelativeLayout NativeAdContainer = (RelativeLayout) findViewById(R.id.nativeAds);
        RelativeLayout NativeAdsStartApp = (RelativeLayout) findViewById(R.id.sNativeAds);
        RecyclerView nativeMoPub = (RecyclerView) findViewById(R.id.nativemopub);
        FrameLayout maxNative = (FrameLayout) findViewById(R.id.max_native_ad_layout);
        SplashLaunchActivity.MixNativeAdsCall(this, shimmerFrameLayout, frameLayout, image, qurekanative, NativeAdContainer, NativeAdsStartApp, nativeMoPub, maxNative);


        RelativeLayout Exit = (RelativeLayout) findViewById(R.id.exitapp);
        RelativeLayout Back = (RelativeLayout) findViewById(R.id.back);

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppThankYouActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppExitActivity.this, FirstPageMainActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(AppExitActivity.this, intent);
            }
        });

    }

    public void QurekaAds(View view) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(SplashLaunchActivity.QurekaAds));
        intent1.setPackage("com.android.chrome");
        startActivity(intent1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}