/*
 *  colleges.js
 *  Kevin Christianson and Isaac Haseley
 *
 *  Javascript to support our college metrics web app
 */

function onCollegeSearchButton() {
	var collegeSearchElement = document.getElementById('inputcollege');
	var url = api_base_url + 'schools/search/' + collegeSearchElement.value;
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);
	xmlHttpRequest.onreadystatechange = function() {
	        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
	            collegeSearchCallback(xmlHttpRequest.responseText);
	        } 
	    }; 
	xmlHttpRequest.send(null);
}

function collegeSearchCallback(responseText) {
    var collegeList = JSON.parse(responseText);
    
    // If responseText is one item, go to that school's page

    var resultsTextElement = document.getElementById('results_text');
    resultsTextElement.innerHTML = collegeList[0]['name'];

	// Else if it's multiple items, show the helper page with options and links
	// Else if it's empty, stay and display an error message
        
}

function onStateSearchButton() {
	var stateSearchElement = document.getElementById('inputstate');
}