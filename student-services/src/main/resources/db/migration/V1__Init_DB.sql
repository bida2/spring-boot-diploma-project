 create table companies (id int8 not null, bulstat varchar(255) not null, company_name varchar(255) not null, email varchar(255) not null, location varchar(255) not null, password varchar(255) not null, phone_number varchar(255) not null, user_id int8, primary key (id))
 create table companies_stock (company_id int8 not null, stock_id int8 not null, primary key (company_id, stock_id))
 create table role (id int8 not null, name varchar(255), primary key (id))
 create table stock (id int8 not null, measuring_units varchar(255) not null, price_per_item float4 not null, quantity varchar(255) not null, stock_description varchar(255) not null, stock_name varchar(255) not null, thumb_path varchar(255) not null, weight_one float4 not null, primary key (id))
 create table stock_group (id int8 not null, stock_group_name varchar(255) not null, primary key (id))
 create table stocks_all_groups (stock_id int8 not null, group_id int8 not null)
 create table user_role (user_id int8 not null, role_id int8 not null, primary key (user_id, role_id))
 create table users (id int8 not null, password varchar(255), username varchar(255), primary key (id))
 create sequence hibernate_sequence start 1 increment 1
 alter table companies add constraint FK9l5d0fem75e59uwf9upwuf9du foreign key (user_id) references users
 alter table companies_stock add constraint FK2ls3qu3dr1jvfft3bnuciyr87 foreign key (stock_id) references stock
 alter table companies_stock add constraint FK4j08xuuibs53o9y8j6t0ue5bo foreign key (company_id) references companies
 alter table stocks_all_groups add constraint FK4arbij2ftpoloc0o13oebgdtd foreign key (group_id) references stock_group
 alter table stocks_all_groups add constraint FKbm9wpc4myx8h0vtb3w34klpft foreign key (stock_id) references stock
 alter table user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role
 alter table user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users
 delete from companies_stock where (company_id) in (select id from companies)
 delete from companies
 delete from companies_stock where (stock_id) in (select id from stock)
 delete from stocks_all_groups where (stock_id) in (select id from stock)
 delete from stock
 delete from user_role where (role_id) in (select id from role)
 delete from role
 delete from user_role where (user_id) in (select id from users)
 delete from users
 delete from user_role where (role_id) in (select id from role)
 delete from role
 delete from stocks_all_groups where (group_id) in (select id from stock_group)
 delete from stock_group
 alter table stock_group add unique(stock_group_name)
