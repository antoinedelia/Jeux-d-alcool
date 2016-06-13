package com.antoinedelia.lebarbu_versionalcool;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class TutorialActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        //addSlide(first_fragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.app_name), getResources().getString(R.string.tutorialWelcome), R.drawable.drinking_games, ContextCompat.getColor(this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.action_addPlayers), getResources().getString(R.string.tutorialPlayers), R.drawable.add_players, ContextCompat.getColor(this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.action_rules), getResources().getString(R.string.tutorialRules), R.drawable.rules_bar, ContextCompat.getColor(this, R.color.colorPrimary)));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setSkipText(getResources().getString(R.string.skip));
        setDoneText(getResources().getString(R.string.done));

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
