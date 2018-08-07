package la.edu.homsombathnuol.k.laosunseen.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import la.edu.homsombathnuol.k.laosunseen.R;

public class MainFragment extends Fragment{
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Register Controller
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Replace Fragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainflagment,new RegisterFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });

    }//Method Main

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   =   inflater.inflate(R.layout.fragment_test,container,false);
        return view;
    }


}
