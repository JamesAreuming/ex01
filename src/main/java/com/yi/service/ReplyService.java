package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;
import com.yi.persistence.BoardDAO;
import com.yi.persistence.ReplyDAO;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyDAO dao;
	
	@Autowired
	private BoardDAO boardDao;
	
	@Transactional //dao두개 - 트랜잭션처리
    public void insert(ReplyVO vo) throws Exception{
    	dao.insert(vo);
    	boardDao.updateReplyCnt(1, vo.getBno());
    }
	
    public List<ReplyVO> list(int bno) throws Exception{
    	return dao.list(bno);
    }
    
    public void update(ReplyVO vo) throws Exception{
    	dao.update(vo);
    }
    
    
    //댓글삭제
	@Transactional
    public void delete(int rno) throws Exception{
    	//rno로 검색해서 replyVO받아  bno를 검색

    	ReplyVO vo = dao.selectByRno(rno);
    	//System.out.println(vo.toString());
    	dao.delete(rno);
    	boardDao.updateReplyCnt(-1, vo.getBno());

    }
    
    
    public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception{
    	return dao.listPage(bno, cri);
    } 
    
    public int totalCount(int bno) throws Exception{
    	return dao.totalCount(bno);
    }
    
    
}
