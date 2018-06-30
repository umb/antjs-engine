# specify the node base image with your desired version node:<version>
FROM node:8


ADD build build
ADD main.sh main.sh

CMD ["./main.sh"]

# replace this with your application's default port
EXPOSE 3000