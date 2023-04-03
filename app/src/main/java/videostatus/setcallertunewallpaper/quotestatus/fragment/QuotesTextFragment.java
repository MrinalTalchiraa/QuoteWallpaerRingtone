package videostatus.setcallertunewallpaper.quotestatus.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import videostatus.setcallertunewallpaper.CheckInternet;
import videostatus.setcallertunewallpaper.R;
import videostatus.setcallertunewallpaper.SplashLaunchActivity;
import videostatus.setcallertunewallpaper.quotestatus.adapter.QuotesCategoryListAdapter;
import videostatus.setcallertunewallpaper.quotestatus.adapter.QuotesTextListAdapter;
import videostatus.setcallertunewallpaper.quotestatus.model.QuotesTextdata;

import java.util.ArrayList;
import java.util.List;

public class QuotesTextFragment extends Fragment {

    RecyclerView recyclerviewQuotesText;
    DatabaseReference reference;
    public static List<QuotesTextdata> list;
    QuotesTextListAdapter quotesTextListAdapter;

    View view;
    //ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_quotes_text, container, false);

        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) view.findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) view.findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) view.findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(getActivity(), adContainer, adContainer2, OwnBannerAds);

        //pDialog = new ProgressDialog(getActivity());
        //pDialog.setMessage("Quotes Text Loading...");
        //pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //pDialog.setCancelable(false);
        //pDialog.show();

        recyclerviewQuotesText = (RecyclerView) view.findViewById(R.id.quotestext);

        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference();

        //for (DataSnapshot snapshot : dataSnapshot.child("0").child("Category").getChildren()) {

        reference.child("QuotesText").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child("0").child(QuotesCategoryListAdapter.Catename).getChildren()) {

                    // String name = snapshot.child("name").getValue().toString();
                    String link = snapshot.child("link").getValue().toString();

                    list.add(new QuotesTextdata(link));
                }


                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
                recyclerviewQuotesText.setLayoutManager(mLayoutManager);
                quotesTextListAdapter = new QuotesTextListAdapter(getActivity(), list);
                recyclerviewQuotesText.setAdapter(quotesTextListAdapter);

                //pDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // pDialog.dismiss();

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}