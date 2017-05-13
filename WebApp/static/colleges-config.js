/*
 *  colleges-config.js
 *  Kevin Christianson and Isaac Haseley
 *
 *  Configuration file for our college metrics web application
 */

// Set this to match the base URL at which your api.py is listening.
var thacker = false
if (thacker) {
    var api_base_url = 'http://thacker.mathcs.carleton.edu:5107/'
    var website_base_url = 'http://thacker.mathcs.carleton.edu:5207/'
} else {
    var api_base_url = 'http://localhost:8080/'
    var website_base_url = 'http://localhost:8081/'
}