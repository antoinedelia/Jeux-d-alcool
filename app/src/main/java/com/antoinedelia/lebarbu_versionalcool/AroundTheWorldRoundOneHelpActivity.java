package com.antoinedelia.lebarbu_versionalcool;

import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class AroundTheWorldRoundOneHelpActivity extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        //addSlide(first_fragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.aroundTheWorldRoundOne), getResources().getString(R.string.aroundTheWorldRoundOneSteps), R.drawable.around_the_world_round_one, ContextCompat.getColor(this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.aroundTheWorldRoundOnePartOne), getResources().getString(R.string.aroundTheWorldRoundOneStepsPartOne), R.drawable.red_or_black, ContextCompat.getColor(this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.aroundTheWorldRoundOnePartTwo), getResources().getString(R.string.aroundTheWorldRoundOneStepsPartTwo), R.drawable.more_or_less, ContextCompat.getColor(this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.aroundTheWorldRoundOnePartThree), getResources().getString(R.string.aroundTheWorldRoundOneStepsPartThree), R.drawable.between_or_outside, ContextCompat.getColor(this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.aroundTheWorldRoundOnePartFour), getResources().getString(R.string.aroundTheWorldRoundOneStepsPartFour), R.drawable.same_or_different, ContextCompat.getColor(this, R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.aroundTheWorldRoundOnePartFive), getResources().getString(R.string.aroundTheWorldRoundOneStepsPartFive), R.drawable.hearts_or_spades_or_diamonds_or_clubs, ContextCompat.getColor(this, R.color.colorPrimary)));
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
