drop table if exists transaction;
drop table if exists card;
drop table if exists fraud_verifier;

create table card (
    id bigint not null auto_increment,
    number varchar(255) unique,
    holder_name varchar(255),
    expiration varchar(255),
    security_code varchar(255),
    issuing_company varchar(255),
    total_limit decimal(19,2),
    available_limit decimal(19,2),
    full_name varchar(255),
    address varchar(255),
    email varchar(255),
    monthly_fee decimal(19,2),
    primary key (id)
) engine=InnoDB default charset=latin1;

create table fraud_verifier (
    id bigint not null auto_increment,
    enabled bit not null,
    type varchar(100) unique,
    primary key (id)
) engine=InnoDB default charset=latin1;

create table transaction (
    id bigint not null auto_increment,
    amount decimal(19,2),
    created_at datetime(6),
    description varchar(1000),
    status varchar(20),
    uuid varchar(255),
    card_id bigint,
    primary key (id)
) engine=InnoDB default charset=latin1;

alter table transaction
    add constraint FK_transaction_card
        foreign key (card_id)
            references card (id);