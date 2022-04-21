create table tags(
    node_id serial not null references nodes (id),
    k varchar(255),
    v varchar(255),
    primary key(node_id, k)
)