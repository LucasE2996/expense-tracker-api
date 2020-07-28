<h1 align="center">Welcome to Expense Tracker API 👋</h1>
<p>
  <a href="https://twitter.com/lucashtwt" target="_blank">
    <img alt="Twitter: lucashtwt" src="https://img.shields.io/twitter/follow/lucashtwt.svg?style=social" />
  </a>
</p>

> Simple expense controller API. The main goal of this project was to practice how to create a Java API using concepts like JWT authentication, docker containers to get the setup up and running as quick as possible, CORS policy configuration, add filters to requests and so on. Feel free to fork this project or open a Pull Request if you have any improvements you'd like to share!

## Install

#### Configuring Dadabase with Docker

> 💎 [Install Docker here](https://docs.docker.com/get-docker/) if you don't have it installed yet.

Let's start by creating a new Postgres' container:

```sh
docker container run --name postgresdb -e POSTGRES_PASSWORD=admin -d  -p 5432:5432 postgres
```

*P.S.: If you don't have any Postgres image in your computer docker will automatically download the latest one.*

Now let's copy the file `expensetracker_db.sql` to our brand-new container:

```sh
docker cp expensetracker_db.sql postgresdb:/
```

Nice! 🎉 Now all we need to do is run the SQL script, so it will create our database inside the container for us. To do that we need to have access to the container command line. Here we have two options:

##### Option 1 - Docker command line tool

Run the following command:

```sh
docker container exec -it postgresdb bash
```

##### Option 2 - Docker desktop dashboard

Download docker for desktop if you don't have it. Go to the docker dashboard and click on the terminal button, so it will open the command line inside the container for us.

##### Running the sql script

Inside the container command line tool run the following:

```sh
psql -U postgres --file expensetracker_db.sql
```

So far so good!  🎉 

##### Checking application with maven

Now we need to run a Maven command to check if everything ok:

```sh
mvn install
```

**Expected result:**

If everything went well you should see this message on your terminal:

```sh
BUILD SUCCESS
```


## Run tests

You can run the following command to run all the tests:

```sh
mvn test
```

*P.S.: This can also be achieved through the previous command `mvn clean install` or just `mvn install`*

## Endpoints

> For all endpoints you will see the statemtn `{host}`. This value must be replaced by the host your running this API on. If you are running locally just replace for `localhost`.

### User

This project uses JWT as authentication method. The current expiration time is 2 hours, fell free to change that if you want.
Also, the generated token must be passed as a header attribute of type `Authorization` for any other endpoint that is not `.../user/register` or `.../user/login`. E.g.: `"Authorization": "Bearer <token in string format>"`

> If you are not familiar with JWT concept please refer to this [documentation](https://jwt.io/introduction/).

#### Create a new user:

Method: `GET`

URL: `http://{host}/api/users/register`

Expected response:

```json
{
    "token": "<token in string format>"
}
```

#### User login:

Method: `GET`

URL: `http://{host}/api/users/login`

Expected response:

```json
{
    "token": "<token in string format>"
}
```

### Categories

> ⚠️ Must be logged in as a user

#### Get all categories:

Method: `GET`

URL: `http://{host}/api/categories`

Expected response:

```json
[
    {
        "categoryId": 16,
        "userId": 3,
        "title": "market",
        "description": "all market expenses",
        "totalExpense": 0.0
    },
    ...
]
```

#### Get category by ID:

Method: `GET`

URL: `http://{host}/api/categories/{categoryId}`

Expected response:

```json
{
    "categoryId": 16,
    "userId": 3,
    "title": "market",
    "description": "all market expenses",
    "totalExpense": 0.0
}
```

#### Create a new category:

Method: `POST`

URL: `http://{host}/api/categories`

Body example:

```json
{
    "title": "market",
    "description": "all market expenses"
}
```

Expected response:

```json
{
    "categoryId": 17,
    "userId": 3,
    "title": "market",
    "description": "all market expenses",
    "totalExpense": 0.0
}
```

#### Update existent category:

Method: `POST`

URL: `http://{host}/api/categories/{categoryId}`

Body example:

```json
{
    "title": "update",
    "description": "updated description"
}
```

Expected response:

```json
{
    "categoryId": 17,
    "userId": 3,
    "title": "update",
    "description": "updated description",
    "totalExpense": 0.0
}
```

#### Delete a category:

Method: `DELETE`

URL: `http://{host}/categories/{categoryId}`

Expected response:

```json
{
  "success": true
}
```

### Transactions

> ⚠️ Must be logged in as a user

#### Get all transactions for a given category

Method: `GET`

URL: `http://{host}/api/categories/{categoryId}/transactions`

Expected response:

```json
[
    {
        "categoryId": 1003,
        "transactionId": 16,
        "userId": 3,
        "amount": 50.9,
        "note": "Walmart",
        "transactionDate": 1595715803000
    },
    ...
]
```

#### Get transaction by ID:

Method: `GET`

URL: `http://{host}/api/categories/{categoryId}/transactions/{transactionId}`

Expected response:

```json
{
    "categoryId": 1003,
    "transactionId": 16,
    "userId": 3,
    "amount": 50.9,
    "note": "Walmart",
    "transactionDate": 1595715803000
}
```

#### Create a new transaction:

Method: `POST`

URL: `http://{host}/api/cateogies/{categoryId}/transctions`

Body example:

```json
{
    "amount": 50.9,
    "note": "Walmart",
    "transactionDate": 1595715803000
}
```

Expected response:

```json
{
    "categoryId": 1003,
    "transactionId": 16,
    "userId": 3,
    "amount": 50.9,
    "note": "Walmart",
    "transactionDate": 1595715803000
}
```

#### Update a category:

Method: `POST`

URL: `http://{host}/api/categories/{categoryId}/transactions/{transactionId}`

Body example:

```json
{
    "amount": 99.9,
    "note": "Supermarket",
    "transactionDate": 1595715803000
}
```

Expected response:

```json
{
    "categoryId": 1003,
    "transactionId": 16,
    "userId": 3,
    "amount": 99.9,
    "note": "Supermarket",
    "transactionDate": 1595715803000
}
```

#### Delete a transaction:

Method: `DELETE`

URL: `http://{host}/api/categories/{categoryId}/transactions/{transactionId}`

Expected response:

```json
{
  "success": true
}
```

## Author

👤 **Lucas Rosa**

* Website: https://codepen.io/Lucas-Rosa/
* Twitter: [@lucashtwt](https://twitter.com/lucashtwt)
* Github: [@LucasE2996](https://github.com/LucasE2996)
* LinkedIn: [@lucas-r-a4020796](https://linkedin.com/in/lucas-r-a4020796)

## Show your support

Give a ⭐ if this project helped you!

***
_This README was generated with ❤ by [readme-md-generator](https://github.com/kefranabg/readme-md-generator)_