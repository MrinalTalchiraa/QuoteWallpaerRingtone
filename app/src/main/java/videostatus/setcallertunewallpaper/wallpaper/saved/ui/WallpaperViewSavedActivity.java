package videostatus.setcallertunewallpaper.wallpaper.saved.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.wallpaper.saved.adapter.WallpaperSavedAdapter;
import videostatus.setcallertunewallpaper.wallpaper.saved.adapter.WallpaperSavedImageAdapter;
import videostatus.setcallertunewallpaper.wallpaper.saved.model.FileModel;

import java.io.File;
import java.io.IOException;

public class WallpaperViewSavedActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {


    private com.facebook.ads.InterstitialAd fbinterstitialAd;

    //  ArrayList<FileModel> saveimages = StatusSaver_SavedImageGridRecycerAdapter.fileModelArrayList;
    int position;
    Boolean isFABOpen;
    ViewPager viewPager;

    LinearLayout backarrow;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton btn_share, btn_setas, btn_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_view_saved);

        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        btn_share = (FloatingActionButton) findViewById(R.id.share);
        btn_setas = (FloatingActionButton) findViewById(R.id.setas);
        btn_delete = (FloatingActionButton) findViewById(R.id.delete);
        backarrow = (LinearLayout) findViewById(R.id.menu);
        btn_share.setOnClickListener(this);
        btn_setas.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        backarrow.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("Position");
            //    imageurl = extras.getString("ImageURL");
          /*  Picasso.with(ImaegeTutorialActivity.this)
                    .load(imageurl)
                    .resize(100,100).into(imageView);*/
            Log.d("Position get in Images", position + "");
            viewPager = (ViewPager) findViewById(R.id.view_pager);
            WallpaperSavedImageAdapter adapter = new WallpaperSavedImageAdapter(this);
            // viewPager.setCurrentItem(position+1);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(position);
            viewPager.addOnPageChangeListener(this);
        }
        materialDesignFAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialDesignFAM.isOpened()) {
                    materialDesignFAM.close(true);
                }
            }
        });

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.position = position;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.share:

                materialDesignFAM.close(true);
                ShareActivity();

                break;
            case R.id.setas:
                materialDesignFAM.close(true);
                SetAsActivity();

                break;
            case R.id.delete:
                materialDesignFAM.close(true);
                DeleteActivity();
                break;
            case R.id.menu:

                finish();

                break;
        }
    }


    private void DeleteActivity() {
        FileModel fileModel = new FileModel();
        fileModel = (FileModel) WallpaperSavedAdapter.fileModelArrayList.get(position);
        String imageFilePath = fileModel.getImageFilePath();
        Uri imageConvertToUri = Uri.parse(imageFilePath);

        File fdelete = new File(imageConvertToUri.getPath());
        if (fdelete.exists()) {
            if (fdelete.delete()) {

                WallpaperSavedAdapter.fileModelArrayList.remove(position);
               /* Intent intent = new Intent(this, StatusSaver_SavedImageViewer.class);
                intent.putExtra("Position", position);
                startActivity(intent);*/
                finish();
                Toast.makeText(this, "Image Deleted Successfully....", Toast.LENGTH_SHORT).show();
                File targetLocation = new File(imageFilePath);
                addImageGallery(targetLocation);


            } else {
                System.out.println("file not Deleted :" + imageConvertToUri.getPath());
            }
        }
    }

    private void addImageGallery(File targetLocation) {

        String[] projection = {MediaStore.Images.Media._ID};

        String selection = MediaStore.Images.Media.DATA + " = ?";
        String[] selectionArgs = new String[]{targetLocation.getAbsolutePath()};

        Uri queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(queryUri, projection, selection, selectionArgs, null);
        if (c.moveToFirst()) {

            long id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            Uri deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            contentResolver.delete(deleteUri, null, null);
        } else {

        }
        c.close();

    }

    @SuppressLint("LongLogTag")
    private void SetAsActivity() {

        try {

            FileModel fileModel = new FileModel();
            fileModel = (FileModel) WallpaperSavedAdapter.fileModelArrayList.get(position);
            String imageFilePath = fileModel.getImageFilePath();

            WallpaperManager.getInstance(getApplicationContext()).setBitmap(BitmapFactory.decodeFile(imageFilePath));
            Toast.makeText(this, "Wallpaper set!", 0).show();

        } catch (IOException unused) {

            //loading.setVisibility(View.GONE);
            Toast.makeText(this, "Error!", 0).show();
        }



        /*WallpaperManager wallpaperManager
                = WallpaperManager.getInstance(getApplicationContext());
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // get the height and width of screen
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        try {
//                    int position2 = viewPager.getCurrentItem();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath, options);
            wallpaperManager.setBitmap(bitmap);

            //wallpaperManager.suggestDesiredDimensions(width / 2, height / 2);
            Toast.makeText(this,"Wallpaper Set", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private void ShareActivity() {
        FileModel fileModel = new FileModel();
        fileModel = (FileModel) WallpaperSavedAdapter.fileModelArrayList.get(position);
        String imageFilePath = fileModel.getImageFilePath();

        File file = new File(imageFilePath);
        Uri imageConvertToUri = FileProvider.getUriForFile(this,
                getPackageName() + ".provider",
                file);
        //       Uri imageConvertToUri = Uri.parse(imageFilePath);

        String appName = getResources().getString(R.string.app_name);
        final String appPackageName = getPackageName();

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageConvertToUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, appName + " \n : https://play.google.com/store/apps/details?id=" + appPackageName);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
