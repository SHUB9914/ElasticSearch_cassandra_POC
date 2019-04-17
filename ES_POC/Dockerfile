FROM vadivelk/alpine-sbt

#WORKDIR /usr/src
#
#COPY src/ /usr/src
#COPY build.sbt build.sbt
#COPY build.sbt build.sbt
#COPY project /project
#
#RUN sbt stage
#
#ENTRYPOINT ["./target/universal/stage/bin/ES_POC"]
##CMD ["sbt", "run"]


RUN mkdir -p /opt/stream

WORKDIR /opt/stream

COPY build.sbt build.sbt
COPY src/main/scala/simulator src/main/scala/simulator
COPY src/main/resources src/main/resources
COPY project/ project/


CMD ["sbt", "run"]


