# WebApp
This is a multi-faceted website project which displays college and university information based on user searches. It contains three main parts:

## Database
The database is not in this repository, however the project requires that the college and university information be loaded into a database so that the API can hit it. For this project we used pSQL to create our database.

## API
This project contains a python file called api.py which is a API created using flask which hits the aforementioned database and returns the resulting from the query.

## Website
The website contains a few different parts. There is the home page with a search bar, which sends a query to the API. Once the API returns the query results, the page will redirect to one of two pages: either a list of colleges matching the search criteria, or a single college with statistics and information about it if the search was sufficiently specific. All of these pages have been embellished with CSS which can include videos and photos as well.

# Desktop Application
In the final folder there is a desktop application which has a similar function. It uses the same API and database as the website, however it has additional functionality. This desktop application allows users to search for colleges by ACT scores, admission rates, tuition prices, designation (public, private or for-profit), state or enrollment. Based on the given criteria, a list of colleges is returned in order of how well they fit the criteria. Additionally, users weigh different metrics (for example, low-tuition as 60% and 20% admission rate and ACT score each when deciding which schools are the best match).
