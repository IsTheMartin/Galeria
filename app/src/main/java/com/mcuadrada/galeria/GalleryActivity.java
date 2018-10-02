package com.mcuadrada.galeria;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class GalleryActivity extends AppCompatActivity {

    RecyclerView rcvGallery;
    GalleryAdapter adapter;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        rcvGallery = (RecyclerView) findViewById(R.id.rcvGallery);
        rcvGallery.setLayoutManager(new GridLayoutManager(GalleryActivity.this, 2));

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            List<String> imageList = getImagePath();
            adapter = new GalleryAdapter(GalleryActivity.this, imageList);
            rcvGallery.setAdapter(adapter);
        }
    }

    private List<String> getImagePath(){
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.Images.ImageColumns.DATA};

        Cursor cursor = null;

        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();

        String[] directories = null;

        if(u != null){
            cursor = managedQuery(u, projection, null, null,
                    MediaStore.Images.ImageColumns.DATE_ADDED);
        }

        if((cursor != null) && (cursor.moveToFirst())){
            do{
                String tempDir = cursor.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try{
                    dirList.add(tempDir);
                } catch(Exception e){

                }
            } while(cursor.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);
        }

        for(int i = 0; i < dirList.size(); i++){
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if(imageList == null){
                continue;
            }
            for (File imagePath : imageList) {
                try {

                    if (imagePath.isDirectory()) {
                        imageList = imagePath.listFiles();
                    }
                    if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                            ) {


                        String path = imagePath.getAbsolutePath();
                        resultIAV.add(path);

                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return resultIAV;

    }

    private static boolean hasPermissions(Context context, String... permissions){
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if(ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED)
                    return false;
            }
        }
        return true;
    }
}
