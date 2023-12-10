FROM node:19-alpine as BUILD_IMAGE
FROM node:19-alpine
WORKDIR /usr/src/app

COPY ./package.json .

RUN npm i

COPY . .


CMD ["npm", "run", "dev"]