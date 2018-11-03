import Flutter
import UIKit
import AVFoundation

public class SwiftFlutterAudioPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_audio", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterAudioPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    let arguments = call.arguments as! Dictionary<String, String>
    let path = arguments["path"]!
    if call.method == "playSound"{
      audio.playSound(path: path)
    }
  }
}

let audio = AudioTest()

class AudioTest{
  
  var player: AVAudioPlayer? = nil
  
  func playSound(path: String){
    let path = Bundle.main.path(forResource: path, ofType: nil, inDirectory: "/flutter_assets")!
    let url = URL(fileURLWithPath: path)
    do {
      print("Setting up sound")
      try AVAudioSession.sharedInstance().setCategory(AVAudioSessionCategoryAmbient)
      try AVAudioSession.sharedInstance().setActive(true)
    
      print("This is MP3")
      player = try AVAudioPlayer(contentsOf: url, fileTypeHint: AVFileType.mp3.rawValue)
      
      print("Checking player status")
      guard let player = player else { return }
      player.numberOfLoops = -1
      
      print("Playing sound")
      player.play()

    } catch let error {
        print(error.localizedDescription)
    }
  }
}

