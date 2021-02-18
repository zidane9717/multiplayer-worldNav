FROM openjdk:8
ADD out/artifacts/worldNavMultiplayer.war docker-worldNavMultiplayer.war
EXPOSE 8080
ENTRYPOINT["java","-war","/docker-worldNavMultiplayer.war"]