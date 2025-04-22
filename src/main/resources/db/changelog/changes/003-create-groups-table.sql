create table groups (
                        id            bigint       not null primary key,
                        group_code    varchar(50)  not null,
                        year          integer      not null,
                        specialization varchar(255) not null
);

alter table groups owner to root;
create sequence group_id_seq;
alter sequence group_id_seq owner to root;
