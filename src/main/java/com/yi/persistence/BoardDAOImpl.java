package com.yi.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO{

	@Autowired
	private SqlSession sqlSession;
	
	private static final String namespace = "mappers.BoardMapper.";
	
	@Override
	public void insert(BoardVO vo) throws Exception {
		sqlSession.insert(namespace+"insert", vo);
		
	}

	@Override
	public BoardVO readByNo(int bno) throws Exception {
		return sqlSession.selectOne(namespace+"readByNo", bno);
	}

	@Override
	public List<BoardVO> list() throws Exception {
		return sqlSession.selectList(namespace+"list");
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update(namespace + "update", vo);
	}

	@Override
	public void delete(int bno) throws Exception {
		sqlSession.delete(namespace+"delete",bno);
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		//1 -> 0, 2 -> 10, 3-> 20
		
		if(page < 0) {
			page = 1;
		}
		
		page = (page-1)*10;
		return sqlSession.selectList(namespace+"listPage", page);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {

		return sqlSession.selectList(namespace+"listCriteria", cri);
	}

	@Override
	public int totalCount() throws Exception {
		return sqlSession.selectOne(namespace+"totalCount");
	}

	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		return sqlSession.selectList(namespace+"listSearchCriteria", cri);
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne(namespace+"totalSearchCount", cri);
	}

	@Override
	public void updateReplyCnt(int amount, int bno) throws Exception {
		//mapper에는 매개변수 1개만 넘겨야 하므로 map을 써서 이용
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("amount", amount);
		map.put("bno", bno);		
		sqlSession.update(namespace +"updateReplyCnt",map);
		
	}

	@Override
	public void updateViewCnt(int bno) throws Exception {
		sqlSession.update(namespace +"updateViewCnt", bno);
		
	}

	@Override
	public void addAttach(String fullname) throws Exception {
//		Map<String, Object> map = new HashMap<String, Object>(); // string, int 라서 지정할 수 없어서 Object
//		map.put("fullname",fullname);
//		map.put("bno",bno);
		sqlSession.insert(namespace+"addAttach",fullname);
				
		
	}

	@Override
	public BoardVO readAndAttachByBno(int bno) throws Exception {
		return sqlSession.selectOne(namespace+"readAndAttachByBno", bno);
	}

	@Override
	public void removeImg(String fullname) throws Exception {
		sqlSession.delete(namespace+"removeImg",fullname);
		
	}

	@Override
	public void deleteAttach(int bno) throws Exception {
		sqlSession.delete(namespace+"deleteAttach",bno);
		
	}

	@Override
	public void modAttach(String fullname, int bno) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("fullname",fullname);
		map.put("bno",bno);
		sqlSession.insert(namespace+"modAttach",map);
	}

}
