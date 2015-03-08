
define(['knockout','knockout_mapping'], function(ko,mapping) {


	return function FilterViewModel(){

			var jsonCriteriaData = ['data','ID','year'];

			function FilterViewModel() {
			 	var self = this;

		   		self.filterItem = ko.observableArray([{}]); 
		    	self.filterDropdown =  ko.observableArray(jsonCriteriaData)

		    	self.filterItemLine = function() {
		    		self.filterItem.push({});
		   		 };

		   		 self.removeFilterLine = function() {
		    		self.filterItem.remove(this);
		   		}
			}


			ko.applyBindings(new FilterViewModel(),document.getElementById("filterData")); 	

	}

});