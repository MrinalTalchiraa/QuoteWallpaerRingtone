package videostatus.setcallertunewallpaper;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import videostatus.setcallertunewallpaper.quotestatus.ui.QuotesMainActivity;
import videostatus.setcallertunewallpaper.ringtone.ui.RingtoneCallerMainActivity;

import videostatus.setcallertunewallpaper.wallpaper.ui.WallpaperMainActivity;

public class MainHomeAppActivity extends AppCompatActivity {

    PopupWindow popupWindow;
    ImageView PopupSet;
    RelativeLayout Main;
    RelativeLayout RootMain;


    Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_app);


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


        Main = (RelativeLayout) findViewById(R.id.menu_h);
        RootMain = (RelativeLayout) findViewById(R.id.ry_banner);


        ((ImageView) findViewById(R.id.lquotes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                intentMain = new Intent(MainHomeAppActivity.this, QuotesMainActivity.class);

                CallAct1();

            }
        });
        ((ImageView) findViewById(R.id.lringtones)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                intentMain = new Intent(MainHomeAppActivity.this, RingtoneCallerMainActivity.class);

                CallAct1();

            }
        });
        ((ImageView) findViewById(R.id.lwallpaper)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                intentMain = new Intent(MainHomeAppActivity.this, WallpaperMainActivity.class);

                CallAct1();

            }
        });
        ((ImageView) findViewById(R.id.lalbum)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                intentMain = new Intent(MainHomeAppActivity.this, AlbumActivity.class);

                CallAct1();

            }
        });


        PopupSet = (ImageView) findViewById(R.id.iv_setting);
        PopupSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiatePopupWindow(view);
            }
        });


    }

    private void initiatePopupWindow(View view) {
        try {
            View inflate = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.popupset, null);
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.rate);
            LinearLayout linearLayout2 = (LinearLayout) inflate.findViewById(R.id.share);
            LinearLayout linearLayout3 = (LinearLayout) inflate.findViewById(R.id.privacy);
            LinearLayout linearLayout4 = (LinearLayout) inflate.findViewById(R.id.more);
            this.popupWindow = new PopupWindow(inflate, -2, -2);
            this.popupWindow.setFocusable(true);
            this.popupWindow.setOutsideTouchable(false);
            this.popupWindow.setAnimationStyle(R.style.animationName);
            this.popupWindow.showAtLocation(PopupSet, 53, getDensity(5.0d), PopupSet.getHeight() + getDensity(5.0d));
            linearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    final String rateapp = getPackageName();
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + rateapp));
                    startActivity(intent1);

                    popupWindow.dismiss();

                }
            });
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    String appName = getResources().getString(R.string.app_name);
                    final String appPackageName = getPackageName();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, appName + " \n : https://play.google.com/store/apps/details?id=" + appPackageName);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);

                    popupWindow.dismiss();

                }
            });
            linearLayout3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(SplashLaunchActivity.PrivacyPolicy));
                    startActivity(intent);

                    popupWindow.dismiss();

                }
            });
            linearLayout4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + SplashLaunchActivity.MoreApps));
                    startActivity(intent);

                    popupWindow.dismiss();

                }
            });
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static int getDensity(double d) {
        double d2 = (double) Resources.getSystem().getDisplayMetrics().density;
        Double.isNaN(d2);
        Double.isNaN(d2);
        return (int) (d * d2);
    }


    private void CallAct1() {
        SplashLaunchActivity.InterstitialAdsCall(MainHomeAppActivity.this, intentMain);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
