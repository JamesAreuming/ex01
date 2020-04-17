<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp"%>
<script>
$(function () {	
	//수정
	$("#btnMod").click(function() {
		var no = $(this).attr("data-no");
		location.href="${pageContext.request.contextPath}/board/update?bno="+no;
	})
	
	//삭제
	$("#btnRemove").click(function() {
		var no = $(this).attr("data-no");
		console.log(no);
		var result = confirm("정말 삭제하시겠습니까?");
		if(result){
			location.href="${pageContext.request.contextPath}/board/deletePage?bno="+no+"&page=${cri.page}";
		}
	})		
	//리스트
	$("#btnList").click(function() {
		location.href="${pageContext.request.contextPath}/board/listPage?page=${cri.page}";
	})	
});	
</script>
<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ</h3>
				</div>
				<div class="box-body">
					<div class="form-group">
						<label>BNO</label>
						<input type="text" class="form-control" value="${board.bno}" readonly>
					</div>				
					<div class="form-group">
						<label>Title</label>
						<input type="text" class="form-control" value="${board.title}" readonly>
					</div>
					<div class="form-group">
						<label>Content</label>
						<textarea rows="5" cols="30" class="form-control" readonly>${board.content}</textarea>
					</div>					
					<div class="form-group">
						<label>Writer</label>
						<input type="text" class="form-control" value="${board.writer}" readonly>
					</div>
					<div class="box-footer">
						<button class="btn btn-warning" id="btnMod" data-no="${board.bno}">Modify</button>
						<button class="btn btn-danger" id="btnRemove" data-no="${board.bno}">Remove</button>
						<button class="btn btn-primary" id="btnList">Go List</button>
					</div>										
				</div>
			</div>
		</div>	
	</div>
</div>
<%@ include file = "../include/footer.jsp"%>