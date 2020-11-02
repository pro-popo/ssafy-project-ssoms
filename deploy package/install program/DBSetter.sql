create database ssoms;
use ssoms;

create table user(
    email varchar(30) not null,
    pw varchar(100) not null,
    name varchar(10) not null,
    employee_id varchar(20) not null,
    is_admin boolean not null,
    is_report_activate boolean not null,
    report_time int,
    phone_number varchar(11) not null,
    primary key(email)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table set_alarms(
    email varchar(30) not null,
    cpu_90_percent boolean,
    cpu_80_percent boolean,
    cpu_70_percent boolean,
    memory_90_percent boolean,
    memory_80_percent boolean,
    memory_70_percent boolean,
    constraint foreign key(email) references user(email)
   on delete cascade
   on update cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table user_visit_times(
    email varchar(30) not null,
    time varchar(20),
    constraint foreign key(email) references user(email)
   on delete cascade
   on update cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8;