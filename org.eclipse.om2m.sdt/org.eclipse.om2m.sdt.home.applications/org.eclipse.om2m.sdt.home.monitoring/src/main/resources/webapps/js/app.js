angular.module('app', ['uiSwitch']).controller('MainController', function($scope,$http,$interval,$q,$timeout) {

	// add contains method to String
	String.prototype.contains = function(it) { return this.indexOf(it) != -1; };

	var canceler = $q.defer();
	var cameraCanceler = $q.defer();
	var timerForDevicesUpdate = null;
	var timerForNotifications = null;
	
	$scope.imgModules = {
		'temperature':'temp.jpg',
		'noise':'noise.jpg',
		'relativeHumidity':'humidity.png',
		'atmosphericPressureSensor':'pressure.jpg',
		'extendedCarbonDioxideSensor':'co2.png',
		'contactSensor':'open_door_35.png',
		'motionSensor':'motion_sensor.png',
		'energyConsumption': 'power_consumption.png'
	};
	
	$scope.moduleFilterDefinition = {
		'temperature':'curT0',
		'noise':'noise',
		'relativeHumidity':'relHy',
		'atmosphericPressureSensor':'atmPe',
		'extendedCarbonDioxideSensor':'cDeVe',
		'contactSensor':'alarm',
		'motionSensor':'alarm',
		'energyConsumption':'power',
		'numberValue': 'numVe'
	};
	
	$scope.datapointsNamePerModule = {
		"binarySwitch" : ["powSe"],
		"temperature" : ["minVe", "unit", "curT0", "maxVe"],
		"noise" : ["noise"],
		"extendedCarbonDioxideSensor" : ["cDeVe", "alarm"],
		"atmosphericPressureSensor" : ["atmPe"],
		"relativeHumidity" : ["relHy"],
		"contactSensor" : ["alarm"],
		"streaming" : ["frmt", "psWd", "login", "url"], 
		"personSensor" : ["detPs"],
		"motionSensor" : ["alarm"],
		"colour" : ["colour"],
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

	$scope.imgPath = "dot.png";

	$scope.urlBase = window.location.protocol + "//" + window.location.host;
	
	$scope.cseContext = "~/in-cse/in-name";
	
	$scope.name="";
	
	$scope.currentUrl = new URL(window.location);
	$scope.sessionId = $scope.currentUrl.searchParams.get("sessionId");
	
	$scope.load = function() {
		var req = {
			method: 'GET',
			url: $scope.urlBase + '/Home_Monitoring_Application/in-cse/context',
			params: {sessionId: $scope.sessionId}
		};
		$http(req).success(function (response, status, headers, config)  {
			$scope.cseContext = response;
		});
		
	
	}; /*"~/dt-in-cse/dt-in-name"*/ /* ~/cseId/cseName */
	$scope.load();

	$scope.count = 0;

	// device polling interval
	var devicePolling = 10000;
	// default polling interval in ms
	var defaultModulePolling = 180000;

//	// blacklist module  polling interval in ms
//	var blModules = ["runMode","streaming","colour","colourSaturation","faultDetection"];
//
//	// fast polling interval in ms
//	var fastModulePolling = 3000;
//	var fastModules = ["binarySwitch","energyConsumption","lock"];
	
	$scope.getDevicesAsArray = function() {
		return Object.values($scope.devices);
	};
	
	$scope.getModulesFromDevice = function(device) {
		console.log("getModules for " + device.name);
		return Object.values(device.modules);
	};

	$scope.moduleFilter = function (module) { 
		return module.value !== ''; 
	};

	$scope.switchFilter = function (module) {
		return (module.name === 'binarySwitch') || (module.name === 'lock');
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
				'X-M2M-Origin': $scope.credentials
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
						'X-M2M-Origin': $scope.credentials
					}, 
					deviceRi: deviceRi // add device ri in request
				};

				$http(getDeviceReq).success(function (response, status, headers, config)  {
					
					var device = {'id':'', 'name':'','desc':'',
							'modules':{},'properties':[]};
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
				'X-M2M-Origin': $scope.credentials
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
				'X-M2M-Origin':$scope.credentials
			},
			device: device
		};
		
		$http(getModuleReq).success(function (response, status, headers, config)  {
			var jsonData = response;
			var key = $scope.getRootKey(jsonData);
			var root = jsonData[key];
			
			var module = {'id':'','name':'','colorClass':'','datapoints':{},
					'actions':[],'img':'','value':'','interval': {},'started':false,
					'url':config.url,'deviceName':config.device.name,'hideSpinning':true, 'state':false};

			// fill the module name
			var tab = root.cnd.split(".");
			var moduleName = tab[tab.length -1];
			var label = root.lbl;
			var id = $scope.getPidFromLabel (label);
			module.id = id;
			module.ri = root.ri;
			module.name = moduleName;
			module.img = 'images/' + $scope.getImageModule(moduleName);
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
				dpNames.forEach(
					function(dpName) {
						module.datapoints[dpName] = {"name": dpName, "value":root[dpName]};
					}
				);
			}
			
			var propName = $scope.getPropValueModule(moduleName);
			if (propName) {
				module.value = module.datapoints[propName].value;	
			}
			 
			if (module.name === 'streaming') {
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
				resourceId:toBeSubscribedResource,
				sessionId: $scope.sessionId
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
			params: {sessionId: $scope.sessionId},
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
							
							var propValueModule = $scope.getPropValueModule(internalModule.name);
							if (propValueModule) {
								var value = moduleRep[propValueModule];
								if (internalModule.value) {
									internalModule.value = value;
								}
							}
							
							if (moduleRep.powSe) {
								console.log('powSe value:' + moduleRep.powSe);
								var datapoints = internalModule.datapoints;
								var powSeValue = (moduleRep.powSe === 'true');
								datapoints.powSe.value = powSeValue;
								if (internalModule.state != powSeValue) {
									internalModule.state = powSeValue;
								}
								console.log('powSe updated!!!!!!!!!!!!!!!!!');
							}
							
							if (moduleRep.dooLk) {
								console.log('dooLk value:' + moduleRep.dooLk);
								var datapoints = internalModule.datapoints;
								var dooLkValue = (moduleRep.dooLk ==='true');
								datapoints.dooLk.value = dooLkValue;
								if (internalModule.state != dooLkValue) {
									internalModule.state = dooLkValue;
								}
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
	
	// called when the user clicks on the witch widget in the HMI
	$scope.changeState = function(device,switchModule) {
		var req;
		switchModule.hideSpinning = false;

		if (switchModule.name === 'lock') {
			var openOnly = $scope.getValueFromModule(switchModule, "opeOy");
			console.log("openOnly: " + openOnly);
			if (switchModule.state || (openOnly == null) || (openOnly === 'false')) {
				switchModule.newState = switchModule.state;
				var lk = switchModule.state;
				// switch on/off
				req = {
					method : 'PUT',
					url : switchModule.url,
					data : '{\"hd:lock\": {\"dooLk\": \"' + lk + '\"}}',
					headers : {
						'Content-Type' : 'application/json',
						'X-M2M-Origin' : $scope.credentials
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
			switchModule.newState = switchModule.state;
			req = {
				method : 'PUT',
				url : switchModule.url,
				data : '{\"hd:binSh\": {\"powSe\": \"' + switchModule.state + '\"}}',
				headers : {
					'Content-Type' : 'application/json',
					'X-M2M-Origin' : $scope.credentials
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

	$scope.getImageModule = function(moduleName) {
		return $scope.imgModules[moduleName];
	}

	$scope.getPropValueModule = function(moduleName) {
		var propName = $scope.moduleFilterDefinition[moduleName];
		return propName;
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
			}
		});
		
		return newDevices;
	}

	$scope.removeOldDevices = function(deviceList) {
		
		for(deviceRi in $scope.devices) {
			if (!deviceList.includes(deviceRi)) {
				// the device must be removed from the $scope.devices object
				// as this device does not exist anymore.
				delete $scope.devices[deviceRi];
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
			var url = $scope.getValueFromModule(cam, "url");
			var format = $scope.getValueFromModule(cam, "frmt");
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
	
	var init = function () {
		var req = {
			method: 'GET',
			url: '../security/cred',
			params : {sessionId: $scope.sessionId},
			headers: {
				'Content-Type': 'application/json'
			}
		};
		$http(req).success(function (response, status, headers, config)  {
			$scope.credentials = response.credentials;
			$scope.name = response.name;
			$scope.getDevices();
			timerForDevicesUpdate = $interval(function() { $scope.getDevices(); }, devicePolling);
			timerForNotifications = $interval(function() { $scope.getNotifications(); }, 3000);
		});
	};

	init();
});

function jsonp_callback() {
	alert("jsonp_callback");
}
