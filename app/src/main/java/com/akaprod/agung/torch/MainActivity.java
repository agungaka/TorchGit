package com.akaprod.agung.torch;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private boolean isLighOn = false;

    private Camera camera;

    private Button button;

    private TextView txt;

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        txt = (TextView)findViewById(R.id.status);

        Context context = this;
        PackageManager pm = context.getPackageManager();

        // if device support camera?
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            //Log.e("err", "Device has no camera!");
            Toast.makeText(getApplicationContext(),"Kamera tidak tersedia",Toast.LENGTH_SHORT).show();
            return;
        }

        camera = Camera.open();
        final Parameters p = camera.getParameters();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isLighOn){
                    //matikan

                    //Log.i("info", "torch is turn off!");
                    Toast.makeText(getApplicationContext(),"Flash mati",Toast.LENGTH_SHORT).show();

                    p.setFlashMode(Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    isLighOn = false;
                    txt.setText("Off");


                }else{
                    //nyalakan

                    //Log.i("info", "torch is turn on!");
                    Toast.makeText(getApplicationContext(),"Flash nyala",Toast.LENGTH_SHORT).show();

                    p.setFlashMode(Parameters.FLASH_MODE_TORCH);

                    camera.setParameters(p);
                    camera.startPreview();
                    isLighOn = true;
                    txt.setText("On");
                }

            }
        });

    }
}
