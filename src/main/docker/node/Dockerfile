# Base Node image for org-wide use
# - Extends local python image
# - Installs Node.js v24.2.0
# - Preinstalls tree-sitter-cli
FROM localhost:5005/python

ARG DEBIAN_FRONTEND=noninteractive
ARG NODE_VERSION=24.2.0
ARG TARGETARCH

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

# Install Node.js v24.2.0 from official binaries
# Map Docker buildx TARGETARCH -> Node's arch naming
RUN set -eux; \
    case "${TARGETARCH:-amd64}" in \
    arm64) NODE_ARCH="arm64" ;; \
    amd64) NODE_ARCH="x64" ;; \
    *) NODE_ARCH="x64" ;; \
    esac; \
    curl -fsSL "https://nodejs.org/dist/v${NODE_VERSION}/node-v${NODE_VERSION}-linux-${NODE_ARCH}.tar.xz" -o /tmp/node.tar.xz; \
    mkdir -p /opt/node; \
    tar -xJf /tmp/node.tar.xz -C /opt/node --strip-components=1; \
    ln -sf /opt/node/bin/node /usr/local/bin/node; \
    ln -sf /opt/node/bin/npm /usr/local/bin/npm; \
    ln -sf /opt/node/bin/npx /usr/local/bin/npx; \
    node --version && npm --version; \
    rm -f /tmp/node.tar.xz

# Ensure Node is available on PATH even without symlinks
ENV NODE_HOME=/opt/node
ENV PATH="${NODE_HOME}/bin:${PATH}"

# Install tree-sitter CLI globally and ensure the binary is on PATH
RUN npm install -g tree-sitter-cli && \
    ln -sf /opt/node/bin/tree-sitter /usr/local/bin/tree-sitter && \
    tree-sitter --version

WORKDIR /home/ubuntu
