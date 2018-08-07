package la.edu.homsombathnuol.k.laosunseen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import la.edu.homsombathnuol.k.laosunseen.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Ad Fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentMainflagment,new MainFragment())
                    .commit();
        }

    }//Main Method
}//Main Class
