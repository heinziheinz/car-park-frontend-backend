FROM node:19-alpine as build

COPY package*.json ./
COPY vite.config.js ./
COPY index.html ./
COPY ./src /src/

RUN npm install --silent

RUN npm run build

FROM nginx:alpine
COPY --from=build /dist /usr/share/nginx/html

COPY index.html /usr/share/nginx/html
EXPOSE 8080
CMD ["/bin/sh", "-c", "sed -i 's/listen  .*/listen 8080;/g' /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'"]