package com.example.silvia.dbproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    //permessi necessari per android 6
    //private static final int REQUEST_WRITE_STORAGE = 100;
    //private static final int REQUEST_READ_STORAGE = 101;
    // private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE=1234;

    private final static int SELECT_PHOTO = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        DB dataBaseDiPersone = new DB(this);
        //dataBaseDiPersone.cancella();
        Log.d("Insert: ", "Inserting ..");
        /*dataBaseDiPersone.addPersona(new Persona("Silvia", "Peveri", "333 3333333", "via Guercino, 21", ""));
        dataBaseDiPersone.addPersona(new Persona("Raffaele", "Pianazza", "333 3333333", "302", ""));
        dataBaseDiPersone.addPersona(new Persona("Alessandro", "Covre", "333 3333333", "305",""));
        dataBaseDiPersone.addPersona(new Persona("Mattia", "Esposito", "333 3333333", "302",""));*/
        Log.d("Reading: ", "Reading all persone..");

        List<Persona> personaList = dataBaseDiPersone.getAllPersona();

//        dataBaseDiPersone.deleteRow();
        Log.d("LIST: ", String.valueOf(personaList.size()));

        for (Persona p: personaList){
            String log = "ID: " + p.getId() +
                    ", Name: " + p.getName() +
                    ", Surname: " + p.getSurname() +
                    ", Number: " + p.getNumber() +
                    ", Address: " + p.getAddress()+
                    ", Image: " + p.getImage();
            Log.d("LIST: ",log);
            //dataBaseDiPersone.deletePersona(p.getId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void caricaFoto(View v){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, SELECT_PHOTO);
    }
    public void scattaFoto(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null){

            Uri image = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(image, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            // serve per salvare il file nella cartella Image dell'applicazione
            
            /*File file = getOutputMediaFile();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
                    Toast saved = Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT);
                    saved.show();
                } else {
                    Toast unsaved = Toast.makeText(getApplicationContext(), "Image unsaved", Toast.LENGTH_SHORT);
                    unsaved.show();
                }
            catch (IOException e){
                e.printStackTrace();
            }

            Log.v("PATH", file.getPath().toString());
            }
            bitmap = BitmapFactory.decodeFile(file.getName());*/
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);


        }


    if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null){

        Bundle extras = data.getExtras();
        Bitmap bitmap = (Bitmap)extras.get("data");

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);


    }
}


    private File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
        + "/Android/data/"
        + getApplicationContext().getPackageName()
        +"/Images");

        if(! mediaStorageDir.exists()){
            if(! mediaStorageDir.mkdir()){
                return null;
            }
        }
        //create a media filename
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "CONTATTO_" + timeStamp+".jpg";
        mediaFile = new File(mediaStorageDir.getPath()+ File.separator + mImageName);
        return mediaFile;
    }
}
