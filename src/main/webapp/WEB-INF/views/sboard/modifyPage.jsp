<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp"%>
<style>
	.imgBox{
		width: 100%;
		height: 200px;
		border: 1px solid black;
	}
	.imgBox .wrap{
		width: 100px;
		height: 100px;
		position: relative;
		float: left;
	}
	.imgBox .wrap img{
		width: 90px;
		height: 90px;
		
	}	
	.imgBox .wrap input{
		position: absolute;
		left:0;
		top:0;
	}
	span.red{
		font-weight: bold;
		color: red;
	}
	
	#dropBox{
		width: 100%;
		height: 200px;
		border: 1px solid black;	
	}
	#dropBox img{
		width: 90px;
		height: 90px;	
	}	
</style>
<script>
	$(function() {
/*  		$("input:checkbox").on("click",function(){
			if ( $(this).prop("checked") ) {
				$(this).parent().css("opacity","0.3");
			}else{
				$(this).css("opacity","none");
			}
		}) 	 */
		
		$("input:checkbox").change(function() {
			if($(this).is(":checked")){
				$(this).parent().css("opacity","0.3");
				
				var filename = $(this).attr("value");
				//alert(filename);
				
				var test1 = filename.substring(0,12);
				var test2 = filename.substring(14);
				//alert(test1+test2);
				/* 파일이름 가져오기 */
				/* var filename = $(this).attr("value");
				alert(filename);
				var result = confirm("정말 삭제하시겠습니까?");
				if(result){
					$(this).parent().remove();*/
				/* } */ 
			}else{
				$(this).parent().css("opacity","1");
			}
		})
		
		
		$("#file").change(function() {
			//var file = $(this)[0].files[0]; // $(this)[0] : javascript 객체
			
			var files = $(this)[0].files;
			//var file = e.target.files;        https://greatps1215.tistory.com/5
			console.log(files);
			$("#dropBox").empty();
 			for(var i = 0; i<files.length;i++){
				var reader = new FileReader(); //javascript 객체
				reader.readAsDataURL(files[i]);
				reader.onload = function(e){
					var $img = $("<img>").attr("src", e.target.result);
					$("#dropBox").append($img);
				}
 			}
		})
		
	})
</script>
<!-- 기본골격 -->
<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Modify</h3>
				</div>
				<form role="form" action="updatePage" method="post" enctype="multipart/form-data">				
					<div class="box-body">
						<div class="form-group">
							<label>BNO</label>
							<input type="text" class="form-control" value="${board.bno}" readonly>
							<input type="hidden" value="${board.bno}" name="bno"> <!-- name을 줘야함 -->
							<input type="hidden" value="${cri.page}" name="page">
							<input type="hidden" value="${cri.searchType}" name="searchType">
							<input type="hidden" value="${cri.keyword}" name="keyword">
							<!-- post로 가면 form태그 안에서 모두 처리 - hidden으로 숨겨서 처리해야함 --> 
						</div>				
						<div class="form-group">
							<label>Title</label>
							<input type="text" class="form-control" name="title" value="${board.title}"> <!-- name을 줘야함 -->
						</div>
						<div class="form-group">
							<label>Content</label>
							<textarea rows="5" cols="30" name="content" class="form-control">${board.content}</textarea> <!-- name을 줘야함 -->
						</div>					
						<div class="form-group">
							<label>Writer</label>
							<input type="text" class="form-control" value="${board.writer}" readonly>
						</div>
						
						<!-- 이미지 수정  -->
						<label><span class="red">기존 추가 이미지: 수정→삭제</span></label>
						<div class="form-group imgBox">
							<c:forEach var="file" items="${board.files}">
								<div class="wrap">
									<img src = "displayFile?filename=${file}">
									<input type="checkbox" name="delfiles" value="${file}">
								</div>
							</c:forEach>
						</div>
						
						<!-- 이미지 새로 등록 -->
						<div class="form-group">
							<label><span class="red">이미지 새로 등록 : 추가</span></label>
							<input type="file" name="imgfiles" class="form-control" multiple="multiple" id="file">
						</div>
						<div id="dropBox"></div>																			
						<div class="box-footer">
							<button type="submit" class="btn btn-primary" id="btnList">수정 완료</button>
						</div>
					</div>
				</form>
			</div>
		</div>	
	</div>
</div>
<%@ include file = "../include/footer.jsp"%>