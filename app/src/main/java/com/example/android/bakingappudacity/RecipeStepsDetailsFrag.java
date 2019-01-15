package com.example.android.bakingappudacity;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingappudacity.RecipesModelJson.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepsDetailsFrag extends Fragment {
    private SimpleExoPlayer mPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView mInstructionsTV;
    private Button mNextBtn;
    private Button mPrevBtn;
    private ImageView mThumbnail;
    private ArrayList<Step> mData;
    private Step steps;
    private Uri videoUri;
    private String imageUrl;
    private String video;
    private View view;
    private boolean notMasterDetail;
    private String name;
    private long playerPosition;
    private boolean playerState;
    private static final String PLAYER_POSITION = "PLAYER_POSITION";
    private static final String PLAYER_STATE = "STATE";



    @SuppressLint("ValidFragment")
    public RecipeStepsDetailsFrag(Step current) {
        this.steps = current;
    }

    public RecipeStepsDetailsFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recipe_steps_details, container, false);
        if (savedInstanceState != null){
            steps = savedInstanceState.getParcelable(getContext().getString(R.string.step_extra));
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION);
            playerState = savedInstanceState.getBoolean(PLAYER_STATE);
        }
        if (getActivity().getIntent().getParcelableExtra(getContext().getString(R.string.step_extra)) != null) {
            steps = getActivity().getIntent().getParcelableExtra(getContext().getString(R.string.step_extra));
        }
        mData = getActivity().getIntent().getParcelableArrayListExtra(getContext().getString(R.string.steps_list));
        mPlayerView = view.findViewById(R.id.exo_player);
        videoUri = Uri.parse(steps.getVideoURL());
        video = videoUri.toString();
        imageUrl = steps.getThumbnailURL();
        name = getActivity().getIntent().getStringExtra(getContext().getString(R.string.name));

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        playerPosition = mPlayer.getCurrentPosition();
        playerState = mPlayer.getPlayWhenReady();
        outState.putParcelable(getContext().getString(R.string.step_extra),steps);
        outState.putLong(PLAYER_POSITION,playerPosition);
        outState.putBoolean(PLAYER_STATE,playerState);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 || mPlayer == null){
            DesignLayout(view);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            DesignLayout(view);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
    private void DesignLayout(View view) {
        //this is a way to handle a mistake in Udacity's Json response where they accidentally
        //switched the video url with the thumbnail url
        final int id = steps.getId();
        String desc = steps.getShortDescription();
        if (id == 5 && desc.equals("Finish filling prep")){
            mPlayerView.setVisibility(View.VISIBLE);
            intializePlayer(Uri.parse(steps.getThumbnailURL()));
        } //end of mistake


        else if (!(video.equals(""))){
            mPlayerView.setVisibility(View.VISIBLE);
            intializePlayer(videoUri);
        } else if (!(imageUrl.equals(""))){
            mThumbnail = view.findViewById(R.id.thumbnail);
            mThumbnail.setVisibility(View.VISIBLE);
            Picasso.get().load(imageUrl).into(mThumbnail);
        }

        mInstructionsTV = view.findViewById(R.id.instructionsTV);
        mInstructionsTV.setText(steps.getDescription());

        if (view.findViewById(R.id.next_btn) != null) {
            notMasterDetail = true;
            mNextBtn = view.findViewById(R.id.next_btn);

            int next_id = id + 1;
            int prev_id = id - 1;
            if (next_id < mData.size()) {
                mNextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Step Nextstep = mData.get(id + 1);
                        Intent intent = new Intent(getContext(), RecipeStepsDetails.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(getContext().getString(R.string.name),name);
                        intent.putExtra(getContext().getString(R.string.step_extra), Nextstep);
                        intent.putParcelableArrayListExtra(getContext().getString(R.string.steps_list), mData);
                        getContext().startActivity(intent);

                    }
                });
            } else {
                mNextBtn.setVisibility(View.GONE);
            }
            mPrevBtn = view.findViewById(R.id.prev_btn);
            if (prev_id >= 0) {
                mPrevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Step Prevstep = mData.get(id - 1);
                        Intent intent = new Intent(getContext(), RecipeStepsDetails.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(getContext().getString(R.string.step_extra), Prevstep);
                        intent.putExtra(getContext().getString(R.string.name),name);
                        intent.putParcelableArrayListExtra(getContext().getString(R.string.steps_list), mData);
                        getContext().startActivity(intent);
                    }
                });
            } else {
                mPrevBtn.setVisibility(View.GONE);
            }
        }
    }

    private void intializePlayer(Uri videoUri) {
        if (mPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            mPlayerView.setPlayer(mPlayer);
            String UserAgent = Util.getUserAgent(getContext(),"BakingAppUdacity");
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,new DefaultDataSourceFactory(getContext(),UserAgent)
                    ,new DefaultExtractorsFactory(),null,null);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mPlayer!=null){
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        }
    }



    //https://stackoverflow.com/questions/49303165/exoplayer-resume-on-same-position-on-rotate-screen
    //https://stackoverflow.com/questions/46713761/how-to-play-video-full-screen-in-landscape-using-exoplayer
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (notMasterDetail) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LinearLayout.LayoutParams prams = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
                prams.height = prams.MATCH_PARENT;
                prams.width = prams.MATCH_PARENT;
                mPlayerView.setLayoutParams(prams);
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                getActivity().getWindow().getDecorView().setSystemUiVisibility
                        (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mPlayerView.getLayoutParams();
                mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                params.width = params.MATCH_PARENT;
                params.height = 730;
                mPlayerView.setLayoutParams(params);
            }

        }
    }

}
