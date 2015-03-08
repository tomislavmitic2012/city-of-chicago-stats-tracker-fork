	// Main viewmodel class
	define(['knockout','knockout_mapping','jquery'], function(ko,mapping,$) {

		return function overViewViewModel(){


			var data = [

	    		{
				  "beat" : "731",
				  "_secondary_description" : "VIOLATE ORDER OF PROTECTION",
				  "fbi_cd" : "26",
				  "x_coordinate" : "1176376",
				  "block" : "001XX W 75TH ST",
				  "domestic" : "N",
				  "case_" : "HX141724",
				  "_iucr" : "4387",
				  "arrest" : "Y",
				  "ward" : "6",
				  "y_coordinate" : "1855292",
				  "date_of_occurrence" : "2014-02-06T19:34:00",
				  "_primary_decsription" : "OTHER OFFENSE",
				  "_location_description" : "RESIDENCE"
				}
				, {
				  "beat" : "1432",
				  "_secondary_description" : "$500 AND UNDER",
				  "location" : {
				    "needs_recoding" : false,
				    "longitude" : "-87.67692180426695",
				    "latitude" : "41.924929817906275"
				  },
				  "fbi_cd" : "06",
				  "x_coordinate" : "1162881",
				  "block" : "019XX W FULLERTON AVE",
				  "domestic" : "N",
				  "case_" : "HX166922",
				  "_iucr" : "0820",
				  "arrest" : "Y",
				  "ward" : "32",
				  "y_coordinate" : "1915918",
				  "date_of_occurrence" : "2014-02-06T19:36:00",
				  "_primary_decsription" : "THEFT",
				  "longitude" : "-87.67692180426695",
				  "latitude" : "41.924929817906275",
				  "_location_description" : "RESTAURANT"
				}

	    		];


	    	var _viewModel = {
	    		crimes: mapping.fromJS(data),
	    	
	    	}
	    	
	    	ko.applyBindings(_viewModel,document.getElementById("overView"));



		}

	   	       

});