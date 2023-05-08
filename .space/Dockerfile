FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive
ENV LC_ALL=C.UTF-8

RUN apt-get update

RUN apt-get install -y \
        git \
        openjdk-11-jdk \
        lsof \
        openssh-client \
        openssh-server \
        python3 \
        python3-pip \
        wget \
        curl \
        pv && \
    rm -rf /var/lib/apt/lists/*

RUN pip3 install projector-installer

RUN case "$(uname -m)" in \
        aarch64) EXT="ARM64" ;; \
        *) EXT="" ;; \
    esac && \
    curl -fSLo idea.tar.gz \
        "https://download.jetbrains.com/product?code=IIC&platform=linux${EXT}"

RUN pv idea.tar.gz | tar -xz
RUN mv idea-IC-* idea
RUN rm idea.tar.gz
RUN projector --accept-license config add --port 4000 --hostname 0.0.0.0 IDEACOMMUNITY "$(pwd)/idea"

EXPOSE 4000

ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
