ARG REGISTRY
FROM $REGISTRY/openjdk:11
ARG APP_HOME=/app
WORKDIR $APP_HOME
COPY target/ai-api*.jar $APP_HOME/ai-api.jar
CMD java $JAVA_OPTS -jar ai-api.jar $JAVA_ARGS
