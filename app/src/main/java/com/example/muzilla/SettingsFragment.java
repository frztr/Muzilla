package com.example.muzilla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class SettingsFragment extends Fragment implements IBaseFragment {

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText edt = (EditText) getView().findViewById(R.id.access_token_input);
        Button button = (Button) getView().findViewById(R.id.choose_wall_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseFile(view);
            }
        });
        if(sp.getString("access_token","")!=""||sp.getString("access_token","")!=null)
        {
            edt.setText(sp.getString("access_token",""));
        }

        edt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    sp.edit().putString("access_token", edt.getText().toString()).commit();
                    API.getInstance().setToken(edt.getText().toString());
                    API.getInstance().getMyProfile(sp,getActivity());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setSharedPreferences(SharedPreferences sp) {
        this.sp = sp;
    }

    public void ChooseFile(View v)
    {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select image"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            sp.edit().putString("wall_uri",data.getData().toString()).commit();
            ImageView constraintLayout = getActivity().findViewById(R.id.constraintLayout);
            constraintLayout.setImageURI(data.getData());
        }
    }
}