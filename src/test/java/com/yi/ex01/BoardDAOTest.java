package com.yi.ex01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDAOTest {

	@Autowired
	public BoardDAO dao;
	
	@Test
	public void testDao() {
		System.out.println(dao);
	}
	
	//@Test
	public void testInsert() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setTitle("test6");
		vo.setContent("test6");
		vo.setWriter("test6");
		dao.insert(vo);
	}
	
	//@Test
	public void testReadyByNo() throws Exception {
		BoardVO selectVO = dao.readByNo(4);
	}
	
	//@Test
	public void testList() throws Exception {
		dao.list();
	}
	
	//@Test
	public void testUpdate() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setBno(5);
		vo.setTitle("업데이트-테스트5");
		vo.setContent("업데이트-테스트5");
		dao.update(vo);	
	}
	
	//@Test
	public void testDelete() throws Exception {
		dao.delete(6);
	}
	
	//@Test
	public void testListPage() throws Exception{
		dao.listPage(29); // 첫번째 페이지 나오게
	}
	
	@Test
	public void testListCriteria() throws Exception{
		Criteria cri = new Criteria();
		cri.setPage(2); //2번째 페이지에 대한 10개
		cri.setPerPageNum(1); // 나오는 게시글 1개로
		dao.listCriteria(cri);
	}
	
}
