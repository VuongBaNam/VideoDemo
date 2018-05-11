package com.example.nam.quanlyhanghoa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "mylog";
    String url = "https://r2---sn-42u-i5oer.googlevideo.com/videoplayback?ipbits=0&itag=22&source=youtube&ratebypass=yes&dur=393.647&key=cms1&ei=SYL1WprXDYr2VL-uj7gF&sparams=dur,ei,expire,id,initcwndbps,ip,ipbits,ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&fvip=2&lmt=1523461923484943&mime=video/mp4&signature=778A3626625B41942F81CC9FDA73E6BD0D040DCB.77D511920ED6F682C56044DAF1AC54ACAF3A43DA&pl=23&ip=37.187.92.18&requiressl=yes&id=o-AMbXA0gRnt9ZwWjKM25boZefvT0f3KMgcrZsbm7D_h-l&expire=1526060713&c=WEB&title=Coi+C%E1%BA%A5m+C%C6%B0%E1%BB%9Di+%7C+P9+T%E1%BB%9B+mu%E1%BB%91n+%C4%91%C3%A1nh+r%E1%BA%AFm+qu%C3%A1+r%E1%BB%93i+Khoai+%C6%A1i+%7C+L%E1%BB%9Bp+h%E1%BB%8Dc+b%C3%A1+%C4%91%E1%BA%A1o+xem+m%C3%A0+c%C6%B0%E1%BB%9Di+l%E1%BB%99n+ru%E1%BB%99t+lu%C3%B4n&redirect_counter=1&rm=sn-25g6r7l&req_id=2bcc936c5732a3ee&cms_redirect=yes&ipbypass=yes&mip=42.113.249.250&mm=31&mn=sn-42u-i5oer&ms=au&mt=1526039135&mv=m";
    VideoDownloadAndPlayService video ;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/video.mp4";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = (VideoView)findViewById(R.id.Video);
        videoView.setMediaController(new MediaController(this));

        isStoragePermissionGranted();

        video = VideoDownloadAndPlayService.startServer(this, url, path, "localhost", new VideoDownloadAndPlayService.VideoStreamInterface() {
            @Override
            public void onServerStart(String videoStreamUrl) {
                videoView.setVideoURI(Uri.parse(videoStreamUrl));
                videoView.requestFocus();
                videoView.start();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {
                Log.v(TAG,"Permission is revoked");
                requestPermissions( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}
