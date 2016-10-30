package cz.muni.fi.pv256.movio2.uco_396537;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "pref";
    private static final String THEME_NAME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        if(pref.getString(THEME_NAME, "").equals("AppTheme1")){
            setTheme(R.style.AppTheme1);
        } else {
            setTheme(R.style.AppTheme2);
        }
        setContentView(R.layout.activity_main);

        App app = new App();
        app.onCreate();

//        final Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(getApplication().getTheme().equals(R.style.AppTheme1)){
//                    setTheme(R.style.AppTheme2);
//                    MainActivity.this.recreate();
//                } else {
//                    setTheme(R.style.AppTheme1);
//                    MainActivity.this.recreate();
//                }
//            }
//        });

    }

    public void changeTheme(View view) {
        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE).edit();
        String str = getTheme().toString();
        int i = view.getId();

        if(pref.getString(THEME_NAME, "").equals("AppTheme1")){
            editor.putString(THEME_NAME, "AppTheme2");
        } else {
            editor.putString(THEME_NAME, "AppTheme1");
        }

//        switch (view.getId()){
//            case R.style.AppTheme1 :
//                editor.putString(THEME_NAME, "AppTheme2");
//                break;
//            case R.style.AppTheme2 :
//                editor.putString(THEME_NAME, "AppTheme1");
//                break;
//        }

//        if(getTheme().toString().equals(R.style.AppTheme1)){
//            editor.putString(THEME_NAME, "AppTheme2");
//        } else {
//            editor.putString(THEME_NAME, "AppTheme1");
//        }
        editor.apply();

        Intent intent = getIntent();
        finish();

        startActivity(  intent);
    }
}
