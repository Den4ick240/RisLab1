create table if not exists tags(
    node_id bigint not null references nodes (id),
    k varchar(255),
    v varchar(255),
    primary key(node_id, k)
)