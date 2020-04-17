package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.persistence.BoardDAO;

//서비스 : 로직처리작업 - DAO를 적절하게 호출
@Service
public class BoardService {
	
	@Autowired //주입을 받게됨
	BoardDAO dao;
	
	public void create(BoardVO vo) throws Exception {
		dao.insert(vo);
	}
	
	public BoardVO readByNo(int bno) throws Exception {
		return dao.readByNo(bno);
	}
	
	public List<BoardVO> list() throws Exception{
		return dao.list();
	}
	
	
	public void update(BoardVO vo) throws Exception {
		dao.update(vo);
	}
	
	public void delete(int bno) throws Exception {
		dao.delete(bno);
	}
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception{
		return dao.listCriteria(cri);
	}
	
	public int totalCount() throws Exception {
		return dao.totalCount();
	}
}
