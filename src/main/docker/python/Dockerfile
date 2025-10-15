# Use the official Ubuntu base image
FROM localhost:5005/jdk

ENV HOME=/home/ubuntu
# Set environment variables

RUN apt-get update && apt-get install -y python3 python3-pip wget bzip2 ca-certificates libglib2.0-0 libxext6 libsm6 libxrender1 sudo
RUN apt-get install -y python3-venv
RUN apt-get install -y python3-toml

RUN python3 -m venv phx
RUN chmod +x ./phx/bin/activate

ENV GRADLE_USER_HOME=/home/ubuntu/.gradle
ENV JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-arm64
ENV PATH="$JAVA_HOME/bin:$PATH"