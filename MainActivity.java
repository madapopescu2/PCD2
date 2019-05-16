package com.example.pcd_double_exp;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

//glide

public class MainActivity extends AppCompatActivity {

    ImageView imageView, imageView2;
    SeekBar seekBar;
    TextView textView;

    Drawable drawable1, drawable2;
    Bitmap bitmap, bitmap2, bitmapFinal;

    int curProgress = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = (ImageView) findViewById(R.id.imageView);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);

        seekBar.setMax(100);
        seekBar.setProgress(curProgress);

        //imageView.setImageResource(R.drawable.peisaj);

        drawable1 = ContextCompat.getDrawable(this, R.drawable.peisaj);
        drawable2 = ContextCompat.getDrawable(this,R.drawable.portret);
        bitmap = ((BitmapDrawable) drawable1).getBitmap();
        bitmap2 = ((BitmapDrawable) drawable2).getBitmap();

        bitmapFinal = convertImage(bitmap, bitmap2, curProgress);
        imageView2.setImageBitmap(bitmapFinal);

        textView.setText(""+curProgress+"%");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                curProgress = progress;
                textView.setText(""+curProgress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bitmapFinal = convertImage(bitmap,bitmap2,curProgress);
                imageView2.setImageBitmap(bitmapFinal);
            }
        });
    }

    public static Bitmap convertImage(Bitmap original, Bitmap original2, int value){
        Bitmap finalImage = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
        
        float A;
        int R,G,B;
        int colorPixel;
        int width = original.getWidth();
        int height = original.getHeight();

        for(int i=0; i<height; i++){
            for(int j=0; j<width;j++){
                colorPixel=original.getPixel(i,j);

                if(colorPixel != 255) {
                    A = 255f / (100f / value);
                    R = Color.red(colorPixel);
                    G = Color.green(colorPixel);
                    B = Color.blue(colorPixel);

                    finalImage.setPixel(i, j, Color.argb((int) A, R, G, B));
                }
            }
        }
        return finalImage;
    }
}
