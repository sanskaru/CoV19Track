package com.s090.cov19track;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, container, false);

        view.findViewById(R.id.cardViewTotal).setTranslationX(1000);
        view.findViewById(R.id.cardViewTotal).animate().alpha(1f).translationXBy(-1000).setDuration(750);

        view.findViewById(R.id.cardViewActive).setTranslationX(-1000);
        view.findViewById(R.id.cardViewActive).animate().alpha(1f).translationXBy(1000).setDuration(750);

        view.findViewById(R.id.cardViewRecovered).setTranslationX(1000);
        view.findViewById(R.id.cardViewRecovered).animate().alpha(1f).translationXBy(-1000).setDuration(750);

        view.findViewById(R.id.cardViewDeaths).setTranslationX(-1000);
        view.findViewById(R.id.cardViewDeaths).animate().alpha(1f).translationXBy(1000).setDuration(750);
        return view;
    }

}
