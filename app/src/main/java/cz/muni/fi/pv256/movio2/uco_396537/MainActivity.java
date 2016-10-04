package cz.muni.fi.pv256.movio2.uco_396537;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        App app = new App();
        app.onCreate();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        Log.i("Ja som TAG", "metoda onStart");
//    }
}
