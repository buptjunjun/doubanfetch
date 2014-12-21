create table douban_subject(
    id int(11) primary key,
    type int(11) default 0,
    fetch_count int(11) default 0,
    associated_id longtext 
)
