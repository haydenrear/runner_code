# Python base image with Docker CLI — for containers that need to
# invoke other Docker containers (e.g. view-agent-exec calling
# view-agents-utils via docker run).
FROM ghcr.io/astral-sh/uv:python3.11-bookworm-slim

# Install Docker CLI (not the daemon — we mount the host socket)
# RUN apt-get update && \
#     apt-get install -y --no-install-recommends \
#         ca-certificates \
#         curl \
#         gnupg && \
#     install -m 0755 -d /etc/apt/keyrings && \
#     curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg && \
#     chmod a+r /etc/apt/keyrings/docker.gpg && \
#     echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo "$VERSION_CODENAME") stable" > /etc/apt/sources.list.d/docker.list && \
#     apt-get update && \
#     apt-get install -y --no-install-recommends docker-ce-cli && \
#     rm -rf /var/lib/apt/lists/*


ENV UV_COMPILE_BYTECODE=1
ENV PATH="/app/.venv/bin:/uv:$PATH"

# Install git for hash resolution (git hash-object)
RUN apt-get update && \
    apt-get install -y --no-install-recommends git && \
    rm -rf /var/lib/apt/lists/*

ENV UV_COMPILE_BYTECODE=1
