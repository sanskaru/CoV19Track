package com.sanskaru.cov19track;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_frag, container, false);

        view.findViewById(R.id.abouttt).setTranslationY(2000);

        ((TextView) view.findViewById(R.id.apptitle)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) view.findViewById(R.id.devs)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) view.findViewById(R.id.sources)).setMovementMethod(LinkMovementMethod.getInstance());

        view.findViewById(R.id.abouttt).animate().translationYBy(-2000).alpha(1f).setDuration(500);


        return view;
    }
}
