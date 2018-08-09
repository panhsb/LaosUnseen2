package la.edu.homsombathnuol.k.laosunseen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import la.edu.homsombathnuol.k.laosunseen.fragment.ServiceFragment;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentServiceFragment,new ServiceFragment())
                    .commit();
        }

    }//Main Method

}//Main Class
