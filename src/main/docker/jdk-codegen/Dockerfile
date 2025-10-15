FROM localhost:5005/python

ARG DEBIAN_FRONTEND=noninteractive
# Pin toolchain versions here for org-wide consistency
ARG GRADLE_VERSION=8.12
ARG RUST_TOOLCHAIN=1.90.0
ARG UV_VERSION=0.5.0

LABEL org.opencontainers.image.title="jdk-codegen"
LABEL org.opencontainers.image.description="Base JDK image with pinned Gradle, Rust/Cargo, and uv"
LABEL org.opencontainers.image.source="runner_code/src/main/docker/jdk-codegen/Dockerfile"
LABEL com.hayden.gradle.version="${GRADLE_VERSION}"
LABEL com.hayden.rust.toolchain="${RUST_TOOLCHAIN}"
LABEL com.hayden.uv.version="${UV_VERSION}"

# Standardize HOME (consistent with other images in this repo)
ENV HOME=/home/ubuntu
ENV CARGO_HOME=${HOME}/.cargo
ENV RUSTUP_HOME=${HOME}/.rustup
ENV GRADLE_HOME=/opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH=${GRADLE_HOME}/bin:${CARGO_HOME}/bin:/usr/local/bin:${PATH}

RUN uv --version

# System packages
# - curl, unzip, ca-certificates: downloads and installs
# - build-essential, pkg-config: compilation toolchain for Rust crates
# - git: used across the org for code fetch
RUN set -eux; \
    apt-get update; \
    apt-get install -y --no-install-recommends \
    curl \
    unzip \
    ca-certificates \
    build-essential \
    pkg-config \
    git; \
    rm -rf /var/lib/apt/lists/*; \
    mkdir -p "${HOME}"

# Install Gradle (binary distribution)
RUN set -eux; \
    curl -fsSL "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" -o /tmp/gradle.zip; \
    mkdir -p /opt/gradle; \
    unzip -q /tmp/gradle.zip -d /opt/gradle; \
    ln -sf "${GRADLE_HOME}/bin/gradle" /usr/local/bin/gradle; \
    gradle -v; \
    rm -f /tmp/gradle.zip

# Install Rust toolchain (pinned) with rustup and ensure cargo in PATH
RUN set -eux; \
    curl -fsSL https://sh.rustup.rs -o /tmp/rustup-init.sh; \
    sh /tmp/rustup-init.sh -y --default-toolchain ${RUST_TOOLCHAIN} --profile default; \
    rm -f /tmp/rustup-init.sh; \
    "${CARGO_HOME}/bin/rustc" --version; \
    "${CARGO_HOME}/bin/cargo" --version


RUN set -eux; \
    echo "GRADLE_VERSION=${GRADLE_VERSION}"; \
    echo "RUST_TOOLCHAIN=${RUST_TOOLCHAIN}"; \
    echo "UV_VERSION=${UV_VERSION}"

WORKDIR /home/ubuntu
