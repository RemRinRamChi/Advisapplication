package tsugumi.seii.bankai.advisoryapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String ID_ID = "ID_ID";
    public static final String TOKEN_ID = "TOKEN_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String id= getIntent().getStringExtra(ID_ID);
        String token= getIntent().getStringExtra(TOKEN_ID);

        ((TextView)findViewById(R.id.textView)).setText(id+"   "+token);


    }
}
