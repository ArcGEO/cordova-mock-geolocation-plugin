/*global cordova, module*/

module.exports = {
  setMock: function (params, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "MockGeolocation", "setMock", params);
  }
};