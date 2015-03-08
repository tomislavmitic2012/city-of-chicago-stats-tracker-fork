// Main viewmodel class
define(['knockout','jquery','knockout_mapping'], function(ko,$ , mappping) {

	return function _mydataViewModel(){
		var _mydata = [];

		$.ajax({
			url: 'https://data.cityofchicago.org/resource/alternative-fuel-locations.json?fuel_type_code=LPG',
			async: false,
			dataType: 'json',
			success: function (json) {
				_mydata = json;
			}
		});


		var _mydataViewModel = {

			myData: mappping.fromJS(_mydata)

		}

		
		ko.applyBindings(_mydataViewModel,document.getElementById("myDataSet"))


	}


});