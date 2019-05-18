angular.module('app', ['uiSwitch']).controller('MainController', function($scope,$http,$interval,$q,$timeout) {

	// add contains method to String
	String.prototype.contains = function(it) { return this.indexOf(it) != -1; };

	var canceler = $q.defer();
	var cameraCanceler = $q.defer();
	var timerForDevicesUpdate = null;
	var timerForNotifications = null;
    
    $scope.moduleDisplay = {
        'temperature' : [{
            'img': 'temp.jpg',
            'dataPoint': 'curT0'
        }],
		'acousticSensor': [{
            'img': 'noise.jpg',
            'dataPoint': 'louds'
        }],
		'barometer': [{
            'img': 'pressure.jpg',
            'dataPoint': 'atmPe'
        }],
		'airQualitySensor': [
            {
                'img': 'humidity.png',
                'dataPoint': 'senHy'
            },
            {
                'img': 'co2.png',
                'dataPoint': 'co2'
            }
        ],
		'contactSensor': [{
            'img': 'open_door_35.png',
            'dataPoint': 'alarm'
        }],
		'motionSensor': [{
            'img': 'motion_sensor.png',
            'dataPoint': 'alarm'
        }],
		'energyConsumption': [{
            'img': 'power_consumption.png',
            'dataPoint': 'power'
        }],
        'numberValue': [{
            'img': '',
            'dataPoint': 'numVe'
        }]
    };

	$scope.datapointsNamePerModule = {
		"brightness": ["brigs"],
		"colourSaturation": ["colSn"],
		"binarySwitch" : ["powSe"],
		"temperature" : ["minVe", "unit", "curT0", "maxVe"],
		"acousticSensor" : ["louds"],
		"airQualitySensor" : ["co2", "senHy"],
		"barometer" : ["atmPe"],
		"contactSensor" : ["alarm"],
		"sessionDescription" : ["sdp", "ur1"],
		"personSensor" : ["detPs"],
		"motionSensor" : ["alarm"],
		"colour" : ["red", "green", "blue"],
		"energyConsumption" : ["volte", "currt", "power"],
		"lock" : ["dooLk", "opeOy"],
		"battery" : ["discg", "charg", "level", "capay"],
		"doorStatus": ["dooSt"],
		"numberValue": ["numVe"]
	};

	$scope.devices = {};
	$scope.cams = [];
	$scope.hideHlsVideo = true;
	$scope.hideMjpegVideo = false;
	$scope.hls = null;
	$scope.mjpegPlayer = null;
	$scope.credentials ='';
	$scope.bearer = '';

	$scope.imgPath = "dot.png";

	$scope.urlBase = window.location.protocol + "//" + window.location.host;

	$scope.cseContext = "~/in-cse/in-name";

	$scope.name="";

	$scope.currentUrl = new URL(window.location);
	

	$scope.changeHueRGBInterval = null;
    $scope.clickedModule = {};

	$scope.load = function() {
		var req = {
			method: 'GET',
			url: $scope.urlBase + '/Home_Monitoring_Application/in-cse/context'
		};
		$http(req).success(function (response, status, headers, config)  {
			$scope.cseContext = response;
		});


	}; /*"~/in-cse/in-name"*/ /* ~/cseId/cseName */
	$scope.load();

	$scope.count = 0;

	// device polling interval
	var devicePolling = 10000;
	// default polling interval in ms
	var defaultModulePolling = 180000;

	$scope.getDevicesAsArray = function() {
		return Object.values($scope.devices);
	};

	$scope.getModulesFromDevice = function(device) {
//		console.log("getModules for " + device.name);
		return Object.values(device.modules);
	};
    
    $scope.getElementsToBeDisplayed = function(module) {
        var el = $scope.moduleDisplay[module.name]
        var toBeDisplayed = [];
        for (var i=0; i<el.length; i++) {
            if (module.datapoints[el[i].dataPoint].value != null && module.datapoints[el[i].dataPoint].value != undefined )
                toBeDisplayed.push($scope.moduleDisplay[module.name][i]);
        }   
        
		return toBeDisplayed;
	};

	$scope.moduleFilter = function (module) {
		return $scope.moduleDisplay.hasOwnProperty(module.name);
	};

	$scope.switchFilter = function (module) {
		return (module.name === 'binarySwitch') || (module.name === 'lock');
	};

	$scope.colorFilter = function (module) {
		return (module.name === 'colour');
	};

	//filter to remove any device which contains a streaming module from the display device list
	$scope.deviceFilter = function (device) {
		if (device.desc === '') {
			return false;
		}
		return !(device.desc === 'org.onem2m.home.device.deviceCamera');
	};

	/*************************************************/
	$scope.getDevices = function() {
		var req = {
			method: 'GET',
			url: $scope.urlBase + '/' + $scope.cseContext
				+ '?fu=1&drt=2&lbl=object.type/device',
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json',
				'Authorization': $scope.credentials,
				'X-M2M-Origin' : $scope.origin
			}
		};
		$http(req).success(function (response, status, headers, config)  {

			var jsonData = response;
			var key = $scope.getRootKey(jsonData);
			var devices;
			devices = jsonData[key];
			// devices is an array containing device FlexContainer resourceId

			// remove old devices
			$scope.removeOldDevices(devices);

			// treat new discovered devices
			var newDevices = $scope.getNewDevices(devices);
			// newDevices is an array containg new-device FlexContainer resourceId
			for (i=0; i<newDevices.length; i++) {
				var deviceRi = newDevices[i];

				var getDeviceReq = {
					method: 'GET',
					url: $scope.urlBase + '/~' + deviceRi + '?rcn=7',
					headers: {
						'Content-Type': 'application/json',
						'Accept': 'application/json',
						'Authorization': $scope.credentials,
						'X-M2M-Origin' : $scope.origin
					},
					deviceRi: deviceRi // add device ri in request
				};

				$http(getDeviceReq).success(function (response, status, headers, config)  {

					var device = {'id':'', 'name':'','desc':'',
							'modules':{},'properties':[], 'enable':true};
					var jsonData = response;
					var key = $scope.getRootKey(jsonData);
					var jsonDevice = jsonData[key];

					var labels = jsonDevice.lbl;
					var id = $scope.getIdFromLabel (labels);
					device.id = id;
					device.name = $scope.getNameFromLabel(labels);
					device.desc = jsonDevice.cnd;
					device.isUpdated = false;
					device.fcntRi = jsonDevice.ri;
					device.fcntaRi = config.deviceRi;
					deviceRi = config.deviceRi; // deviceRi is the address of the device in the context of INCSE
					// so it could be the "true" device ri if the device is hosted by the CSE
					// or it could be the ri of the FlexContainerAnnc object representing the device
					// In this latter case, the device is hosted somewhere else.

					for (key in jsonDevice) {
						// starts with prop
						var value = jsonDevice[key];
						if (typeof value !== "undefined") {
							// override name if there is a propDeviceAliasName
							if (key === 'pDANe') {
								device.name = value;
							}
							var propName = key;
							device.properties.push({'name':propName,
								'value':value});
						}
					}

					// add new device in devices list
					$scope.devices[deviceRi] = device;

					// get all the modules for the given device
					$scope.getModules(device);
				}).error(function (response, status, headers, config) {
					console.log("error getNewDevices " + response);
				});
			}
		}).error(function (response, status, headers, config) {
			console.log("error getDevices " + response);
			// called asynchronously if an error occurs
			// or server returns response with an error status.

			// this is not a big issue here
			// as the device will be detected again as a new device
			
			// here we should disable all devices
			for (currentDevice in $scope.devices) {
				$scope.devices[currentDevice].enable=false;
			}
		});
	};

	$scope.getModules = function (device) {

		var getModulesRiReq = {
			method: 'GET',
			url: $scope.urlBase + '/' + $scope.cseContext
			+ '?fu=1&drt=2&lbl=device.id/' + device.id,
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json',
				'Authorization': $scope.credentials,
				'X-M2M-Origin' : $scope.origin
			},
			device: device
		};

		$http(getModulesRiReq).success(function (response, status, headers, config)  {
			var jsonData = response;
			var key = $scope.getRootKey(jsonData);
			var modules = jsonData[key];
			// modules is an array. It contains module resource id

			// TODO : fix issue related to missing modules
			// in some cases (access right), some modules take time to become available.

			modules.forEach(
				function(moduleRi) {
					// retrieve module data
					$scope.getModule(config.device, moduleRi);
				}
			);

		}).error(function (response, status, headers, config)  {
			console.log("error getModules " + response);
		});
	}

	$scope.getModule = function (device, moduleRi) {

		var getModuleReq = {
			method: 'GET',
			url: $scope.urlBase + '/~' + moduleRi + '?rcn=7',
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json',
				'Authorization':$scope.credentials,
				'X-M2M-Origin' : $scope.origin
			},
			device: device
		};

		$http(getModuleReq).success(function (response, status, headers, config)  {
			var jsonData = response;
			var key = $scope.getRootKey(jsonData);
			var root = jsonData[key];

			var module = {'id':'','name':'','colorClass':'','datapoints':{},
					'actions':[],'interval': {},'started':false,
					'url':config.url,'deviceName':config.device.name,'hideSpinning':true, 'state':false};

			// fill the module name
			var tab = root.cnd.split(".");
			var moduleName = tab[tab.length -1];
			var label = root.lbl;
			var id = $scope.getPidFromLabel (label);
			module.id = id;
			module.ri = root.ri;
			module.name = moduleName;
			// fill the class with the module name to define the text color. see css file.
			module.colorClass = tab[tab.length -1];

			module.datapoints = {};
			module.actions = [];
			if (root.prROy) {
				module.isReadOnly = (root.prROy === 'true');
			} else {
				module.isReadOnly = false;
			}

			// create the attributes        
			var dpNames = $scope.datapointsNamePerModule[module.name];
			if (dpNames) {
                for (var i=0; i < dpNames.length; i++){
                    module.datapoints[dpNames[i]] = {"name": dpNames[i], "value":root[dpNames[i]]};
                }
			}

			if (module.name === 'sessionDescription') {
				var index = $scope.getCamModuleIndex(module.id);
				if (index == -1) {
					$scope.cams.push(module);
				}
				if ($scope.cams.length == 1) {
					$scope.loadWebcam(module);
				}
			}

			if (module.name === 'binarySwitch') {
				module.state = (module.datapoints.powSe.value === 'true') ;
			}

			if (module.name === 'lock') {
				module.state = (module.datapoints.dooLk.value === 'true')
			}

			if (module.name === 'brightness') {
				module.brightness = parseInt(module.datapoints.brigs.value);
//                module.colorPicker = {};
			}

			if (module.name === 'colourSaturation') {
				module.colourSaturation = parseInt(module.datapoints.colSn.value);
//                module.colorPicker = {};
			}

			if (module.name === 'colour') {
				module.colorPicker = {};
				config.device.colourModule = module;
			}

			// add module in device
			config.device.modules[module.ri] = module;

			$scope.createSubscription(root.ri);
		}).error(function (response, status, headers, config)  {
			console.log("error getModule " + response);
		});
	}

	$scope.createSubscription = function(toBeSubscribedResource) {
		req = {
			method : 'POST',
			url : $scope.urlBase + '/Home_Monitoring_Application/in-cse/context',
			data : {
				resourceId:toBeSubscribedResource
			},
			headers : {
				'Content-Type' : 'application/json'
			}
		};
		// don't care about response
		$http(req);

	}

	$scope.getNotifications = function() {
		req = {
			method : 'GET',
			url : $scope.urlBase + '/Home_Monitoring_Application/in-cse/context/notifications',
			headers : {
				'Accept' : 'application/json'
			}
		};

		$http(req).success(
			function(response, status, headers, config) {
				// for each notification --> update device & module model
				var notifications = response;
				// notifications is an array
				notifications.forEach(
					function(notification) {
						console.log(notification);
						var sgn = notification["m2m:sgn"];
						var nev = null;
						if (sgn !== null) {
							nev = sgn["m2m:nev"];
						}
						var rep = null;
						if (nev != null) {
							rep = nev["m2m:rep"];
						}

						var moduleRep = null;
						if (rep != null) {
							var key = $scope.getRootKey(rep);
							moduleRep = rep[key];
						}

						if (moduleRep != null) {
							var internalModule = $scope.getModuleByRi(moduleRep.ri, moduleRep.pi);
							console.log(internalModule);
                            
                            if (internalModule.name in $scope.datapointsNamePerModule) {
                                var dpNames = $scope.datapointsNamePerModule[internalModule.name];
                                for (var i=0; i < dpNames.length; i++){
                                    if (moduleRep.hasOwnProperty(dpNames[i])) {
                                        console.log('New ' + dpNames[i] + ' value:' + moduleRep[dpNames[i]] + '!!!!!!!!!!!!!');
                                        internalModule.datapoints[dpNames[i]].value = moduleRep[dpNames[i]];
                                    }
                                }
                            }

							if (moduleRep.powSe) {
								console.log('powSe value:' + moduleRep.powSe);
								var powSeValue = (moduleRep.powSe === 'true');
								if (internalModule.state != powSeValue) {
									internalModule.state = powSeValue;
								}
								console.log('powSe updated!!!!!!!!!!!!!!!!!');
							}

							if (moduleRep.dooLk) {
								console.log('dooLk value:' + moduleRep.dooLk);
								var dooLkValue = (moduleRep.dooLk ==='true');
								if (internalModule.state != dooLkValue) {
									internalModule.state = dooLkValue;
								}
							}

							if (moduleRep.brigs) {
								console.log('brigs value:' + moduleRep.brigs);
								var brigsValue = parseInt(moduleRep.brigs);

								var moduleParent = $scope.getDeviceByRi(moduleRep.pi);
								if(moduleParent && moduleParent.colourModule){
									var colorPicker = moduleParent.colourModule.colorPicker;
									colorPicker.setHSV([colorPicker.hsv[0], colorPicker.hsv[1], (brigsValue<100 ? brigsValue + 1  : brigsValue)/100]);
								} else if (internalModule.brightness != brigsValue) {
									internalModule.brightness = brigsValue;
								}
								console.log('Brightness updated!!!!!!!!!!!!!!!!!');
							}

							if (moduleRep.colSn) {
								console.log('colSn value:' + moduleRep.colSn);
								var colSnValue = parseInt(moduleRep.colSn);
								
								var moduleParent = $scope.getDeviceByRi(moduleRep.pi);
								if(moduleParent && moduleParent.colourModule){
									var colorPicker = moduleParent.colourModule.colorPicker;
								colorPicker.setHSV([colorPicker.hsv[0], (colSnValue<100 ? colSnValue + 1  : colSnValue)/100, colorPicker.hsv[2]]);
								} else if (internalModule.colourSaturation != colSnValue) {
									internalModule.colourSaturation = colSnValue;										
								}
								console.log('Saturation updated!!!!!!!!!!!!!!!!!');
							}

							if (moduleRep.red || moduleRep.green || moduleRep.blue) {
								var pickerRGB = internalModule.colorPicker.getRGB();
								var om2mRGB = [parseInt(internalModule.datapoints.red.value), parseInt(internalModule.datapoints.green.value), parseInt(internalModule.datapoints.blue.value)];
								if (pickerRGB[0] != om2mRGB[0] || pickerRGB[1] != om2mRGB[1] || pickerRGB[2] != om2mRGB[2])
								{
									internalModule.colorPicker.setRGB(om2mRGB[0], om2mRGB[1], om2mRGB[2]);
								}
								console.log('Color updated!!!!!!!!!!!!!!!!!');
							}

							// put background red
							// here we need to be carefull with device = moduleRep.pi
							// as we have announced device.
							device = $scope.getDeviceByRi(moduleRep.pi);
							if (device) {
								$scope.removeColor(device);
							}
						}
					}
				);
			}
		);
	}

	$scope.removeColor = function(d) {
		d.isUpdated = true;
		// remove background after 1,5s
		$timeout(
				function() {
					d.isUpdated = false;
				},
				1500
		);
	}

	// called when the user clicks on the switch widget in the HMI
	$scope.changeState = function(device,switchModule) {
		var req;
		switchModule.hideSpinning = false;

		if (switchModule.name === 'lock') {
			var openOnly = $scope.getValueFromModule(switchModule, "opeOy");
			console.log("openOnly: " + openOnly);
			if (switchModule.state || (openOnly == null) || (openOnly === 'false')) {
				var lk = switchModule.state;
				// switch on/off
				req = {
					method : 'PUT',
					url : switchModule.url,
					data : '{\"hd:lock\": {\"lock\": \"' + lk + '\"}}',
					headers : {
						'Content-Type' : 'application/json',
						'Authorization' : $scope.credentials,
						'X-M2M-Origin' : $scope.origin
					},
					valueToBeSet: lk,
					currentSwitch: switchModule
				};
				$http(req).success(function(response, status, headers, config) {
					console.log("binary lock state changed");

					// config = switchModule
					config.currentSwitch.hideSpinning = true;
					if (config.currentSwitch.state !== config.valueToBeSet) {
						config.currentSwitch.state = config.valueToBeSet;
					}

					var datapoints = config.currentSwitch.datapoints;
					datapoints.dooLk.value = config.valueToBeSet;
					console.log("door lock state changed");

				}).error(function(response, status, headers, config) {
					console.log("error on lock state change action");
					config.currentSwitch.hideSpinning = true;
					config.currentSwitch.state = !config.valueToBeSet;

				});
			}
		} else if (switchModule.name === 'binarySwitch') {
			req = {
				method : 'PUT',
				url : switchModule.url,
				data : '{\"hd:binSh\": {\"powSe\": \"' + switchModule.state + '\"}}',
				headers : {
					'Content-Type' : 'application/json',
					'Authorization' : $scope.credentials,
					'X-M2M-Origin' : $scope.origin
				},
				valueToBeSet : switchModule.state,
				currentSwitch : switchModule
			};
			$http(req).success(
				function(response, status, headers, config) {
					// binarySwitchModule.state = !binarySwitchModule.state;
					config.currentSwitch.hideSpinning = true;
					if (config.currentSwitch.state !== config.valueToBeSet) {
						config.currentSwitch.state = config.valueToBeSet;
					}

					var datapoints = config.currentSwitch.datapoints;
					datapoints.powSe.value = config.valueToBeSet;
					console.log("binary switch state changed");
				})
				.error(function(response, status, headers, config) {
					config.currentSwitch.hideSpinning = true;
					config.currentSwitch.state = !config.valueToBeSet;
					console.log("error on binary switch state change action");
				}
			);
		}
	}

	$scope.getIdFromLabel = function(labels) {
		for(label in labels) {
			var labelValue = labels[label];
			if (labelValue.contains('id/')) {
				return labelValue.replace('id/','');
			}
		}
		return null;
	}

	$scope.getPidFromLabel = function(labels) {
		for(label in labels) {
			var labelValue = labels[label];
			if (labelValue.contains('pid/')) {
				return labelValue.replace('pid/','');
			}
		}
		return null;
	}

	$scope.getNameFromLabel = function(labels) {
		for(label in labels) {
			var labelValue = labels[label];
			if (labelValue.contains('name/')) {
				return labelValue.replace('name/','');
			}
		}
		return null;
	}

	$scope.getRootKey = function(rootObj) {
		for (var key in rootObj) {
			return key;
		}
		return null;
	}

	$scope.getPropValueFromDevice = function (device, propName) {
		for (var i = 0; i< device.properties.length; i++) {
			if (device.properties[i].name == propName) {
				return device.properties[i].value;
			}
		}
		return null;
	}

	$scope.getNewDevices = function(deviceList) {
		var newDevices = [];
		deviceList.forEach(function (device) {
			// device = device resource id
			if (!$scope.devices[device]) {
				newDevices.push(device);
			} else {
				$scope.devices[device].enable=true;
			}
		});

		return newDevices;
	}

	$scope.removeOldDevices = function(deviceList) {

		for(deviceRi in $scope.devices) {
			if (!deviceList.includes(deviceRi)) {
				// the device must be removed from the $scope.devices object
				// as this device does not exist anymore.
				$scope.devices[deviceRi].enable = false;
			}
		}

//		var registeredDevice,returnDevice,returnDeviceId;
//		var sortedDeviceList = [];
//		var found;
//		for (i=0; i<$scope.devices.length; i++) {
//			found = false;
//			registeredDevice = $scope.devices[i];
//			for (j=0; j<deviceList.length; j++) {
//				returnDevice = deviceList[j];
//				if (returnDevice === registeredDevice.link) {
//					found = true;
//					break;
//				}
//			}
//			if (found) {
//				sortedDeviceList.push(registeredDevice);
//			} else {
//				// stop requests of old modules
//				for (k = 0; k<registeredDevice.modules.length; k++) {
//					var module = registeredDevice.modules[k];
//					var index = $scope.getCamModuleIndex(module.id);
//					if (index != -1) {
//						var newCams = [];
//						for(var i = 0; i< $scope.cams.length;i++) {
//							if (i != index) {
//								newCams.push($scope.cams[i]);
//							}
//						}
//						var selectedCamDestroyed = $scope.cams[index].btnClass == 'selectedCam';
//						$scope.cams = newCams;
//						if (selectedCamDestroyed) {
//							if ($scope.cams.length != 0) {
//								// load the first cam in the array
//								$scope.loadWebcam($scope.cams[0]);
//							} else {
//								// no more cams, stop everything
//								if ($scope.mjpegPlayer != null) {
//									$scope.mjpegPlayer.stop();
//									$scope.mjpegPlayer = null;
//								}
//								if ($scope.hls != null) {
//									$scope.hls.destroy();
//									$scope.hls = null;
//								}
//								$scope.hideHlsVideo = false;
//								$scope.hideMjpegVideo = false;
//							}
//						}
//					}
//					module = null;
//				}
//				registeredDevice = null;
//			}
//		}
//		$scope.devices = sortedDeviceList;
	}

	$scope.arrayContains = function (array, label) {
		for (i = 0; i < array.length; i++) {
			if (label === array[i]) {
				return true;
			}
		}
		return false;
	}

	$scope.loadWebcam = function (cam) {
		for ( var i = 0; i < $scope.cams.length; i++) {
			$scope.cams[i].btnClass = '';
		}
		cam.btnClass = 'selectedCam';
		if ($scope.cams.length != 0) {
			var url = $scope.getValueFromModule(cam, "ur1");
			var format = $scope.getValueFromModule(cam, "sdp");
			console.log("url/format: " + url + "/" + format);
			if (format === "HLS") {
//				$scope.camera = "";
				if ($scope.hls != null) {
//					$scope.hls.stop();
					$scope.hls.destroy();
					$scope.hls = null;
				}

				if ($scope.mjpegPlayer != null) {
					$scope.mjpegPlayer.stop();
					$scope.mjpegPlayer = null;
				}

				$scope.hideHlsVideo = false;
				$scope.hideMjpegVideo = true;
				var playerElement = document.getElementById("clappr");
				$scope.hls = new Clappr.Player({
					source: url,
					mute: true,
					height: 360,
					width: 480
				});
				$scope.hls.attachTo(playerElement);
				$scope.hls.play();

			} else if (format === "MJPEG") {
				if ($scope.hls != null) {
//					$scope.hls.stop();
					$scope.hls.destroy();
					$scope.hls = null;
				}

				if ($scope.mjpegPlayer != null) {
					$scope.mjpegPlayer.stop();
					$scope.mjpegPlayer = null;
				}

				$scope.mjpegPlayer = new MJPEG.Player("player", url);
				$scope.mjpegPlayer.start();

				$scope.hideHlsVideo = true;
				$scope.hideMjpegVideo = false;
			}
		}
	}

	$scope.getValueFromModule = function (module, propName) {
		return module.datapoints[propName].value;
	}

	$scope.getCamModuleIndex = function (moduleId) {
		for (var i=0; i<$scope.cams.length; i++) {
			if (moduleId == $scope.cams[i].id) {
				return i;
			}
		}
		return -1;
	}

	$scope.test = function() {
		$scope.mjpegPlayer.stop();
	}

	$scope.test2 = function(device,switchModule) {
		$scope.hide=true;
	}

	$scope.getModuleByRi = function(moduleResourceId, deviceResourceId) {
		device = $scope.getDeviceByRi(deviceResourceId);
		module = null;
		if (device) {
			module = device.modules[moduleResourceId]
		}
		return module;
	}

	$scope.getDeviceByRi = function (deviceResourceId) {
		device =  $scope.devices[deviceResourceId];
		if (!device) {
			// try to find device by trueRi
			for(dri in $scope.devices) {
				currentDevice = $scope.devices[dri];
				if (currentDevice.fcntRi === deviceResourceId) {
					device = currentDevice;
					break;
				}
			}
		}

		return device;
	}


    $scope.startHueRGBChange = function(module) {

        $scope.clickedModule = module;

        // bind to mouseup event to stopHueRGBChange
        angular.element(document).bind('mouseup', $scope.stopHueRGBChange);
        angular.element(document).bind('mouseleave', $scope.stopHueRGBChange);

        // start interval
        if($scope.changeHueRGBInterval !== null || $scope.changeHueRGBInterval !== undefined) {
            $interval.cancel($scope.changeHueRGBInterval);
        }
        $scope.changeHueRGBInterval = $interval($scope.changeHueRGB, 1000);

    }

    $scope.stopHueRGBChange = function() {
        angular.element(document).unbind('mouseup', $scope.stopHueRGBChange);
        angular.element(document).unbind('mouseleave', $scope.stopHueRGBChange);
        if($scope.changeHueRGBInterval !== null || $scope.changeHueRGBInterval !== undefined) {
            $interval.cancel($scope.changeHueRGBInterval);
        }
        $scope.changeHueRGBInterval = null;
        $scope.changeHueRGB();
    }

    $scope.changeHueRGB = function() {
        var newColor = document.getElementById('picker'+ $scope.clickedModule.id +"_color").style.backgroundColor;
        console.log("Setting new Hue RGB" + newColor);

		var colourModule = $scope.clickedModule;
		var rgb = colourModule.colorPicker.getRGB();
		var req = {
			method : 'PUT',
			url : colourModule.url,
			data : '{"hd:color": {"red": "' + rgb[0] + '","green": "' + rgb[1] + '","blue": "' + rgb[2] + '"}}',
			headers : {
				'Content-Type' : 'application/json',
				'Authorization' : $scope.credentials,
				'X-M2M-Origin' : $scope.origin
			},
			valueToBeSet : colourModule.state,
			currentModule : colourModule
		};
		$http(req).success(
			function(response, status, headers, config) {
				// binarySwitchModule.state = !binarySwitchModule.state;
				config.currentModule.hideSpinning = true;
				if (config.currentModule.state !== config.valueToBeSet) {
					config.currentModule.state = config.valueToBeSet;
				}
				console.log("color changed");
			})
			.error(function(response, status, headers, config) {
				config.currentModule.hideSpinning = true;
				config.currentModule.state = !config.valueToBeSet;
				console.log("error on color change action");
			}
			);
    }

	$scope.picker_id = function (module) {

		var pickerId = "picker" + module.id;
		return pickerId;
	}

	$scope.color_jq = function (module) {

		var global_number_of_lights = module.id;
		var newHueName = 'global_Value_picker'+global_number_of_lights+'_hue';

		if(window[newHueName]=== undefined)
			window[newHueName] = 0.99;

		$(document).ready(function() {
			$('#picker'+global_number_of_lights).colorPicker('#picker'+global_number_of_lights+'_color');
			module.colorPicker = $.colorPicker('#picker'+global_number_of_lights);
			if(module.colorPicker.color === null)
				module.colorPicker.setRGB(module.datapoints.red.value, module.datapoints.green.value, module.datapoints.blue.value);
		});
	}


	var init = function () {
		var req = {
			method: 'GET',
			url: '../security/cred',
			headers: {
				'Content-Type': 'application/json'
			}
		};
		$http(req).success(function (response, status, headers, config)  {
			if (response.credentials) {
				$scope.credentials = 'Basic ' + window.btoa(response.credentials);
				$scope.origin = response.credentials;
			} else if (response.bearer){
				$scope.credentials = 'Bearer ' + response.bearer;
				$scope.origin = response.clientId;
			}
			//$scope.credentials = response.credentials;
			//$scope.bearer = response.bearer;
			$scope.name = response.name;
			$scope.getDevices();
			timerForDevicesUpdate = $interval(function() { $scope.getDevices(); }, devicePolling);
			timerForNotifications = $interval(function() { $scope.getNotifications(); }, 3000);
		});
	};

	init();

}).directive('numberFormatter', function() {
        return {
            restrict: 'A',
            require: '^ngModel',
            link: function(scope, element, attrs, ngModel) {
            	if(ngModel !== undefined) {
                    ngModel.$parsers.push(function (val) {
                        return Number(val);
                    });
                    ngModel.$formatters.push(function (val) {
                        return Number(val);
                    });
                }
            }
        };
    }
);



function jsonp_callback() {
	alert("jsonp_callback");
}
