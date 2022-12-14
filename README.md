# MoviesDemo
Overview:
This project displays list of popular movies from tmdb. First screen displays a list of popular movies. There are four sorting filters available -Popularity high to low, popularity low to high, rating high to low and rating low to high. Default filter is popularity high to low. 
Clicking on any movie from the list opens a new screen which displays following details of a movie - movie name, movie image, tagline, genres, release date and overview. 

API endpoints :
discover/movie - This endpoint is used to fetch popular movies.
movie/{movie_id} - This endpoint is used to fetch details of a movie with the movie id.

API_KEY
Please enter your API_KEY in the apikey.properties, to call the APIs.

Architecture:
MVVM architecture is used. All the service call are made from repository. Activity relies on viewmodel for getting the data and viewmodel fetches the data from the dedicated repository. 

Network:
Retrofit library is used for network calls. ApiClient is made singleton.

Images:
Glide library is used to display images from URL.


![screen-1](https://user-images.githubusercontent.com/118042729/201466552-d70a4026-f97c-4e09-9e77-a45cc15a2e4d.jpeg)
![screen-1_1](https://user-images.githubusercontent.com/118042729/201466562-f855b9eb-d1d0-448d-ad04-4175aceaa898.jpeg)
![screen-2](https://user-images.githubusercontent.com/118042729/201466568-68d8cdf3-a4c6-49dc-9f89-ec9fb4048a4e.jpeg)
