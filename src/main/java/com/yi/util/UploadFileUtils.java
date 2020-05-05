package com.yi.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

//업로드 전용 유틸 클래스
public class UploadFileUtils {
	
	public static String uploadFile(String uploadPath, String filename, byte[] fileDate) throws IOException {
		//생성 : 기본 폴더 만들기
		File dir = new File(uploadPath); 
		if(dir.exists() == false) { //upload폴더가 없다면
			dir.mkdir(); //upload 폴더만들기
		}
		
		//생성 : 년,월,일 폴더
		String target = calcPath(uploadPath); // /2020/04.29
		
		//생성 : 원본이미지
		UUID uid = UUID.randomUUID(); //중복되지 않는 고유한 키값을 반환함
		String savedName = uid.toString() + "_" + filename; //중복되지 않게 이름 맞춤 : 5ad4ad49-ef72-448e-9acc-7605f4eaa5a8_areuming
		File file = new File(uploadPath +target+"/"+ savedName); 
		FileCopyUtils.copy(fileDate, file); //서버 upload폴더 안에 파일이 생성됨
		
		//생성 : 작은 이미지
		String thumbPath = makeThumbnail(uploadPath, target, savedName);
		
		return thumbPath; //  작은이미지 경로를 반환 /2020/04/29/2222_sImage.jpg              //  target + "/" + savedName; //2020/04/29
	}
	
	
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();
		String yearPath = "/" + cal.get(Calendar.YEAR); // 2020
		String monthPath = String.format("%s/%02d", yearPath, cal.get(Calendar.MONTH)+1); // 2020/04
		String datePath = String.format("%s/%02d", monthPath, cal.get(Calendar.DATE)); // 2020/04/29
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath; //년월일 : 2020/04/29
	}
	
	private static void makeDir(String uploadPath, String... paths) {
		for(String path: paths) {
			File dir = new File(uploadPath+path); //uploadPath:c:/zzz/upload/  ----> +path : 년도, 월, 일 폴더 만들어야함
			if(dir.exists() == false) {
				dir.mkdir();
			}
		}
	}
	
	//uploadPath : root 
	//path : 년/월/일 폴더
	//fileName : 오리지널 파일 이름
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws IOException {
		
		//원본 이미지 데이터를 컴퓨터 상의 가상 도화지에 옮겨옴
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath+path+"/"+fileName));
		
		//가상 도화지에 옮겨진 원본을 기준으로 작은 이미지를 가상공간에 만듬
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100); //FIT_TO_HEIGHT: 높이값을 고정 ---> 100px로 고정 // 가로고정 :FIT_TO_WIDTH 
		
		String thumbnailName = uploadPath + path + "/s_" + fileName; //작은용 이미지라 표시하기 위해  s_를 붙임
		
		//해당 경로에 빈파일 만듬
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		//가상 작은 이미지 데이터를 원하는 경로에 저장을 함
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		//작은 이미지 경로를 리턴  : 루트경로(c:/zzz/upload/)빼고 /2020/04/29/2222_sImage.jpg 이미지를 반환
		return thumbnailName.substring(uploadPath.length()); // /2020/04/29/2222_sImage.jpg
				
	}
}
