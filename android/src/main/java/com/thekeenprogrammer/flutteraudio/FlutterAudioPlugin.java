package com.thekeenprogrammer.flutteraudio;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import android.media.MediaPlayer;
import java.net.URL;

/** FlutterAudioPlugin */
public class FlutterAudioPlugin implements MethodCallHandler {
  private static Registrar mRegistrar;
  MediaPlayer mediaPlayer = new MediaPlayer();
  
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    FlutterAudioPlugin.mRegistrar = registrar;
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_audio");
    channel.setMethodCallHandler(new FlutterAudioPlugin());
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if(call.method.equals("playSound")){
      System.out.println("=====");
      String fileLocation = call.argument("path");//"assets/Jingle_Bells.mp3";
      System.out.println("File location is: " + fileLocation);
      AssetManager assetManager = FlutterAudioPlugin.mRegistrar.context().getAssets();
      String key = FlutterAudioPlugin.mRegistrar.lookupKeyForAsset(fileLocation);
      AssetFileDescriptor fd;
      try {
          fd= assetManager.openFd(key);
          mediaPlayer.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength());
          fd.close();
          mediaPlayer.prepare();
          mediaPlayer.start();
          result.success("played successfully");
      }
      catch (Exception e){
        result.success("playing failed");
      }
    }
  }
}
