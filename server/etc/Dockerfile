FROM openjdk:17.0.2-jdk

ENV APP_PROFILE=""
ENV ARTIFACT=coffee-shop-server-0.0.1-SNAPSHOT.jar
ENV APP_PATH=opt/app-root/src

COPY build/libs/${ARTIFACT} ${APP_PATH}/${ARTIFACT}

ENTRYPOINT java -jar "-Dspring.profiles.active=${APP_PROFILE}" "${APP_PATH}/${ARTIFACT}"
