package com.yi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.domain.ReplyVO;
import com.yi.service.ReplyService;

@RequestMapping("/replies/*")
@RestController //모두 아작스 형식으로 되어있을때
public class ReplyController {

	@Autowired
	private ReplyService service;
	
	//등록 - 1 : POST
	@RequestMapping(value="", method = RequestMethod.POST) //value값을 비우면 replies로만 오게 됨
	public ResponseEntity<String> register(@RequestBody ReplyVO vo){ //★ @RequestBody : method 방식이 post, put일 경우, body에 실려오는 json 데이타일 경우 를 class객체에 실어줌  
		System.out.println(vo);
		
		ResponseEntity<String> entity = null;
		
		try {
			service.insert(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//리스트
	// /replies/all?bno=521  ---> /replies/all/521
	@RequestMapping(value="/all/{bno}", method = RequestMethod.GET) // /all/{bno}/{rno} 등등 아예 주소줄에 붙이기 -- @PathVariable("bno") 애노테이션을 사용해야함
	public ResponseEntity<List<ReplyVO>> list(@PathVariable("bno") int bno){
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try {
			List<ReplyVO> list = service.list(bno);
			entity = new ResponseEntity<List<ReplyVO>>(list, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST); //400 err
		}
		return entity;
	}
	
	//수정
	// replies/1
	@RequestMapping(value="/{rno}", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable("rno") int rno, @RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		System.out.println(vo);
		System.out.println(rno);
		try {
			//넘어온 rno를 vo에 넣기
			vo.setRno(rno); //주소줄에 넘어온 rno 번호를 넣음
			service.update(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//삭제
	// replise/{rno} - 메소트타입 : delete  --> success / fail
	@RequestMapping(value="/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") int rno){
		ResponseEntity<String> entity = null;
		System.out.println(rno);
		
		try {
			service.delete(rno);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
		}
		return entity;
		
	}
	
	
	//댓글 페이징처리
	// /replies/521/1
	@RequestMapping(value="/{bno}/{page}", method = RequestMethod.GET) // /all/{bno}/{rno} 등등 아예 주소줄에 붙이기 -- @PathVariable("bno") 애노테이션을 사용해야함
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") int bno, @PathVariable("page") int page){
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			//pageMaker.setTotalCount(44); //select count(rno) from tbl_reply where bno = 521;
			pageMaker.setTotalCount(service.totalCount(bno)); // 현재 34 - 페이지 4개로 계산이 되어야 함 (1페이지당 10개)
			
			List<ReplyVO> list = service.listPage(bno, cri);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			
			System.out.println(bno); //찍기
			System.out.println(cri); //찍기
			System.out.println(page); //찍기
			System.out.println(list); // 없는경우 []로 찍혀서 나옴
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST); //400 err
		}
		return entity;
	}
	
//	@RequestMapping(value="/replyTest", method = RequestMethod.GET)
//	public String replyTest() {
//		return "replyTest";
//	}
	
}
