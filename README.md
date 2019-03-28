[![CircleCI](https://circleci.com/gh/veken1199/City-Names-Autocomplete.svg?style=svg)](https://circleci.com/gh/veken1199/City-Names-Autocomplete)
# City-Names-Autocomplete

## Visit the [site](https://city-name-autocomplete-2019.herokuapp.com/) for full documentation
### Visit the [my swagger](https://city-name-autocomplete-2019.herokuapp.com/swagger-ui.html) for API endpoint documentation
### Story
This web application is built on Spring boot using Java 8 and Gradle. One of reasons for choosing Spring boot and Java 8 is the fact that I wanted to build something from scratch using this technology and to go through the challenges of the basic setup of the application, testing, and deployment configuration. The application uses Swagger UI to provide developers an easy access to the REST controllers supported by this application. You can access the swagger ui from API docs .

Further more, the app has been containerized and published on [docker hub repository](https://cloud.docker.com/u/feken/repository/docker/feken/dockerized-city-autocomplete), and also deployed on a remote [Heroku machine](https://city-name-autocomplete-2019.herokuapp.com/), It should be running now 


### How to build 
```
// Build the app
gradlew build

// Run the app
java -jar build/libs/city-autocomplete-1.0.0.jar
// The app should start on port http://localhost:3112
```

### Run directly from docker hub image
```
docker run -p 3112:3112 feken/dockerized-city-autocomplete
// For Linux: The app should start on http://localhost:3112 
// For Windows: The app should start on http://192.168.99.100:3112
```

### How to use
GET https://city-name-autocomplete-2019.herokuapp.com/suggestion?q=Queb&minScore=0.5&latitude=46.81228&longitude=46.81228&similarityAlgo=Jaro&limit=6 

Request Params :

        q: String representing search query
        limit: Integer representing the number suggestions in the response
        latitude: Lat geolocation to improve suggestions. Should be sent with Longitude
        longitude: Long geolocation to improve suggestions. Should be sent with Latitude
        minScore: Double between 1 and 0 to set the min score for suggestions returned
        similarityAlgo: Similarity algorithm to be used, Levenshtein will be used by default. Send "Jaro"
                        to use Jaro Winkler Algo.
    
Response:

        {
        "suggestions": [
            {
                "name": "Québec, QC, Canada",
                "latitude": 46.81228,
                "longitude": 46.81228,
                "score": 1
            },
            {
                "name": "Hoquiam, WA, USA",
                "latitude": 46.98092,
                "longitude": 46.98092,
                "score": 0.895
            }],
        "status": "200",
        "message": "Operation succeeded"
        }

