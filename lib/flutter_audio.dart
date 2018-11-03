import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAudio {
  static const MethodChannel _channel = const MethodChannel('flutter_audio');

  static Future<String> playSound(String path) async {
    final Map<String, dynamic> params = <String, dynamic> {
      "path": path
    };
    return await _channel.invokeMethod('playSound', params);
  }
}
