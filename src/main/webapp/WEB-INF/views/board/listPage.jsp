<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp"%>

<!-- 기본골격 -->
<div class="content">
	<div class="row">
		<div class="col-sm-12">
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">LIST</h3>
				</div>
				<div class="box-body text-right">
					<a href="${pageContext.request.contextPath}/board/register">register</a>	
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
								<td><a href="${pageContext.request.contextPath}/board/read?bno=${board.bno}">${board.title}</a></td>
								<td>${board.writer}</td>
								<td><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td><span class="badge bg-red">${board.viewcnt}</span></td>								
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="box-footer">
					<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
					${idx },
					</c:forEach>
				</div>
			</div>
		</div>	
	</div>
</div>
<%@ include file = "../include/footer.jsp"%>