# Coffee Shop

## Production deploy


```
-- IMPORTANT
 
In order to deploy application to production, each application needed to be build separately. Detailed build steps can be found below. 
After generating artifacts for Server and Client applications following command can be run to boot up environment.
```


```
docker-compose up
```
Before running the compose command ensure nothing is running on;
- `5432` for postgres
- `8080` for the Spring
- `4200` for the Angular

Otherwise, cluster will not set up correctly.

Once the cluster is up application is accessible on `localhost:4200`

The application has 3 users. One admin and two salesmen. Below are the credentials for the users.  
- admin Password12
- salesman1 password
- salesman2 password

PS: Since the authentication mechanism is `Basic` logout is not implemented! In order to `logout` just close all browser windows. Incognito mode could be helpful in this case.

Only Admin user is allowed to change the stock for the products.

## Local development

### Front-end
To start development for Angular application, simply change directory to `client` and then execute `npm run start`. Or use your idea to start the application.
Soon after application should be running on port `4200`. Beware that Angular application depends on backend to be running!

### Back-end
To start development for Spring application, simply change directory to `server` and then execute `./gradlew bootRun`. Or use your idea to start the application.
Soon after application should be running on port `8080` with `/api` context path. Beware that Spring application depends on Postgres to be running!

### Postgres DB
To start Postgres, simply change directory to `server` and then execute `docker-compose up -f development-docker-compose.yml`
It will simply boot up one postgres instance for local development.


## Server Application

### Build

Build automatically executes tests and generate artifact under build folder.  
```
cd server
./gradlew clean build
```

Additionally, coverage report will be generated under
```
server/build/reports/jacoco/test/html/index.html
```

## Client Application

### Build
Build will generate artifacts with optimization for production

```
cd client
npm run build
```
### Test

```
cd client
npm run test
```

### Coverage

```
cd client
npm run coverage
```

Coverage report can be found under
```
client/dist/coffee-shop-client/index.html
```


# Improvement points
In order to keep this task less complicated and small some details are not provided. Such as;
- Password encryption
- Cookie based authentication rather than Basic
- More advanced user management
- CSRF security for the API
- More targeted tests for backend code rather than covering everything with a few integration tests
- Front-end tests
- Using Headless chrome for running tests
- Proper CDN server for product images. Rather than having them under resource folder.
- Cache for images.
- DB improvements for Payment history 

