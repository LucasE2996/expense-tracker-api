<h1 align="center">Welcome to Expense Tracker API 👋</h1>
<p>
  <a href="https://twitter.com/lucashtwt" target="_blank">
    <img alt="Twitter: lucashtwt" src="https://img.shields.io/twitter/follow/lucashtwt.svg?style=social" />
  </a>
</p>

> Simple expense controller

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

> TODO: when the API is finished complete this part

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