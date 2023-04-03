package videostatus.setcallertunewallpaper;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class FirstPageMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page_main);


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


        SplashLaunchActivity.getPermission(this);

        SplashLaunchActivity.createFolder();


        if (MyOneApplication.getuser_onetime() == 0) {

            PrivacyDialog();

        }


        ((LinearLayout) findViewById(R.id.btnstart)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mix Interstitial Ads Call
                Intent intent = new Intent(FirstPageMainActivity.this, MainHomeAppActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(FirstPageMainActivity.this, intent);
            }
        });
        ((LinearLayout) findViewById(R.id.btnrate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String rateapp = getPackageName();
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + rateapp));
                startActivity(intent1);
            }
        });
        ((LinearLayout) findViewById(R.id.btnshare)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appName = getResources().getString(R.string.app_name);
                final String appPackageName = getPackageName();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, appName + " : \nhttps://play.google.com/store/apps/details?id=" + appPackageName);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        ((LinearLayout) findViewById(R.id.btnmore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + SplashLaunchActivity.MoreApps));
                startActivity(intent);
            }
        });

        ((RelativeLayout) findViewById(R.id.btnRemoveAds)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstPageMainActivity.this, PremiumRemoveAdsActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(FirstPageMainActivity.this, intent);
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

        final Dialog dialog = new Dialog(FirstPageMainActivity.this, R.style.DialogTheme);
        dialog.setContentView(R.layout.popup_dialog_exit);
        dialog.setCancelable(false);

        LinearLayout DialogExit = (LinearLayout) dialog.findViewById(R.id.dialogexit);

        Animation animation1 = AnimationUtils.loadAnimation(FirstPageMainActivity.this,
                R.anim.slide_up);
        DialogExit.setAnimation(animation1);

        //Mix Native Ads Call
        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) dialog.findViewById(R.id.shimmer_300);
        FrameLayout frameLayout = dialog.findViewById(R.id.fl_adplaceholder);
        ImageView image = (ImageView) dialog.findViewById(R.id.banner_image);
        CardView qurekanative = (CardView) dialog.findViewById(R.id.qurekanative);
        RelativeLayout NativeAdContainer = (RelativeLayout) dialog.findViewById(R.id.nativeAds);
        RelativeLayout NativeAdsStartApp = (RelativeLayout) dialog.findViewById(R.id.sNativeAds);
        RecyclerView nativeMoPub = (RecyclerView) dialog.findViewById(R.id.nativemopub);
        FrameLayout maxNative = (FrameLayout) dialog.findViewById(R.id.max_native_ad_layout);
        SplashLaunchActivity.MixNativeAdsCall(this, shimmerFrameLayout, frameLayout, image, qurekanative, NativeAdContainer, NativeAdsStartApp, nativeMoPub, maxNative);

        ImageView Yes = (ImageView) dialog.findViewById(R.id.yes);
        ImageView NotNow = (ImageView) dialog.findViewById(R.id.no);

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                Intent intent = new Intent(FirstPageMainActivity.this, AppExitActivity.class);
                SplashLaunchActivity.InterstitialAdsCall(FirstPageMainActivity.this, intent);
            }
        });
        NotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

        return;

    }

    private void PrivacyDialog() {

        final Dialog dialog = new Dialog(FirstPageMainActivity.this);
        dialog.setContentView(R.layout.popup_privacy_policy);
        dialog.setCancelable(false);

        AppCompatButton Accept = (AppCompatButton) dialog.findViewById(R.id.btnAccept);

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyOneApplication.setuser_onetime(1);

                dialog.dismiss();

            }
        });

        dialog.show();

    }

}
