create table if not exists nodes
(
    id        bigint not null primary key,
    lat       double precision,
    lon       double precision,
    usr       varchar(255),
    uid       bigint,
    visible   boolean,
    version   bigint,
    changeset bigint,
    timestamp timestamp
)