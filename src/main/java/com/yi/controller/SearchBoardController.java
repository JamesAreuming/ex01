package com.yi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yi.domain.BoardVO;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;
import com.yi.util.UploadFileUtils;


@Controller
@RequestMapping("/sboard/*") //command에 항상  /sboard/로 시작한다
public class SearchBoardController {
	
	@Autowired // 주입받기
	BoardService service;
	
	@Resource(name="uploadPath") //널리쓰이는 경우는 id명으로 주입시킴 : <beans:bean id="uploadPath" class="java.lang.String">
	String uploadPath;

	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public String listPage(SearchCriteria cri, Model model) throws Exception {
		
		//System.out.println(cri);
		List<BoardVO> list = service.listSearchCriteria(cri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalSearchCount(cri));
		
		model.addAttribute("cri",cri);
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		
		return "/sboard/listPage";
	}
	
	
	/** 등록 **/
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerGet() {
		return "/sboard/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo, List<MultipartFile> imgfiles) throws Exception {
		System.out.println("regiser POST----------------" + vo); //컨트럴로에 잘 넘어옴
		System.out.println(imgfiles);
		ArrayList<String> fullName = new ArrayList<String>();
		
		for(MultipartFile file : imgfiles) {
			System.out.println("파일이름 : "+file.getOriginalFilename());
			System.out.println("파일사이즈 : "+file.getSize());
			
			//upload처리
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			
			fullName.add(savedName);
		}
		vo.setFiles(fullName);

		service.create(vo);
		return "redirect:/sboard/listPage";
	}
	
	/** 상세보기 **/
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public String readPage(int bno, SearchCriteria cri, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
		model.addAttribute("searchType", cri.getSearchType() );
		model.addAttribute("keyword", cri.getKeyword());
		return "/sboard/readPage";
	}
	
	/** 게시글 삭제 **/
	//리스트 - 검색키워드 유지
	@RequestMapping(value = "/deletePage", method = RequestMethod.GET)
	public String deletePage(int bno, SearchCriteria cri, Model model) throws Exception{
		service.delete(bno);
		
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType() );
		model.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/listPage?page="+cri.getPage();
	}
	
	/** 수정 **/
	@RequestMapping(value = "/updatePage", method = RequestMethod.GET)
	public String updatePageGet(int bno, SearchCriteria cri, Model model) throws Exception{
		
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
		
		return "/sboard/modifyPage";
	}
	
	//수정 - 리스트로 가기 	//리스트 - 검색키워드 유지
	@RequestMapping(value = "/updatePage", method = RequestMethod.POST)
	public String updatePagePost(BoardVO vo, String[] delfiles, List<MultipartFile> imgfiles, SearchCriteria cri, Model model) throws Exception{
		
		//추가한 이미지파일중 삭제할 파일이 있다면
         if(delfiles != null) {
 			for(int i=0;i<delfiles.length;i++) {
				//System.out.println(delfiles[i]);	 /// 2020/05/04/s_60d05b02-e320-4bff-8fe8-3d31cb22fbd7_메인 헤더.PNG
				//System.out.println(delfiles[i].substring(0,12));	
				//System.out.println(delfiles[i].substring(14));
				
				String test1 = delfiles[i].substring(0,12);
				String test2 = delfiles[i].substring(14);
				
				
				
				service.removeImg(delfiles[i]);
				
				//썸네일 지우기
				File file = new File(uploadPath+delfiles[i]);
				file.delete();
				
				
				//원본파일 지우기
				String orignName = test1 + test2;
				File orignfile = new File(uploadPath+orignName);
				orignfile.delete();					
				
			}       	 
         }		
         
 		//새로 추가할 파일이 있다면
	    System.out.println("찍혀라"+imgfiles);
		    ArrayList<String> fullName = new ArrayList<String>();
			
			for(MultipartFile file : imgfiles) {
				System.out.println("파일이름 : "+file.getOriginalFilename());
				System.out.println("파일사이즈 : "+file.getSize());
				if(file.getSize() != 0) {
					//upload처리
					String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
					
					fullName.add(savedName);
				}
			}
			vo.setFiles(fullName);
        
		service.update(vo);
		
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType() );
		model.addAttribute("keyword", cri.getKeyword());
		return  "redirect:/sboard/listPage?page="+cri.getPage();
	}
	
	//아작스처리
	@ResponseBody
	@RequestMapping(value="displayFile", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity = null;
		
		//System.out.println("displayFile------" + filename);
		
		InputStream in = null;
		try {
			in = new FileInputStream(uploadPath+filename);
			
			//while문 처리 없이 IOUtils.toByteArray(in)
			
			String format = filename.substring(filename.lastIndexOf(".")+1); //확장자 뽑아내기
			MediaType mType = null;
			if (format.equalsIgnoreCase("png")) {
				mType = MediaType.IMAGE_PNG;
			}else if(format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")){
				mType = MediaType.IMAGE_JPEG;
			}else if(format.equalsIgnoreCase("gif")){
				mType = MediaType.IMAGE_GIF;
			}else {
				//mType = MediaType.T
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mType);
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers,HttpStatus.OK);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}	
}
