[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](http://opensource.org/licenses/MIT)

Tested on: Android 4.4.2, Android 6.0

# Cordova Mock Geolocation Plugin

Plugin that mocks geolocation into Android device, e.g. from external GPS Bluetooth device. Plugin has been tested on Android 4.4.2 and Android 6.0

## Using
    
Install the plugin:

    $ cordova plugin add https://github.com/ArcGEO/cordova-mock-geolocation-plugin.git   

In your cordova application:

```js
    var latitude = -26.902038;
    var longitude = -48.671337;
    var accuracy = 1;
    var altitude = 0;
    
    mockGeolocation.setMock([latitude, longitude, accuracy, altitude], function(suc){
          console.log(suc);
        }, function(err){
          console.log(err);
        });
```

## Permissions

For Android Marshmellow, you need to allow your app to mock location (in developer tools).

Follow: [Allow mock location on Android M](https://www.youtube.com/watch?v=CWfIBqjP1kA)

## API

### mockGeolocation.setMock([latitude, longitude, accuracy, altitude], successCallback, errorCallback)</code>

Mocks input parameters as your device location.

## TODO

* Add more parameters
* Add samples