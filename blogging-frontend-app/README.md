### Blogging APP Backend

## Description
Portfolio Front END application to manage Users, Articles , Comments , Likes , Followers

## Requirements
1. CRUD capabilities for USERS
2. CRUD capabilities for BLOGS which would be scoped under a USER. i.e. only the user which creates a blog would be able to modify/delete it
3. All USERS should be able to view BLOGS which have been posted
4. A USER should be able to write a BLOG and post it for everyone to see
5. A USER should be able to follow another USER so that their BLOGS have a higher priority on the former USER's feed
6. USERS should be able to COMMENT under a BLOG. These comments would be public
7. USERS should be able to LIKE / UNLIKE a BLOG

## Prerequisites
1. Node 14
3. Angular CLI

## Local Deployment Steps
1. Clone the project -> https://github.com/subhatanay/bloggingapp-frontend.git
2. cd bloggingapp-frontend
3. Open /Users/subhajgh/Documents/Cisco/poc/blogging-frontend-app/blogging-frontend-app/src/environments/environment.ts
4. Update the localApiUrl -> http://localhost:6060/api
3. npm install
4. npm start
5. Front Application will be up in 4200 port. 

## Backend App Deployment Steps
Follow https://github.com/subhatanay/blogging-app/blob/main/README.md

## Demo
