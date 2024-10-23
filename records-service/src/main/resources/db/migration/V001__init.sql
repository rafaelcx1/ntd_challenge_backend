create table records (
    id integer not null auto_increment,
    operation_id integer not null,
    user_id integer not null,
    amount decimal(6, 2),
    user_balance decimal(6, 2),
    operation_response varchar(255),
    date datetime(6),
    is_deleted BOOLEAN,
    primary key (id)
);
