package la.edu.homsombathnuol.k.laosunseen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import la.edu.homsombathnuol.k.laosunseen.fragment.ServiceFragment;

public class ServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);



//        Add Fragment
        addFragment(savedInstanceState);

//        Exit Controller
        exitController();

    }//Main Method

    private void exitController() {
        TextView textView = findViewById(R.id.txtExit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                finish();

            }
        });
    }

    private void addFragment(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentServiceFragment,new ServiceFragment())
                    .commit();
        }
    }

}//Main Class
