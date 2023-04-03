package videostatus.setcallertunewallpaper.quotestatus.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.adapter.QuotesCategoryListAdapter;
import videostatus.setcallertunewallpaper.quotestatus.adapter.QuotesListAdapter;
import videostatus.setcallertunewallpaper.quotestatus.model.QuotesImageData;

import java.util.ArrayList;
import java.util.List;

public class QuotesImagesFragment extends Fragment {

    TextView titleText;
    LinearLayout backarrow;
    int position;
    public static String Catename;

    RecyclerView recyclerviewQuotes;
    QuotesListAdapter quotesListAdapter;

    View view;

    List<QuotesImageData> list;
    //ProgressBar progressBar;
    //TextView NoData;

    LottieAnimationView loading;

    String aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_quotes_status_image, container, false);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) view.findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) view.findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) view.findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(getActivity(), adContainer, adContainer2, OwnBannerAds);



        Catename = QuotesCategoryListAdapter.Catename;
        position = QuotesCategoryListAdapter.Position;
        //size = QuotesCategoryListAdapter.size;

        loading = (LottieAnimationView) view.findViewById(R.id.img_internet1);
        loading.setVisibility(View.VISIBLE);


        list = new ArrayList<>();

        recyclerviewQuotes = (RecyclerView) view.findViewById(R.id.list);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("Quotes/" + Catename);

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

                            list.add(new QuotesImageData(uri.toString(), filename));

                        }
                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                            recyclerviewQuotes.setLayoutManager(mLayoutManager);
                            quotesListAdapter = new QuotesListAdapter(getActivity(), Catename, list);
                            recyclerviewQuotes.setAdapter(quotesListAdapter);
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

            }
        });


        return view;
    }

}