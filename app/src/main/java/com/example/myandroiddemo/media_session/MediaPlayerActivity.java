package com.example.myandroiddemo.media_session;

import android.content.ComponentName;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myandroiddemo.R;
import com.example.myandroiddemo.service.PlayerActivity;
import com.google.android.material.slider.Slider;

//https://developer.android.com/guide/topics/media-apps/audio-app/building-a-mediabrowser-client?hl=zh-cn
public class MediaPlayerActivity extends AppCompatActivity {
    private static final String TAG = "MediaPlayerActivity";

    private MediaBrowserCompat mediaBrowser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
//        mediaBrowser = new MediaBrowserCompat(this,new ComponentName("com.qiyi.video.iv","com.iqy.iv.player.mediasession.MediaSessionController"),connectionCallbacks,null);
        mediaBrowser = new MediaBrowserCompat(this,new ComponentName(this,MediaPlaybackService.class),connectionCallbacks,null);

    }

    public void play(View view) {
        Log.i(TAG, "play: ");
//        int pbState = MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getPlaybackState().getState();
//        if (pbState == PlaybackStateCompat.STATE_PLAYING) {
//            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().pause();
//        } else {
            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().play();
//        }
    }

    public void pause(View view) {
        Log.i(TAG, "pause: ");
        MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().pause();
    }
    public void next(View view) {
        Log.i(TAG, "next: ");
        MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().skipToNext();
    }
    public void previews(View view) {
        Log.i(TAG, "previews: ");
        MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().skipToPrevious();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mediaBrowser.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // (see "stay in sync with the MediaSession")
        if (MediaControllerCompat.getMediaController(MediaPlayerActivity.this) != null) {
            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).unregisterCallback(controllerCallback);
        }
        mediaBrowser.disconnect();
    }

    MediaControllerCompat.Callback controllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    Log.i(TAG, "onMetadataChanged: ");
                }

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    Log.i(TAG, "onPlaybackStateChanged: ");
                }
            };

    private final MediaBrowserCompat.ConnectionCallback connectionCallbacks = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {
            super.onConnected();
            Log.i(TAG, "onConnected: ");
            MediaSessionCompat.Token token = mediaBrowser.getSessionToken();
            MediaControllerCompat mediaController = new MediaControllerCompat(MediaPlayerActivity.this,token);
            MediaControllerCompat.setMediaController(MediaPlayerActivity.this,mediaController);
            buildTransportControls();
        }

        @Override
        public void onConnectionSuspended() {
            super.onConnectionSuspended();
            Log.i(TAG, "onConnectionSuspended: ");
        }

        @Override
        public void onConnectionFailed() {
            super.onConnectionFailed();
            Log.i(TAG, "onConnectionFailed: ");
        }
    };

    private void buildTransportControls() {

    }
}
