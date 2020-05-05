package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;
import com.yi.persistence.BoardDAO;

//서비스 : 로직처리작업 - DAO를 적절하게 호출
@Service
public class BoardService {
	
	@Autowired //주입을 받게됨
	BoardDAO dao;
	
	@Transactional
	public void create(BoardVO vo) throws Exception { //게시글 등록
		dao.insert(vo); //게시글 자동
		
		//파일 이름을 tbl_attach추가
		for(String file: vo.getFiles()) {
			dao.addAttach(file); //LAST_INSERT_ID()로 마지막 입력된 게시글의 번호를 알 수 있다 --> bno를 쓸 필요가 없어짐
		}
	}
	
	public BoardVO readByNo(int bno) throws Exception {
		dao.updateViewCnt(bno);
		return dao.readAndAttachByBno(bno);//attach의 정보도 가지고  // return dao.readByNo(bno);
	}
	
	public List<BoardVO> list() throws Exception{
		return dao.list();
	}
	

	@Transactional
	public void update(BoardVO vo) throws Exception {
		dao.update(vo);
		System.out.println(vo);
		
		//파일 이름을 tbl_attach추가
		for(String file: vo.getFiles()) {
			dao.modAttach(file, vo.getBno()); 
		}
	}
	
	@Transactional
	public void delete(int bno) throws Exception {
		dao.deleteAttach(bno);
		dao.delete(bno);
	}
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception{
		return dao.listCriteria(cri);
	}
	
	public int totalCount() throws Exception {
		return dao.totalCount();
	}
	
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception{
		return dao.listSearchCriteria(cri);
	}
	
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		return dao.totalSearchCount(cri);
	}

	public void removeImg(String delfiles) throws Exception {
		dao.removeImg(delfiles);		
	}
	
}
