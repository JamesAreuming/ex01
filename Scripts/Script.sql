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


-- 댓글 숫자 표현
alter table tbl_board add column replycnt int default 0;


update tbl_board set replycnt  = (select count(rno) from tbl_reply where bno = tbl_board .bno);

select count(rno) from tbl_reply where bno = 1;
update tbl_board set viewcnt = viewcnt+1 where  


-- 여러 파일 업로드
create table tbl_attach(
	fullName varchar(150) not null,
	bno int not null,
	regdate timestamp default now(),
	primary key(fullName)
);
alter table tbl_attach add constraint fk_board_attach foreign key(bno) references tbl_board(bno);

desc tbl_attach;

show tables;

select * from tbl_attach;
select * from tbl_board;

select * from tbl_board tb left join tbl_attach ta on tb.bno = ta.bno where tb.bno= 558;

delete from tbl_attach where bno= 552;
delete from tbl_board where bno= 552;

insert into tbl_attach (fullName, bno) values ('테스트2', 558);

delete tb, ta from tbl_board tb left join tbl_attach ta on tb.bno = ta.bno where ta.bno= 552;

delete from tbl_attach where fullName = '';