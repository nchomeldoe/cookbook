This is the back end of my full-stack cookbook app, see https://github.com/nchomeldoe/nats-cookbook for the front end. It is built in Java Spring Boot.

The API allows users to create, read, update and delete recipes and is structured as a controller with GET, POST, PUT and DELETE endpoints, and a set of entities, each of which has an associated service and a repository for interacting with the MySQL database.

There are four entities: Ingredient, Quantity, Recipe and RecipeElement. RecipeElement has a many-to-one relationship with each of the other entities.

There is a also a DeserializedRecipe class which mirrors the structure of the data that will be received from / sent to the font end, and the services include a DataTransformer service with methods for converting the front-end data into the format needed for the bakc end and vice versa.

To create an object the user submits data in JSON format with a body such as:

 {
        "serves": 2,
        "name": "Omelette",
        "description": [
            "Crack eggs into a jug and add a small amount of tap water.",
            "Beat the eggs and pour into heated frying pan.",
            "Cook for 5-10 mins.",
            "Grate cheddar over the top and serve.",
        ],
        "createdBy": "Nat",
        "cuisine": "French",
        "ingredientsAndQuantities": [
            {
                "ingredient": {
                    "name": "Eggs"
                },
                "quantity": {
                    "unit": "item",
                    "value": "3"
                }
            },
            {
                "ingredient": {
                    "name": "Cheddar cheese"
                },
                "quantity": {
                    "unit": "g",
                    "value": "15"
                }
            }
        ]
    }

