package krescendo.sumit.kresc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ErroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erro);
    }
    public void retry(View view)
    {
        try{
finish();
            Intent intent=new Intent(ErroActivity.this,MainActivity.class);
            startActivity(intent);

        }catch(Exception e)
        {

        }
    }
}
