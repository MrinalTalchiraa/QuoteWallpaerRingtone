package videostatus.setcallertunewallpaper.quotestatus.saved.ui;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.saved.adapter.QuotesSavedAdapter;
import videostatus.setcallertunewallpaper.quotestatus.saved.model.FileModel;

import java.io.File;
import java.util.ArrayList;

public class QuotesSavedListActivity extends AppCompatActivity {

    QuotesSavedAdapter wallpaperSavedAdapter;
    String fileNames;
    String filepath;
    RecyclerView recyclerView;
    public static ArrayList<FileModel> FilePathStrings;
    File[] listFile;

    LinearLayout Back;

    File file;

    File imageRoot = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Quotes Status Ringtone/Quotes Images");

    LottieAnimationView loading;

    TextView NoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_saved_list);


        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


        Back = (LinearLayout) findViewById(R.id.menu);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loading = (LottieAnimationView) findViewById(R.id.img_internet1);
        loading.setVisibility(View.VISIBLE);
        NoData = (TextView) findViewById(R.id.nodata);



        fetchImageLocationAndName();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchImageLocationAndName();
    }

    public void fetchImageLocationAndName() {

        this.FilePathStrings = new ArrayList();

        if (Environment.getExternalStorageState().equals("mounted")) {
            this.file = new File(this.imageRoot + File.separator);
            Log.e("file location", this.file.toString());
        } else {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
        }
        if (this.file.isDirectory()) {
            this.listFile = this.file.listFiles();
            Log.e("file length", this.listFile.length + "");
            if (this.listFile != null) {
                for (int i = 0; i < this.listFile.length; i++) {
                    this.filepath = this.listFile[i].getAbsolutePath();
                    this.fileNames = this.listFile[i].getName();
                    if (this.fileNames.endsWith(".jpg") || this.fileNames.endsWith(".jpeg") || this.fileNames.endsWith(".png") || this.fileNames.endsWith(".gif")) {
                        FileModel fileModel = new FileModel();
                        fileModel.setImageFilePath(this.filepath);
                        fileModel.setImageFileName(this.fileNames);
                        fileModel.setImageChecked(Boolean.valueOf(false));
                        this.FilePathStrings.add(fileModel);
                    }
                }
            }
        }

        this.recyclerView = (RecyclerView) findViewById(R.id.list);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.recyclerView.setHasFixedSize(true);
        this.wallpaperSavedAdapter = new QuotesSavedAdapter(this, FilePathStrings);
        this.recyclerView.setAdapter(wallpaperSavedAdapter);
        loading.setVisibility(View.GONE);

        int total = wallpaperSavedAdapter.getItemCount();
        if (total > 0) {
            NoData.setVisibility(View.GONE);
        } else {
            NoData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
