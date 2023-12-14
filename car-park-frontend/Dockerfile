FROM node:19-alpine as build

COPY package*.json ./
COPY vite.config.js ./
COPY index.html ./
COPY ./src /src/

RUN npm install --silent

RUN npm run build

FROM nginx:alpine
COPY --from=build /dist /usr/share/nginx/html