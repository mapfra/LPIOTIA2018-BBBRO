angular.module('app', ['uiSwitch']).controller('MainController', function($scope,$http,$interval,$q) {

	// add contains method to String
	String.prototype.contains = function(it) { return this.indexOf(it) != -1; };

	var canceler = $q.defer();
	var cameraCanceler = $q.defer();
	var timerForDevicesUpdate = null;

	$scope.devices = [];
	$scope.cams = [];
	$scope.hideHlsVideo = true;
	$scope.hideMjpegVideo = false;
	$scope.hls = null;
	$scope.mjpegPlayer = null;
	$scope.credentials ='';

	$scope.imgPath = "dot.png";

	$scope.urlBase = window.location.protocol + "//" + window.location.host;
	
	$scope.cseContext = "~/in-cse/in-name";
	
	$scope.load = function() {
		var req = {
				method: 'GET',
				url: $scope.urlBase + '/Home_Monitoring_Application/in-cse/context',
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

	// blacklist module  polling interval in ms
	var blModules = ["runMode","streaming","colour","colourSaturation","faultDetection"];

	// fast polling interval in ms
	var fastModulePolling = 3000;
	var fastModules = ["binarySwitch","energyConsumption","lock"];

	$scope.moduleFilter = function (module) { 
		return module.value !== ''; 
	};

	$scope.switchFilter = function (module) {
		var datapoints = module.datapoints;
		$scope.count = $scope.count+1;
		if (datapoints.length != 0) {
			for (i=0; i<datapoints.length; i++) {
				if (datapoints[i].name === 'powSe') {
					module.state = !(datapoints[i].value === 'false');
					if (module.state == module.newState) {
						module.hideSpinning = true;
					}
					return true;
				} else if (datapoints[i].name === 'dooLk') {
					module.state = !(datapoints[i].value === 'true');
					if (module.state == module.newState) {
						module.hideSpinning = true;
					}
					return true;
				}
			}
		}
		return false; 
	};

	//filter to remove any device which contains a streaming module from the display device list
	$scope.deviceFilter = function (device) { 
		for (i = 0; i<device.modules.length; i++) {
			if (device.modules[i].name === 'streaming') {
				return false;
			}
		}
		return true;
	};

	/*************************************************/
	$scope.getDevices = function() {
		var req = {
				method: 'GET',
				url: $scope.urlBase + '/' + $scope.cseContext 
					+ '?fu=1&lbl=object.type/device',
				headers: {
					'Content-Type': 'application/xml',
					'X-M2M-Origin': $scope.credentials
				}
		};
		$http(req).success(function (response, status, headers, config)  {
			var x2js = new X2JS();
			var jsonData = x2js.xml_str2json(response);
			var key = $scope.getRootKey(jsonData);
			var devices;
			if (jsonData[key].__text != undefined || jsonData[key].__text != '' ) {
				devices = jsonData[key].__text.split(" ");
			}
			$scope.removeOldDevices(devices);

			// get devices 			
			var newDevices = $scope.getNewDevices(devices);
			for (i=0; i<newDevices.length; i++) {
				var myDevice = newDevices[i];
				var device = {'id':'', 'name':'','desc':'','link':myDevice,
						'modules':[],'properties':[]};
				// fill device list
				$scope.devices.push (device);

				req.url = $scope.urlBase + '/~' + myDevice + '?rcn=7';
				req.data = device;
				$http(req).success(function (response, status, headers, config)  {
					var x2js = new X2JS();
					var jsonData = x2js.xml_str2json(response);
					var key = $scope.getRootKey(jsonData);

					var label = jsonData[key].lbl;
					var id = $scope.getIdFromLabel (label);
					config.data.id = id;
					config.data.name = $scope.getNameFromLabel(label);
					config.data.desc = jsonData[key].cnd;

					var tags = jsonData[key];
					for (tagKey in tags) {
						// starts with prop
//						if (tagKey.lastIndexOf('prop', 0) === 0) {
							if (typeof tags[tagKey].__text !== "undefined") {
								// override name if there is a propDeviceName
								if (tagKey === 'devNe') {
									config.data.name = tags[tagKey].__text;
								}
								var propName = tagKey;
								config.data.properties.push({'name':propName,
									'value':tags[tagKey].__text});
							}
//						}
					}

					req.url = $scope.urlBase + '/' + $scope.cseContext 
						+ '?fu=1&lbl=object.type/module&lbl=device.id/' + id;
					req.data = config.data;

					// get all the modules for the given device
					$scope.getModules(req);
				}).error(function (response, status, headers, config) {
					console.log("error getNewDevices " + response);
				});
			}
		}).error(function (response, status, headers, config) {
			console.log("error getDevices " + response);
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};

	$scope.getModules = function (req) {
		$http(req).success(function (response, status, headers, config)  {
			var x2js = new X2JS();
			var jsonData = x2js.xml_str2json(response);	
			var key = $scope.getRootKey(jsonData);
			var modules = [];
			if (config.data.desc == 'org.onem2m.home.device.deviceCamera') {
				if (typeof jsonData[key].__text !== 'undefined') {
					modules = jsonData[key].__text.split(" ");
					if ((modules.length > 1) || modules[0].contains('org.onem2m.home.moduleclass.streaming')) {
						for (i=0; i<modules.length; i++) {
							// create the module object and push it in the device array
							var url = $scope.urlBase + '/~' + modules[i] + '?rcn=7';
							var module= {'id':'','name':'','colorClass':'',
									'datapoints':[],'actions':[],'img':'','value':'',
									'interval': {},'started':false,'url':url,
									'deviceName':config.data.name,'hideSpinning':true};
							config.data.modules.push(module);
							var moduleReq = {
									method: 'GET',
									url: '',
									headers: {
										'Content-Type': 'application/xml',
										'X-M2M-Origin':$scope.credentials
									}
							};
							moduleReq.url = url;
							moduleReq.data = module;
							$scope.getModule(moduleReq);
						}						
					} else {
						$interval(function() { $scope.getModules(req); }, 2000, 1);
					}
				} else {
					$interval(function() { $scope.getModules(req); }, 2000, 1);
				}
			} else if (jsonData[key].__text != undefined || jsonData[key].__text != '' ) {
				modules = jsonData[key].__text.split(" ");
				for (i=0; i<modules.length; i++) {
					// create the module object and push it in the device array
					var url = $scope.urlBase + '/~' + modules[i] + '?rcn=7';
					var module = {'id':'','name':'','colorClass':'','datapoints':[],
							'actions':[],'img':'','value':'','interval': {},'started':false,
							'url':url,'deviceName':config.data.name,'hideSpinning':true};
					config.data.modules.push(module);
					var moduleReq = {
							method: 'GET',
							url: '',
							headers: {
								'Content-Type': 'application/xml',
								'X-M2M-Origin':$scope.credentials
							}
					};
					moduleReq.url = url;
					moduleReq.data = module;
					$scope.getModule(moduleReq);
				}
			} else {
				$interval(function() { $scope.getModules(req); }, 3000, 1);
			}
		}).error(function (response, status, headers, config)  {
			console.log("error getModules " + response);
		});
	}

	$scope.getModule = function (req) {
		$http(req).success(function (response, status, headers, config)  {
			var x2js = new X2JS();
			var jsonData = x2js.xml_str2json(response);
			var key = $scope.getRootKey(jsonData);
			var root = jsonData[key];

			// fill the module name
			var tab = root.cnd.split(".");
			var moduleName = tab[tab.length -1];
			var module = config.data;
			var label = root.lbl;
			var id = $scope.getPidFromLabel (label);
			module.id = id;
			module.name = moduleName;
			module.img = 'images/'+$scope.getImageModule(moduleName);
			// fill the class with the module name to define the text color. see css file.
			module.colorClass = tab[tab.length -1];

			module.datapoints = [];
			module.actions = [];

			// create the attributes
			for (var childKey in root) {
				var datapoint = {'name':childKey,'value':root[childKey]};
				module.datapoints.push(datapoint);
				if (childKey === $scope.getPropValueModule(module.name)) {
					module.value =	root[childKey];
				}
				if (root[childKey]._ty != null) {
					var action = {'name':root[childKey]._rn,'value':''};
					module.actions.push(action);
				}
			}

			if (module.name === 'streaming') {
				var index = $scope.getCamModuleIndex(module.id);
				if (index == -1) {
					$scope.cams.push(module);
				}
				if ($scope.cams.length == 1) {
					$scope.loadWebcam(config.data);
				}
			}
			if (!module.started && ! $scope.arrayContains(blModules,module.name)) {
				var intervalPolling = defaultModulePolling;
				if ($scope.arrayContains(fastModules,module.name)) {
					intervalPolling = fastModulePolling;
				} 
				var moduleReq = {
						method: 'GET',
						url: config.url,
						headers: {
							'Content-Type': 'application/xml',
							'X-M2M-Origin':$scope.credentials
						}
				};
				moduleReq.data = module;
				module.interval = $interval(function() { $scope.getModule(moduleReq); }, intervalPolling);
				module.started = true;
			}
		}).error(function (response, status, headers, config)  {
			console.log("error getModule " + response);
		});
	}
	
	// called when the user clicks on the witch widget in the HMI
	$scope.changeState = function(device,switchModule) {
		var req;
		var openOnly = $scope.getPropValueFromDevice(device, "opeOy");
		console.log("openOnly: " + openOnly);
		switchModule.hideSpinning = false;

		if (switchModule.name === 'lock') {
			if (switchModule.state == true) {
				switchModule.newState = true;
				// switch on
				req = {
						method : 'PUT',
						url : switchModule.url,
						data : '{\"hd:lock\": {\"dooLk\": \"false\"}}',
						headers : {
							'Content-Type' : 'application/json',
							'X-M2M-Origin' : $scope.credentials
						}
				};
				$http(req).success(
						function(response, status, headers, config) {
							console.log("binary lock state changed to opened");
						}).error(
								function(response, status, headers, config) {
									console.log("error on lock state change action");
								});
			} else if ((openOnly == null) || (openOnly === 'false')) {
				switchModule.newState = false;
				// switch off
				req = {
						method : 'PUT',
						url : switchModule.url,
						data : '{\"hd:lock\": {\"dooLk\": \"true\"}}',
						headers : {
							'Content-Type' : 'application/json',
							'X-M2M-Origin' : $scope.credentials
						}
				};
				$http(req).success(
						function(response, status, headers, config) {
							console.log("binary lock state changed to closed");
						}).error(
								function(response, status, headers, config) {
									console.log("error on lock state change action");
								});
			}			
		} else if (switchModule.name === 'binarySwitch') {
			req = {
					method : 'PUT',
					url : switchModule.url,
					data : '{\"hd:binSh\": {\"powSe\": \"' + switchModule.state + '\"}}',
					headers : {
						'Content-Type' : 'application/json',
						'X-M2M-Origin' : $scope.credentials
					}
			};
			$http(req).success(
					function(response, status, headers, config) {
						// binarySwitchModule.state = !binarySwitchModule.state;
						var datapoints = switchModule.datapoints;
						if (datapoints.length != 0) {
							for (i=0; i<datapoints.length; i++) {
								var datapoint = datapoints[i];
								if (datapoint.name ==='powSe') {
									// update switch state
									switchModule.state = !(datapoint.value === 'false');
									if (datapoint.value === 'false') {
										datapoint.value = 'true';
									} else {
										datapoint.value = 'false';
									}
								} 
							}
						}
						switchModule.hideSpinning = true;
						console.log("binary switch state changed");
					}).error(function(response, status, headers, config) {
							switchModule.hideSpinning = true;
							console.log("error on binary switch state change action");
						});
		}
	}

	$scope.getIdFromLabel = function(label) {
		var tab = label.split(' ');
		for (i=0; i<tab.length; i++) {
			if (tab[i].contains('id/')) {
				return tab[i].replace('id/','');
			}
		}		
		return null;
	}

	$scope.getPidFromLabel = function(label) {
		var tab = label.split(' ');
		for (i=0; i<tab.length; i++) {
			if (tab[i].contains('pid/')) {
				return tab[i].replace('pid/','');
			}
		}		
		return null;
	}

	$scope.getNameFromLabel = function(label) {
		var tab = label.split(' ');
		for (i=0; i<tab.length; i++) {
			if (tab[i].contains('name/')) {
				return tab[i].replace('name/','');
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
		var imgModules = {
				'temperature':'temp.jpg',
				'noise':'noise.jpg',
				'relativeHumidity':'humidity.png',
				'atmosphericPressureSensor':'pressure.jpg',
				'extendedCarbonDioxideSensor':'co2.png'
		}
		return imgModules[moduleName];
	}

	$scope.getPropValueModule = function(moduleName) {
		var moduleFilter = {
				'temperature':'curT0',
				'noise':'noise',
				'relativeHumidity':'relHy',
				'atmosphericPressureSensor':'atmPe',
				'extendedCarbonDioxideSensor':'cDeVe'
		}
		return moduleFilter[moduleName];
	}
	
	$scope.getPropValueFromDevice = function (device, propName) {
		var result = null;
		for (var i = 0; i< device.properties.length; i++) {
			if (device.properties[i].name == propName) {
				result = device.properties[i].value;
				break;
			}					
		}
		return result;
	}

	$scope.getNewDevices = function(deviceList) {
		var registeredDevice,returnDevice,returnDeviceId;
		var newDeviceList = [];
		var found;
		for (i=0; i<deviceList.length; i++) {
			found = false;
			returnDevice = deviceList[i];
			for (j=0; j<$scope.devices.length; j++) {
				registeredDevice = $scope.devices[j];
				if (registeredDevice.link == returnDevice) {
					found = true;
					break;
				}
			}			
			if (!found) {
				newDeviceList.push(returnDevice);
			}
		}		
		return newDeviceList;		
	}

	$scope.removeOldDevices = function(deviceList) {
		var registeredDevice,returnDevice,returnDeviceId;
		var sortedDeviceList = [];
		var found;
		for (i=0; i<$scope.devices.length; i++) {
			found = false;
			registeredDevice = $scope.devices[i];
			for (j=0; j<deviceList.length; j++) {
				returnDevice = deviceList[j];
				if (returnDevice === registeredDevice.link) {
					found = true;
					break;
				}
			}			
			if (found) {
				sortedDeviceList.push(registeredDevice);
			} else {
				// stop requests of old modules
				for (k = 0; k<registeredDevice.modules.length; k++) {
					var module = registeredDevice.modules[k];
					if (module.interval != {}) {
						$interval.cancel(module.interval);
					}
					var index = $scope.getCamModuleIndex(module.id);
					if (index != -1) {
						var newCams = [];
						for(var i = 0; i< $scope.cams.length;i++) {
							if (i != index) {
								newCams.push($scope.cams[i]);
							}
						}
						var selectedCamDestroyed = $scope.cams[index].btnClass == 'selectedCam';
						$scope.cams = newCams;
						if (selectedCamDestroyed) {
							if ($scope.cams.length != 0) {
								// load the first cam in the array
								$scope.loadWebcam($scope.cams[0]);
							} else {
								// no more cams, stop everything
								if ($scope.mjpegPlayer != null) {
									$scope.mjpegPlayer.stop();
									$scope.mjpegPlayer = null;
								}
								if ($scope.hls != null) {
									$scope.hls.destroy();
									$scope.hls = null;
								}
								$scope.hideHlsVideo = false;
								$scope.hideMjpegVideo = false;
							}
						}
					}
					module = null;
				}
				registeredDevice = null;
			}
		}
		$scope.devices = sortedDeviceList;
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
			var url = $scope.getUrlFromStreamModule(cam);
			var format = $scope.getFormatFromStreamModule(cam);
			console.log("url/format " + url + "/" + format);
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

	$scope.getUrlFromStreamModule = function (module) {
		var datapoints = module.datapoints;
		for (i=0; i<datapoints.length; i++) {
			var datapoint = datapoints[i];
			if (datapoint.name === 'url') {
				return datapoint.value;
			} 
		}
		return null;		
	}

//	return the format value (HLS / MJPEG ) of a streaming module
	$scope.getFormatFromStreamModule = function (module) {
		var datapoints = module.datapoints;
		for (i=0; i<datapoints.length; i++) {
			var datapoint = datapoints[i];
			if (datapoint.name === 'frmt') {
				return datapoint.value;
			}
		}
		return null;		
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
	
	var init = function () {
		var req = {
				method: 'GET',
				url: '../security/cred',
				headers: {
					'Content-Type': 'application/json'
				}
		};
		$http(req).success(function (response, status, headers, config)  {
			$scope.credentials = response;
			$scope.getDevices();
			timerForDevicesUpdate = $interval(function() { $scope.getDevices(); }, devicePolling);
		});
	};

	init();
});

function jsonp_callback() {
	alert("jsonp_callback");
}
