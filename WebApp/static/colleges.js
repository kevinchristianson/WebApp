/*
 *  colleges.js
 *  Kevin Christianson and Isaac Haseley
 *
 *  Javascript to support our college metrics web app
 */

function onCollegeSearchButton() {
	var collegeSearchElement = document.getElementById('input_college');
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

    var resultsTextElement = document.getElementById('college_results_element');
    resultsTextElement.innerHTML = collegeList[0]['name'] + ' is located in ' + collegeList[0]['state'] + '. '
    							   + collegeList[0]['size'] + ' students attend. Its tuition is $'
    							   + collegeList[0]['in_state_tuition'] + ', its acceptance rate is '
    							   + collegeList[0]['acceptance_rate'] + ', its midpoint ACT is '
    							   + collegeList[0]['midpoint_ACT'] + ', and its midpoint SAT is '
    							   + collegeList[0]['midpoint_SAT'] + '. To find out more, visit '
    							   + collegeList[0]['school_site'] + '.';

	// Else if it's multiple items, show the helper page with options and links
	// Else if it's empty, stay and display an error message
        
}

function onStateSearchButton() {
	var stateSearchElement = document.getElementById('input_state');
	var url = api_base_url + 'schools/by_state/' + stateSearchElement.value;
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.open('get', url);
	xmlHttpRequest.onreadystatechange = function() {
	        if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) { 
	            stateSearchCallback(xmlHttpRequest.responseText);
	        } 
	    }; 
	xmlHttpRequest.send(null);
}

function stateSearchCallback(responseText) {
    var collegeList = JSON.parse(responseText);
    var resultsTextElement = document.getElementById('state_results_element');
    for (i = 0; i < collegeList.length; i++) {
    	if (i != collegeList.length) {
    		resultsTextElement.innerHTML += collegeList[i][0] + ' --- ';    
    	}
    }
}