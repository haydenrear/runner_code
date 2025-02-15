ARG PG_MAJOR=15

FROM postgres:${PG_MAJOR}

RUN apt-get update && \
    apt-mark hold locales && \
    apt-get install -y --no-install-recommends \
        build-essential \
        postgresql-server-dev-${PG_MAJOR}

WORKDIR /tmp

RUN apt-get update && \
    apt install -y git

RUN git clone --branch v0.7.2 https://github.com/pgvector/pgvector.git

WORKDIR /tmp/pgvector

RUN apt-get update && \
		apt-mark hold locales && \
		apt-get install -y --no-install-recommends build-essential postgresql-server-dev-$PG_MAJOR && \
        apt-get install make && \
		make clean && \
		make OPTFLAGS="" && \
		make install && \
		mkdir /usr/share/doc/pgvector && \
		cp LICENSE README.md /usr/share/doc/pgvector && \
		apt-get remove -y build-essential postgresql-server-dev-$PG_MAJOR && \
		apt-get autoremove -y && \
		apt-mark unhold locales && \
		rm -rf /var/lib/apt/lists/*

COPY init-pgvector.sql /docker-entrypoint-initdb.d/

WORKDIR /
EXPOSE 5432

CMD ["docker-entrypoint.sh", "postgres"]
