<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp"%>
<script>
	//리스트
	$("#btnList").click(function() {
		location.href="${pageContext.request.contextPath}/board/listPage?page=${cri.page}";
</script>
<!-- 기본골격 -->
<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Modify</h3>
				</div>
				<form role="form" action="updatePage" method="post">				
					<div class="box-body">
						<div class="form-group">
							<label>BNO</label>
							<input type="text" class="form-control" value="${board.bno}" readonly>
							<input type="hidden" value="${board.bno}" name="bno"> <!-- name을 줘야함 -->
							<input type="hidden" value="${cri.page}" name="page"> 
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
						<div class="box-footer">
							<button class="btn btn-primary" id="btnList">수정 완료</button>
						</div>
					</div>
				</form>
			</div>
		</div>	
	</div>
</div>
<%@ include file = "../include/footer.jsp"%>