### Medium Clone Blogging App

## Description
Portfolio Backend REST API and UI application to manage Users, Articles , Comments , Likes , Followers

## Requirements
1. CRUD capabilities for USERS
2. CRUD capabilities for BLOGS which would be scoped under a USER. i.e. only the user which creates a blog would be able to modify/delete it
3. All USERS should be able to view BLOGS which have been posted
4. A USER should be able to write a BLOG and post it for everyone to see
5. A USER should be able to follow another USER so that their BLOGS have a higher priority on the former USER's feed
6. USERS should be able to COMMENT under a BLOG. These comments would be public
7. USERS should be able to LIKE / UNLIKE a BLOG

## Prerequisites
1. Java 11
3. Docker, Docker compose 

## Local Deployment Steps with one step
1. Clone the project -> https://github.com/subhatanay/blogging-app.git
2. cd blogging-app. 
4. docker-compose up -d
5. Backend will start at 6060 and UI will start at 4200
6. Backend env file -> ./app-backend/src/java/main/resources/application.yml
7. UI env file -> ./blogging-frontend-app/src/environments/environments.prod.ts

## API Documentation
Open Browser and hit http://localhost:6060/api/swagger-ui/index.html
![image](https://user-images.githubusercontent.com/22850961/204879368-056f911e-801d-4cf8-9f3d-5799c4c03df6.png)


## Demo
![Medium Clone](https://user-images.githubusercontent.com/22850961/204880642-9183f848-bfda-4edc-a090-105c4eec58f8.gif)
