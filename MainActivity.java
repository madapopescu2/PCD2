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

    ImageView imageViewFinal, imageView1, imageView2;
    SeekBar seekBar;
    TextView textView;

    Drawable drawable1, drawable2;
    Bitmap bitmap1, bitmap2, bitmapFinal;

    int curProgress = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageViewFinal = (ImageView) findViewById(R.id.imageViewFinal); //Imaginea finala
        imageView1 = (ImageView) findViewById(R.id.imageView1); //Imaginea cu peisajul
        imageView2 = (ImageView) findViewById(R.id.imageView2); //Imaginea cu portret

        seekBar = (SeekBar) findViewById(R.id.seekBar); //bara de blending
        textView = (TextView) findViewById(R.id.textView); //nivel de blending

        seekBar.setMax(100);
        seekBar.setProgress(curProgress);


        // Trebuie pusa referinta trimisa din Select Photos, ca sa se poata afisa cele doua poze separat
        imageView1.setImageResource(R.drawable.peisaj);
        imageView2.setImageResource(R.drawable.portret);


        drawable1 = ContextCompat.getDrawable(this, R.drawable.peisaj);
        drawable2 = ContextCompat.getDrawable(this,R.drawable.portret);
        bitmap1 = ((BitmapDrawable) drawable1).getBitmap();
        bitmap2 = ((BitmapDrawable) drawable2).getBitmap();

        bitmapFinal = convertImage(bitmap1, bitmap2, curProgress);
        imageViewFinal.setImageBitmap(bitmapFinal);

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
                bitmapFinal = convertImage(bitmap1,bitmap2,curProgress);
                imageViewFinal.setImageBitmap(bitmapFinal);
            }
        });
    }

    public static Bitmap convertImage(Bitmap bitmap1, Bitmap bitmap2, int value){
        Bitmap finalImage = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Bitmap.Config.ARGB_8888);

        float A;
        int R,G,B;
        int colorPixel;
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();

        for(int i=0; i<height; i++){
            for(int j=0; j<width;j++){
                colorPixel=bitmap1.getPixel(i,j);

                if(colorPixel != 255) {
                    A = 255f / (100f / value);
                    R = Color.red(colorPixel);
                    G = Color.green(colorPixel);
                    B = Color.blue(colorPixel);

                    finalImage.setPixel(i, j, Color.argb((int) A, R, G, B));
                }

                else {
                    //Valoare mica pentru A + de implementat cand se pleaca de la portret inapoi (100% -> 50%)
                }
            }
        }
        return finalImage;
    }
}
