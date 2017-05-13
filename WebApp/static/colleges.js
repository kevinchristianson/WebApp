/*
 *  colleges.js
 *  Kevin Christianson and Isaac Haseley
 *
 *  Javascript to support our college metrics web app
 */

function onCollegeSearchButton() {
    var collegeSearchElement = document.getElementById('input_college');
    var url = website_base_url + 'schools/search/' + collegeSearchElement.value;
    document.location.href = url;
}

function onStateSearchButton() {
    var stateSearchElement = document.getElementById('input_state');
    var url = website_base_url + 'schools/by_state/' + stateSearchElement.value;
    document.location.href = url;
}

function onLoadStates(message) {
    tableBody = '';
    for (var i = 0; i < message.length; i++) {
        tableBody += '<tr>';
        tableBody += '<td><a href = ' + message[i][1] + '>' + message[i][0] + '</a></td>';
        tableBody += '</tr>';
    }
    var schoolTable = document.getElementById('school_table');
    schoolTable.innerHTML = tableBody;
}

// for when server does not support html5 loop for video on homepage
function repeat(){
    var film = document.getElementsByTagName('video')[0];
   // film.load();
    film.play();
}