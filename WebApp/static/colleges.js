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

// Creates a table of colleges that resulted from a user's search
function onLoadMultipleResults(message) {
    tableBody = '';
    for (var i = 1; i < message.length; i++) {
        tableBody += '<tr>';
        tableBody += '<td><a href = ' + message[i]['url'] + '>' + message[i]['name'] + '</a></td>';
        tableBody += '</tr>';
    }
    var schoolTable = document.getElementById('school_table');
    schoolTable.innerHTML = tableBody;
}

function onStateSearchButton() {
    var stateSearchElement = document.getElementById('input_state');
    var url = website_base_url + 'schools/by_state/' + stateSearchElement.value;
    document.location.href = url;
}

// Creates a table of colleges located in the state of interest
function onLoadStates(message) {
    tableBody = '';
    for (var i = 1; i < message.length; i++) {
        tableBody += '<tr>';
        tableBody += '<td><a href = ' + message[i][1] + '>' + message[i][0] + '</a></td>';
        tableBody += '</tr>';
    }
    var schoolTable = document.getElementById('school_table');
    schoolTable.innerHTML = tableBody;
}

window.onload = function() {
    document.getElementsByTagName('video')[0].onended = function () {
        this.load();
    }
}

// Creates a table of metrics on a single college
function onLoadSchool(message) {
    tableBody = "<p><b>Location: </b>" + message['state'] + "<br></p><p><b>Total enrollment: </b>" + message['size'] + "<br></p>";
    tableBody += "<p><b>Designation: </b>" + message['designation'] + "<br></p><p><b>";
    if (message['tuition_text'][0] != '') {
        tableBody += message['tuition_text'][0] + " </b>" + message['tuition'] + "<br></p>";
    } else {
        tableBody += message['tuition_text'][1] + " </b>" + message['in_state_tuition'] + "<br></p>";
        tableBody += "<p><b>" + message['tuition_text'][2] + " </b>" + message['out_state_tuition'] + "<br></p>";
    }
    tableBody += "<p><b>Acceptance rate: </b>" + message['acceptance_rate'] + "<br></p>";
    tableBody += "<p><b>Midpoint ACT: </b>" + message['midpoint_ACT'];
    tableBody += "<br></p><p><b>Midpoint SAT: </b>" + message['midpoint_SAT'] + "<br></p>";
    tableBody += '<p><br></p><p><b><a href = "http://' + message['school_site'] + '">Institution website</a></b></p>';
    var infoTable = document.getElementById('info_table');
    infoTable.innerHTML = tableBody;
}