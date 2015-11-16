	define(['knockout','jquery','chart'], function(ko,$,Chart) {

		return function ChartViewModel(){
	        var dataset = [];
			var d ={};	
			var setDataLoad;
			var self = this;
			
		    self.datasetDropdowns = ko.observableArray(dataset);
		    self.crimesdataSet = ko.observable(setDataLoad);
		    self.dataset = ko.observable();
		    self.initializeSelectedDatasets = function(viewModels,e) {
	            e.preventDefault();
	            displayChart($(e.target).val());
		         return false;
		    };
		    		   
		    self.cleanAndApplyBindings = function() {
	            ko.cleanNode( $('#chartView')[0]);
	            ko.applyBindings(self, $('#chartView')[0]);
	        };
	
			function data1(xaxis,yaxis){				
				
				var returnData = new Object();
				returnData.labels= xaxis,
				returnData.datasets = [
				{
					label: "My Second dataset",
		            fillColor: "rgba(151,187,205,0.5)",
		            strokeColor: "rgba(151,187,205,0.8)",
		            highlightFill: "rgba(151,187,205,0.75)",
		            highlightStroke: "rgba(151,187,205,1)",
					data: yaxis
				}]
				return returnData;
			};
			
			function displayChart(chartSet){
			    var myCharts = document.getElementById('myChart');
			    var chartInstance;
				ajaxGetNoQueryParams([window.chicago_stats_globals.middleware_resources['chart'],
		                       [chartSet]].join('/'),
		    	  function(d, textStatus, xhr) {	
		           var yaxis = d[0].Yaxis;
		           var xaxis = d[0].Xaxis; 
		           var Chartjs = Chart.noConflict();
		           if (myCharts) {
						myCharts = myCharts.getContext('2d');
						chartInstance = new Chart(myCharts).Bar(data1(xaxis,yaxis));						
					}
		           console.log(d);
		           chartInstance.destroy();
	                 
		        },function(d, textStatus, xhr) {
		            console.log(d);
		            console.log(textStatus);
		            console.log(xhr);
		        });
			}
			init.call(self);
			function init() {
	            ajaxMultiGet.call(self,
	                [window.chicago_stats_globals.middleware_resources['mongo/getCollectionNames']],
	                function(data) {
	                    var filteredData = filterData(data[0], 'system.indexes');
	                    dataset.splice(0);
	                    filteredData.forEach(function(datum) {
	                        dataset.push(datum);
	                    });
	                    setDataLoad = data[0][0];
	                    displayChart(data[0][0]);
	                    self.cleanAndApplyBindings();

	                }, function(data) {
	                    console.log(data);
	                    showModal(window.chicago_stats_globals.GENERAL_MODAL_TITLE,
	                        [data[0][2]['status'], ': ' , data[0][2]['responseText']].join(''));
	                });
	        }

		}
	});