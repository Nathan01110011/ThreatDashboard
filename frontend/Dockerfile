# client build stage
FROM node:17.1.0-alpine3.14 AS client-build-env
COPY ./package.json /src/package.json
COPY ./package-lock.json /src/package-lock.json 
WORKDIR /src
RUN npm ci
COPY . /src
RUN npm run build

# final stage
FROM openresty/openresty:1.19.9.1-2-alpine-fat
WORKDIR /app
COPY --from=client-build-env /src/dist/threat-dashboard /usr/local/openresty/nginx/html
COPY nginx-config/ /usr/local/openresty/nginx/conf/

EXPOSE 80
