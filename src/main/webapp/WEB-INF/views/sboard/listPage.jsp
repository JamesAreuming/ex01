<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp"%>
<style>
	span#red{
		color: red;
	}
</style>
<!-- 기본골격 -->
<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">Search Board LIST</h3>
				</div>
				<div class="box-body">
					<select name="searchType" id="searchType">
						<option value="n" ${cri.searchType==null? 'selected':''}>-------------------------------</option>
						<option value="t" ${cri.searchType=='t'? 'selected':''}>Title</option>
						<option value="c" ${cri.searchType=='c'? 'selected':''}>Content</option>
						<option value="w" ${cri.searchType=='w'? 'selected':''}>Writer</option>
						<option value="tc" ${cri.searchType=='tc'? 'selected':''}>Title OR Content</option>
						<option value="cw" ${cri.searchType=='cw'? 'selected':''}>Content OR Writer</option>
						<option value="tcw" ${cri.searchType=='tcw'? 'selected':''}>Title OR Content OR Writer</option>
					</select>
					<input type="text" name="keyword" id="keywordInput" value="${cri.keyword }">
					<button id="btnSearch">Search</button>
					<button id="btnRegister">New Board</button>
				</div>
				<div class="box-body">
					<table class="table table-boardered">
						<tr>
							<th styel="width:10px">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th styel="width:40px">VIEWCNT</th>
						</tr>
						<c:forEach var="board" items="${list}">
							<tr>
								<td>${board.bno}</td>
								<td><a href="${pageContext.request.contextPath}/sboard/readPage?bno=${board.bno}&page=${cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}">${board.title}&nbsp;&nbsp;&nbsp;<span id="red"><b>[${board.replycnt}]</b></span></a></td>
								<td>${board.writer}</td>
								<td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td><span class="badge bg-red">${board.viewcnt}</span></td>								
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="box-footer">
<%-- 					<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
					${idx },
					</c:forEach> --%>
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev == true}">
								<li><a href="listPage?page=${pageMaker.startPage-1 }&searchType=${cri.searchType}&keyword=${cri.keyword}">&laquo;</a></li>
							</c:if>						
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<li class="${pageMaker.cri.page == idx?'active':''}"><a href="listPage?page=${idx }&searchType=${cri.searchType}&keyword=${cri.keyword}">${idx }</a></li>
							</c:forEach>
							<c:if test="${pageMaker.next == true}">
								<li><a href="listPage?page=${pageMaker.endPage+1 }&searchType=${cri.searchType}&keyword=${cri.keyword}">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
				</div>
			</div>
		</div>	
	</div>
</div>

<script>
	$("#btnSearch").click(function() {
		var searchType = $("#searchType").val();
		var keyword = $("#keywordInput").val();
		location.href = "listPage?&searchType="+searchType+"&keyword="+keyword;
	})
	
	$("#btnRegister").click(function() {
		location.href="register";
	})
</script>
<%@ include file = "../include/footer.jsp"%>