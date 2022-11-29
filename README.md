### Blogging APP Backend

## Description
Portfolio Backend REST API application to manage Users, Articles , Comments , Likes , Followers

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
2. Maven
3. Docker, Docker compose 

## Local Deployment Steps
1. Clone the project -> https://github.com/subhatanay/blogging-app.git
2. cd blogging-app. 
3. mvn clean install
4. docker-compose up -d
5. Application will be up in 6060 port. 

## Frontend UI Deployment Steps
Follow https://github.com/subhatanay/bloggingapp-frontend/blob/master/README.md

## API Documentation
Open Browser and hit http://localhost:6060/api/swagger-ui/index.html

## Demo
