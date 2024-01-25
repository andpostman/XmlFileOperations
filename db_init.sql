create schema if not exists dbo;

create sequence if not exists dbo.person_pkg
    minvalue 1 no maxvalue
    start with 1;

create table if not exists dbo.person(
    person_id int primary key default nextval('dbo.person_pkg' :: regclass),
    date_born date not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    middle_name varchar(255) null,
    UN_ID varchar(50) not null,
    internet_address varchar(255) null,
    modified_date_time timestamp not null,
    insert_date timestamp not null,
    insert_user varchar(50) not null
);


create unique index if not exists "person_id_idx" on dbo.person using btree(person_id asc);

alter table dbo.person cluster on person_id_idx;

create sequence if not exists dbo.card_pkg
    minvalue 1 no maxvalue
    start with 1;

create table if not exists dbo.card(
    card_id int primary key default nextval('dbo.card_pkg' :: regclass),
    name_jur text not null,
    type text not null,
    tmp_prizn text not null,
    prizn text not null,
    forma text not null,
    forma_full text not null,
    INN text not null,
    KPP text not null,
    OKPO text not null,
    act text not null,
    tmp_card_type text not null,
    name_jur_eng text not null,
    PBOYuL text not null,
    birthday text not null,
    grade text not null,
    last_name text not null,
    first_name text not null,
    middle_name text not null,
    title text not null,
    last_name_e text not null,
    first_name_e text not null,
    middle_name_e text not null,
    country text not null,
    zip text not null,
    region text not null,
    area text not null,
    town text not null,
    street text not null,
    home text not null,
    building text not null,
    flat text not null,
    live_country text not null,
    live_zip text not null,
    live_region text not null,
    live_area text not null,
    live_town text not null,
    live_street text not null,
    live_home text not null,
    live_building text not null,
    live_flat text not null,
    country_e text not null,
    zip_d text not null,
    region_e text not null,
    area_e text not null,
    town_e text not null,
    street_e text not null,
    home_e text not null,
    building_e text not null,
    flat_e text not null,
    locations text not null,
    unid text not null,
    doc_par text not null,
    link_LN text not null,
    id_client_update_queue text not null,
    id_client text not null,
    modified_user text not null,
    ur_phone text not null,
    live_phone_city text not null,
    phone text not null,
    is_customer text not null,
    structure text not null,
    structure_unid text not null,
    contr_role text not null
);

create unique index if not exists "card_id_idx" on dbo.card using btree(card_id asc);

alter table dbo.card cluster on card_id_idx;

create sequence if not exists dbo.currency_form_pkg
    minvalue 1 no maxvalue
    start with 1;

create table if not exists dbo.currency_form(
    currency_form_id int primary key default nextval('dbo.currency_form_pkg' :: regclass),
    cur_type text not null,
    code_cur text not null,
    xcode_cur text not null,
    course text not null,
    date timestamp not null,
    cur int not null
);

create sequence if not exists dbo.cur_type_dep_pkg
    minvalue 1 no maxvalue
    start with 1;

create table if not exists dbo.cur_type_dep(
       id int primary key default nextval('dbo.cur_type_dep_pkg' :: regclass),
       cur_type_dep text not null,
       currency_form_id int references dbo.currency_form(currency_form_id) on delete no action
);

create sequence if not exists dbo.code_cur_dep_pkg
    minvalue 1 no maxvalue
    start with 1;

create table if not exists dbo.code_cur_dep(
    id int primary key default nextval('dbo.code_cur_dep_pkg' :: regclass),
    code_cur_dep text not null ,
    currency_form_id int references dbo.currency_form(currency_form_id) on delete no action
);

create sequence if not exists dbo.xcode_cur_dep_pkg
    minvalue 1 no maxvalue
    start with 1;

create table if not exists dbo.xcode_cur_dep(
   id int primary key default nextval('dbo.xcode_cur_dep_pkg' :: regclass),
   xcode_cur_dep text not null ,
   currency_form_id int references dbo.currency_form(currency_form_id) on delete no action
);

create unique index if not exists "currency_form_id_idx" on dbo.currency_form using btree(currency_form_id asc);

alter table dbo.currency_form cluster on currency_form_id_idx;

TRUNCATE dbo.card, dbo.code_cur_dep, dbo.cur_type_dep, dbo.xcode_cur_dep, dbo.currency_form, dbo.person CASCADE;