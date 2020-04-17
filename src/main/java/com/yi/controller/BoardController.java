package com.yi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired // 주입받기
	BoardService service;
	
	@RequestMapping(value = "/board/register", method = RequestMethod.GET)
	public String registerGet() {
		return "/board/register";
	}
	
	@RequestMapping(value = "/board/register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception {
		//System.out.println("regiser POST----------------" + vo); //컨트럴로에 잘 넘어옴
		service.create(vo);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public String list(Model model) throws Exception { //모델이 이 함수가 가지고 있는 리스트를 반환
		List<BoardVO> list = service.list();
		model.addAttribute("list",list);
		return "/board/list";
	}

	@RequestMapping(value = "/board/read", method = RequestMethod.GET)
	public String read(int bno, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		return "/board/read";
	}
	
	//수정- 수정화면 보여주고
	@RequestMapping(value = "/board/update", method = RequestMethod.GET)
	public String updateGet(int bno,Model model) throws Exception{
		
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		
		return "/board/modify";
	}
	
	//수정 - 리스트로 가기
	@RequestMapping(value = "/board/update", method = RequestMethod.POST)
	public String updatePost(BoardVO vo) throws Exception{
		//System.out.println(vo);
		service.update(vo);
		//System.out.println(vo);
		return  "redirect:/board/list";
	}
	
	//삭제
	@RequestMapping(value = "/board/delete", method = RequestMethod.GET)
	public String delete(int bno) throws Exception{
		service.delete(bno);
		return "redirect:/board/list";
	}
	
	
	@RequestMapping(value = "/board/listPage", method = RequestMethod.GET)
	public String listPage(Criteria cri, Model model) throws Exception {
		List<BoardVO> list = service.listCriteria(cri);
		model.addAttribute("list", list);
		return "/board/listPage";
	}
}
