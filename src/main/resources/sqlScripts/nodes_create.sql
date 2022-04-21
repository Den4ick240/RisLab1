create table nodes (
    id serial not null primary key,
    lat numeric(10),
    lon numeric(10),
    usr varchar(255),
    uid bigint,
    visible boolean,
    version bigint,
    changeset bigint,
    timestamp timestamp
)