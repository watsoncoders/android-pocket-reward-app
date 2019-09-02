package com.droidoxy.easymoneyrewards.activities;

/*
 *  Created by DroidOXY
 */

import android.os.Bundle;
import android.content.Intent;

import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.app.App;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {

        if (!App.getInstance().get("isFirstTimeLaunch",true)) {
            launchHome();
            finish();
        }

        // Just set a title, description,image and background. AppIntro will do the rest
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.slide_1_title),getResources().getString(R.string.slide_1_desc), R.drawable.ic_discount, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.slide_2_title),getResources().getString(R.string.slide_2_desc), R.drawable.ic_movie, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.slide_3_title),getResources().getString(R.string.slide_3_desc), R.drawable.ic_food, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.slide_4_title),getResources().getString(R.string.slide_4_desc), R.drawable.ic_travel,getResources().getColor(R.color.colorPrimary)));

        // Hide Skip/Done button
        showSkipButton(true);
        showDoneButton(true);

    }

    void launchHome(){
        App.getInstance().store("isFirstTimeLaunch",false);
        Intent skip = new Intent(this, AppActivity.class);
        startActivity(skip);
    }

    @Override
    public void onSkipPressed() {
        launchHome();
    }

    @Override
    public void onDonePressed() {
        launchHome();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onSlideChanged() {

    }
}

