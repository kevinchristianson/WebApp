# WebApp
This is a multi-faceted website projcet which displays college and university information based on user searches. It contains three main parts:

## Database
The database is not in this repository, however the project requires that the college and university information be loaded into a database so that the API can hit it. For this project we used pSQL to create our database.

## API
This project contains a python file called api.py which is a API created using flask which hits the aforementioned database and returns the resulting from the query.

## Website
The website contains a few different parts. There is the home page with a search bar, which sends a query to the API. Once the API returns the query results, the page will redirect to one of two pages. Either a list of colleges matching the search criteria, or a specific college if the search was sufficiently specific. All of these pages have been embellished with CSS which can include vieos and photos as well.
