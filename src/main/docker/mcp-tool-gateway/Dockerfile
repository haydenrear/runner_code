FROM localhost:5005/node

ENV NODE_HOME=/opt/node
ENV PATH="${NODE_HOME}/bin:${PATH}"


RUN mkdir -p /home/ubuntu/mcp-tools/node-tools/postgres

COPY tsconfig.json /tsconfig.json

WORKDIR /home/ubuntu/mcp-tools/node-tools
COPY postgres-mcp ./postgres
WORKDIR /home/ubuntu/mcp-tools/node-tools/postgres
RUN npm install
RUN npm ci --ignore-scripts --omit-dev

WORKDIR /home/ubuntu

ENV NODE_ENV=production



RUN node --version && npm --version && uv --version && java --version
