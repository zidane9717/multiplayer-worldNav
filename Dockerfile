FROM openjdk:12-alpine
EXPOSE 8080
ADD /out/artifacts/worldNavMultiplayer/worldNavMultiplayer.war worldNavMultiplayer.war
ENTRYPOINT ["java","-war","/worldNavMultiplayer.war"]