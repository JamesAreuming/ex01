<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "../include/header.jsp"%>
<style>
	span#yellow{
		color: yellow;
		font-size: 20px;
	}
	#imgInfo{
		width: 100%;
		height: 200px;
		border: 2px solid black;
	}
	#imgInfo img{
		width: 150px;
		height: 150px;
		float: left;
		padding: 10px;
	}	
</style>
<script src="https://cdn.jsdelivr.net/npm/handlebars@latest/dist/handlebars.js"></script>
<script>
$(function () {	
	//수정
	$("#btnMod").click(function() {
		var no = $(this).attr("data-no");
		location.href="${pageContext.request.contextPath}/sboard/updatePage?bno="+no+"&page=${cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}";
	})
	
	//삭제
	$("#btnRemove").click(function() {
		var no = $(this).attr("data-no");
		console.log(no);
		var result = confirm("정말 삭제하시겠습니까?");
		if(result){
			location.href="${pageContext.request.contextPath}/sboard/deletePage?bno="+no+"&page=${cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}";
			//&searchType=${cri.searchType}&keyword=${cri.keyword}
		}
	})		
	//리스트
	$("#btnList").click(function() {
		location.href="${pageContext.request.contextPath}/sboard/listPage?page=${cri.page}&searchType=${cri.searchType}&keyword=${cri.keyword}";
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
					<div class="form-group" id="imgInfo">
						<c:forEach var="file" items="${board.files}">
							<%-- <p>${file}</p> --%>
							<img src = "displayFile?filename=${file}"> <!-- 컨트롤러에 아작스 추가 // 보드와 댓글에도 이미지 나오게 하려면 새로운 컨트롤러 추가해서 사용 -->
						</c:forEach>
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

<div class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">ADD NEW REPLY</h3>
				</div>
				<div class="box-body">
					<label>Writer</label>
						<input type="text" class="form-control" placeholder="User Id" id="newReplyWriter"><br>
						<label>Reply Text</label> <input type="text" class="form-control" placeholder="text" id="newReplyText">
				</div>
				<div class="box-footer">
					<button class="btn btn-primary" id="btnReplyAdd">REPLY</button>
				</div>
			</div>
			<ul class="timeline">
				<li class="time-label" id="repliesDiv"><span class="bg-green">Replies
						List&nbsp;&nbsp;&nbsp;<span id="yellow">[${board.replycnt}]</span></span></li>
			</ul>
			<div class="text-center">
				<ul id="pagination" class="pagination pagination-sm no-margin"></ul>
			</div>
		</div>
	</div>
</div>

<!-- 수정팝업 -->
<div id="modifyModal" class="modal modal-primary" role="dialog">
 	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">22</h4>
			</div>
			<div class="modal-body">
				<p>
					<input type="text" id="replytext" class="form-control">
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" id="btnModSave">Modify</button>
			</div>
		</div>
	</div> 
</div>
<script id="template" type="text/x-handlebars-template">
{{#each list}}
<li class="replyLi" data-rno="{{rno}}">
	<i class="fa fa-comments bg-blue"></i>
	<div class="timeline-item">
		<span class="time">
			<i class="fa fa-clock-o"></i><b>{{dateHelper regdate}}</b>
		</span>
		<h3 class="timeline-header"><strong>{{rno}}</strong> - {{replyer}}</h3>
		<div class="timeline-body">{{replytext}}</div>
		<div class="timeline-footer">
			<a class="btn btn-primary btn-xs btnMod" data-rno="{{rno}}" data-text="{{replytext}}" data-toggle="modal" data-target="#modifyModal">Modify</a>
			<a class="btn btn-danger btn-xs btnDel" a-rno="{{rno}}">Delete</a>
		</div>
	</div>
</li>
{{/each}}
</script>
<script>
$("#modifyModal").modal("hide");

var currentPage = 1;
//함수로 따로 빼기
function getPageList(page){
    var bno = ${board.bno};
    $.ajax({
       url: "${pageContext.request.contextPath}/replies/"+bno+"/"+page, // ex01/ewplies/521/1
       type: "get", //get방식으로 댓글 리스트 가져옴
       data: "json",
       success:function(res){
          console.log(res);
          
		Handlebars.registerHelper("dateHelper", function(value){
			var d = new Date(value);
			var year = d.getFullYear();
			var month = d.getMonth() +1;
			var day = d.getDate();
			var week = new Array("일", "월", "화", "수", "목", "금", "토");
			var dd = week[d.getDay()]; // 숫자를 반환하므로 배열~
			
			
			return year +"-"+ month+"-"+ day +" "+dd+"요일";
		});
        $(".replyLi").remove();
          var source = $("#template").html();
          var func = Handlebars.compile(source);
          $(".timeline").append(func(res)); //리스트 키가 있는 상태에서 넣었으므로 res만 넣으면 된다 
          
          $("span#yellow").html("["+res.pageMaker.totalCount+"]");
          
          $("#pagination").empty();     
          for(var i = res.pageMaker.startPage; i<= res.pageMaker.endPage; i++){
        	  var $li = $("<li>");
        	  if(i == currentPage){
        		  $li.addClass("active");
        	  }
        	  var $a = $("<a>").html(i);
        	  $li.append($a);
             $("#pagination").append($li);
          }
       }
    })   		
}
	$("#repliesDiv").click(function() {
		getPageList(1);
	})
	
	
	$(document).on("click", "#pagination li a", function(){
		//클릭했는 li태그 번호
		currentPage = $(this).text();
		getPageList(currentPage);
		})
	
	//삭제
	$(document).on("click",".btnDel", function() {
			//attr 심어주고
			var no = $(this).attr("a-rno");
			console.log(no);
			
			$.ajax({
				url:"${pageContext.request.contextPath}/replies/"+no,
				type:"delete",
				dataType:"text",
           success:function(res){
        	   console.log(res);
               if(res == "SUCCESS"){
               	alert("게시글을 삭제하였습니다.");
               	//리스트 갱신
               	getPageList(currentPage);
               }
           }
		})
	})
	
	//등록
	$("#btnReplyAdd").click(function() {
		//댓글 등록
		//var bno = $("#newReplyWriter").val();
		var bno = ${board.bno};
		var replyer = $("#newReplyWriter").val();
		var text = $("#newReplyText").val();
		
		//서버 주소 : /replies/
		
		//@RequestBody 서버에서 사용시
		//1. headers - "Content-Type" : "application/json"
		//2. 보내는 data는 json string으로 변형해서 보내야 됨
		// -- "{bno:bno}"
		
		var json = JSON.stringify({"bno":bno, "replyer":replyer, "replytext":text}); //stringify를 이용하여 json
		
		$.ajax({
			url:"${pageContext.request.contextPath}/replies/",
			method:"post",
			headers:{"Content-Type":"application/json"},
			data:json, //보낼 데이터, 서버에 보낼 vo데이터 값과 이름이 같아야 한다
			dataType : "text", //단순한 글자 1개 / <List<ReplyVO>>의 경우 json
            success:function(res){
                console.log(res);
                if(res == "SUCCESS"){
                	alert("댓글이 등록되었습니다.");
                	//리스트 갱신
                	getPageList(1);
                }
            }
			
		})		
	})
	
	//댓글수정
 	$(document).on("click", ".btnMod", function() {
			var rno = $(this).attr("data-rno"); //rno 뽑아오기
			var text = $(this).attr("data-text"); //text 뽑아오기
			 
			
			$(".modal-title").html(rno);
			$("#replytext").attr("value", text);
			$("#modifyModal").modal("show");
		})
		
		$("#btnModSave").click(function(){
			var rno = $(this).parent().find(".modal-title").text();
			var text = $(this).parent().find("#replytext").val();
		
 			$.ajax({
				url:"${pageContext.request.contextPath}/replies/"+rno,
				type:"put",
				headers:{"Content-Type":"application/json"},
				data:JSON.stringify({"replytext":text}),
				dataType:"text",
          success:function(res){
        	  console.log(res);
              if(res == "SUCCESS"){
              	alert("댓글이 수정되었습니다.");
    			$("#modifyModal").hide();
              	//리스트 갱신
              	getPageList(currentPage);
              }        	  
          }
			
		}) 
		
		})
</script>
<%@ include file = "../include/footer.jsp"%>