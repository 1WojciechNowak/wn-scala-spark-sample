FROM mozilla/sbt:latest

WORKDIR usr/app
COPY ./ ./

CMD ["sbt", "run"]