package com.example.vgvideoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class VideoList extends AppCompatActivity {
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        listView=findViewById(R.id.lv);

        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        ArrayList<File> VideosList = listsongs(Environment.getExternalStorageDirectory());
                        String [] items = new String[VideosList.size()];
                        for(int i=0;i<VideosList.size();i++){
                            items[i] = VideosList.get(i).getName().replace(".mp4", "");
                        }
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(VideoList.this, android.R.layout.simple_list_item_1,items);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent=new Intent(VideoList.this,MainActivity.class);
                                String currentSong = listView.getItemAtPosition(position).toString();
                                intent.putExtra("songList", VideosList);
                                intent.putExtra("currentSong",currentSong);
                                intent.putExtra("position",position);

                                startActivity(intent);
                            }
                        });


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(VideoList.this, "permissiton not given", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                })
        .check();

    }
    //method
    private ArrayList<File> listsongs(File file){
        ArrayList Song= new ArrayList();
        File [] songs= file.listFiles();
        if(songs !=null){
            for(File myFile: songs){
                if(!myFile.isHidden() && myFile.isDirectory()){
                    Song.addAll(listsongs(myFile));
                }
                else{
                    if(myFile.getName().endsWith(".mp4") && !myFile.getName().startsWith(".")){
                        Song.add(myFile);
                    }
                }
            }
        }

        return Song;

    }
}