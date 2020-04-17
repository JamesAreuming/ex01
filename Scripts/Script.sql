select user(), database ();

create table tbl_board(
	bno int not null auto_increment,
	title varchar(200) not null,
	content text null,
	writer varchar(50) not null,
	regdate timestamp not null default now(),
	viewcnt int default 0,
	primary key(bno)
);

show tables;

select * from tbl_board;


select * from tbl_board order by bno desc;

-- 자가증식
insert into tbl_board (title, content, writer)
(select title, content, writer from tbl_board );

-- 칼럼 수
select count(bno) from tbl_board;

-- 게시글 10개
select * from tbl_board order by bno desc limit 0, 10; -- 10개
select * from tbl_board order by bno desc limit 10,10; -- 10번부터 10개
select * from tbl_board order by bno desc limit 20,10; -- 20번부터 10개