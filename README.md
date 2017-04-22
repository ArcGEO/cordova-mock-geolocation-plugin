[![MIT license](http://img.shields.io/badge/license-MIT-brightgreen.svg)](http://opensource.org/licenses/MIT)

# Cordova Mock Geolocation Plugin

Plugin that mocks geolocation into Android device, e.g. from external GPS Bluetooth device.

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

```xml
    <uses-permission android:name="android.permission.ACCESS_MOCK_LOCATION" />
```

## API

### mockGeolocation.setMock([latitude, longitude, accuracy, altitude], successCallback, errorCallback)</code>

Mocks input parameters as your device location.

## TODO

* Add more parameters
* Add samples