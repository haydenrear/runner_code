ARG PG_MAJOR=17

FROM postgres:${PG_MAJOR}

RUN apt-get update && \
    apt-mark hold locales && \
    apt-get install -y --no-install-recommends \
        build-essential \
        postgresql-server-dev-${PG_MAJOR} \
        postgresql-contrib-${PG_MAJOR}

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

# Enable logical replication for CDC
RUN echo "wal_level = logical" >> /usr/share/postgresql/postgresql.conf.sample
RUN echo "max_wal_senders = 8" >> /usr/share/postgresql/postgresql.conf.sample
RUN echo "max_replication_slots = 8" >> /usr/share/postgresql/postgresql.conf.sample

WORKDIR /
EXPOSE 5432

CMD ["docker-entrypoint.sh", "postgres"]
