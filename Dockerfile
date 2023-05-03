FROM adoptopenjdk:11-jdk-hotspot

# Setup adapted from https://github.com/hseeberger/scala-sbt/blob/master/debian/Dockerfile
RUN \
  apt-get update -q && \
  apt-get upgrade -qq && \
  apt-get install -y git && \
  rm -rf /var/lib/apt/lists/*

# Any RUN command after an ARG is set has that value in it as an environment variable and thus
# invalidates layer cache, so only declaring these ARGs when they're used
#https://github.com/sbt/sbt/releases/download/v1.2.8/sbt-1.2.8.tgz
ARG SBT_VERSION="1.7.1"
RUN \
  curl -L "https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.tgz" | tar zxf - -C /usr/share  && \
  cd /usr/share/sbt/bin && \
  ln -s /usr/share/sbt/bin/sbt /usr/local/bin/sbt


RUN mkdir -p /chatapp/project
WORKDIR /chatapp
COPY ./build.sbt /chatapp
COPY ./project/plugin.sbt /chatapp/project
ADD . /chatapp

EXPOSE 9000 9091

CMD ["bash"]