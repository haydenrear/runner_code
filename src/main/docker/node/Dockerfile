FROM localhost:5005/python

ARG DEBIAN_FRONTEND=noninteractive
ARG NODE_VERSION=24.2.0
ARG TARGETARCH=arm64

LABEL org.opencontainers.image.title="node"
LABEL org.opencontainers.image.description="Base Node image (v24.2.0) atop local python, with tree-sitter-cli preinstalled"
LABEL org.opencontainers.image.source="runner_code/src/main/docker/node/Dockerfile"
LABEL com.hayden.node.version="${NODE_VERSION}"

# Required tools for fetching and extracting Node binaries and cloning repos
RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends \
    curl \
    ca-certificates \
    xz-utils \
    git; \
    rm -rf /var/lib/apt/lists/*

RUN set -eux; \
    case "${TARGETARCH:-amd64}" in \
    arm64) NODE_ARCH="arm64" ;; \
    amd64) NODE_ARCH="x64" ;; \
    *) NODE_ARCH="x64" ;; \
    esac; \
    curl -fsSL "https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-linux-${NODE_ARCH}.tar.xz" -o /tmp/node.tar.xz; \
    mkdir -p /opt/node; \
    tar -xJf /tmp/node.tar.xz -C /opt/node --strip-components=1;

ENV NODE_HOME=/opt/node
ENV PATH="${NODE_HOME}/bin:${PATH}"

RUN node --version && npm --version
RUN rm /tmp/node.tar.xz


RUN npm install -g tree-sitter-cli && ln -sf /opt/node/bin/tree-sitter /usr/local/bin/tree-sitter && tree-sitter --version

RUN npm --version
RUN node --version
RUN npx --version
RUN uv --version
WORKDIR /home/ubuntu
