//add contains method to String
String.prototype.contains = function(it) { return this.indexOf(it) != -1; };

//angular module & controller
var gemDelegate = angular.module('gemDelegate', ['angularBootstrapNavTree','ngAnimate']);

gemDelegate.controller('delegationController', function($scope, $http, $window) {

	var tree;

	var filteredElements = ["ty", "ri", "pi", "ct", "lt", "lbl", "cnd",
		"acpi", "et", "owner", "_xmlns:m2m", "_xmlns:hd", "_rn", "__prefix" ];

	$scope.my_tree = tree = {};
	$scope.my_tree2 = tree2 = {};
	$scope.devices = [];

	$scope.imgPath = "dot.png";

	$scope.urlBase = window.location.protocol + "//" + window.location.host;

	$scope.my_data = [];

	$scope.my_data2 = [];
	
	$scope.context = $window.context;

	$scope.my_tree_handler1 = function(branch) {
		//alert(branch.label);
		$scope.selectedItem1 = branch.label;
	};
	$scope.my_tree_handler2 = function(branch) {
		//alert(branch.label);
		$scope.selectedItem2 = branch.label;
	};  

	$scope.updateTree1 = function () {
		$scope.my_data.pop();
		$scope.buildTree($scope.selectedDevice,$scope.my_data);
		$scope.my_tree.expand_all();
	}

	$scope.updateTree2 = function () {
		$scope.my_data2.pop();
		$scope.buildTree($scope.selectedDevice2,$scope.my_data2);
		$scope.my_tree2.expand_all();
	}

	$scope.getDevices = function() {

		var req = {
				method: 'GET',
				url: $scope.urlBase+'/~' + $scope.context + '/?fu=1&lbl=object.type/device',
				headers: {
					'Accept': 'application/xml',
					'X-M2M-Origin':'admin:admin'
				}
		};
		$http(req).success(function (response, status, headers, config) {
			//alert(response);
			var x2js = new X2JS();
			var jsonData = x2js.xml_str2json(response);
			//alert(jsonData.cb.ch[1].__text);
			var key = $scope.getRootKey(jsonData);
			
			var devices = jsonData[key].__text.split(" ");
			//get devices 


			for (i=0; i<devices.length; i++) {
				var myDevice = devices[i];
				var device = {'id':'', 'name':'','desc':'','link':myDevice,'modules':[],'properties':[]};
				//fill device list
				$scope.devices.push (device);
				
				req.url = $scope.urlBase+'/~'+myDevice+'?rcn=7';
				req.data = device;
				$http(req).success(function (response, status, headers, config)  {
					
					var x2js = new X2JS();
					var jsonData = x2js.xml_str2json(response);					
					var key = $scope.getRootKey(jsonData);
					
					var label = jsonData[key].lbl;
					var id = $scope.getIdFromLabel (label);
					config.data.id = id;
					config.data.name = $scope.getNameFromLabel(label);
					config.data.friendlyName = jsonData[key].pDANe;
					if (!config.data.friendlyName) {
						config.data.friendlyName = config.data.name;
					}
					config.data.desc = jsonData[key].cnd;
					
					var tags = jsonData[key];
					for (tagKey in tags) {
						//starts with prop
						if (tagKey.lastIndexOf('prop', 0) === 0) {
							if (typeof tags[tagKey].__text !== "undefined") {
								var propName = tagKey.substring(4);
								config.data.properties.push({'name':propName,'value':tags[tagKey].__text});
							}
						}
					}
					
					req.url = $scope.urlBase+'/~' + $scope.context +'/?fu=1&lbl=object.type/module&lbl=device.id/'+id;
					req.data = config.data;
					
					//get all the modules for the given device
					$http(req).success(function (response, status, headers, config) {
						
						var x2js = new X2JS();
						var jsonData = x2js.xml_str2json(response);	
						var key = $scope.getRootKey(jsonData);
						
						var modules = jsonData[key].__text.split(" ");
						
						for (i=0; i<modules.length; i++) {
							
							//create the module object and push it in the device array
							var module = {'id':'','name':'','colorClass':'','attributes':[]};
							config.data.modules.push(module);
							
							req.url = $scope.urlBase+'/~'+modules[i]+'?rcn=7'
							req.data = module;							
							
							$http(req).success(function (response, status, headers, config) {
								
								var x2js = new X2JS();
								var jsonData = x2js.xml_str2json(response);	
								var key = $scope.getRootKey(jsonData);
								var root = jsonData[key];
								
								//fill the module name
								var tab = root.cnd.split(".");
								
								config.data.name = tab[tab.length -1];
								//fill the class with the module name to define the text color. see css file.
								config.data.colorClass = tab[tab.length -1];
								
								config.data.datapoints = [];
								config.data.actions = [];
								
								//create the attributes
								for (var childKey in root){

//									if(root[childKey]._type !=null && childKey!=='propOwner') {
									if (! $scope.arrayContains(filteredElements, childKey)) {
										var datapoint = {'name':childKey,'value':root[childKey]};
										//fill datapoints in the module
										config.data.datapoints.push(datapoint);
									} else if (root[childKey]._ty !=null) {
										var action = {'name':root[childKey]._rn,'value':''};
										config.data.actions.push(action);
									}
								}
							}).error(function (response, status, headers, config)  {});
						}
					}).error(function (response, status, headers, config)  {});
				}).error(function (response, status, headers, config)  {});
			}
		}).error(function (response, status, headers, config) {
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};

	$scope.buildTree = function (device,tree) {
		//var device = {'id':myDevice._rn, 'name':name,'link':myDevice.__text,'modules':[]};
		//var module = {'id':modules[i]._rn,'name':name,'attributes':[]}

		var Moduleschildren = [];
		var module, attribute,properties;
		var propChildren = [];
		var deviceChildren = [];
		
		for( i=0; i<device.properties.length; i++) {
			propChildren.push({'label':device.properties[i].name+':'+device.properties[i].value});
		}
		for (i=0; i<device.modules.length; i++) {
			var module = device.modules[i];
			var classes = [module.colorClass];
			var datapointsTree = [];
			var actionsTree = []
			for (j=0; j<module.datapoints.length; j++) {    			   
				datapoint = module.datapoints[j];
				//40 chars max
				var value ='';
				if (typeof datapoint.value != "undefined") {
					value = datapoint.value.substring(0,70);
					if (datapoint.value.length>70)
						value += ' ...';
				}
				datapointsTree.push({'label': datapoint.name +': '+value,'classes': classes});
			}
			for (j=0; j<module.actions.length; j++) {
				action = module.actions[j]; 
				actionsTree.push({'label': action.name,'classes': classes});
			}
			
			moduleChildren = [];
			if (datapointsTree.length != 0) {
				moduleChildren.push({'label':'datapoints','children': datapointsTree,'classes':classes});
			}
			if (actionsTree.length != 0) {
				moduleChildren.push({'label':'actions','children': actionsTree,'classes':classes});
			}
			Moduleschildren.push({'label':module.name,'children': moduleChildren,'classes':classes});
		}

		if (moduleChildren.length != 0) {
            Moduleschildren.sort(function(a,b) {return (a.label > b.label) ? 1 : ((b.label > a.label) ? -1 : 0);} );
			deviceChildren.push({'label':'modules','children': Moduleschildren});
		}
        
        if (propChildren.length != 0) {
            propChildren.sort(function(a,b) {return (a.label > b.label) ? 1 : ((b.label > a.label) ? -1 : 0);} );
			deviceChildren.push({'label':'properties','children': propChildren});
		}
					
		var deviceTree = {
				label: device.desc,
				children: deviceChildren
		}

		tree.push(deviceTree);
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

	$scope.getNameFromLabel = function(label) {
		var tab = label.split(' ');
		for (i=0; i<tab.length; i++) {
			if (tab[i].contains('name/')) {
				return tab[i].replace('name/','');
			}
		}		
		return null;
	}
	
	$scope.arrayContains = function (array, label) {
		for (i = 0; i < array.length; i++) {
			if (label === array[i]) {
				return true;
			}
		}
		return false;
	}

	$scope.getRootKey = function(rootObj) {
		for (var key in rootObj){
			return key;
		}		
		return null;
	}
	
	$scope.initContext = function(newContext) {
		console.log(newContext);
	}
	
	var init = function () {
		$scope.getDevices();
	}
	init();

});
