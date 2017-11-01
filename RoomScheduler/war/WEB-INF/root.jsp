<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="en">
<head>
<meta charset="UTF-8">
<title>Room Scheduler</title>

<style>
		table,td,th{
		border-collapse: separate;
		border: 1px solid black;
		text-align:center;
		}
		#book{
		position:absolute;
        top:48%;
        left:5%; 
		}
		#bookdetails{
		position:absolute;
        top:48%;
        left:60%; 
		}
		table {
		height: auto;
    	width: 34%;
    	vertical-align: middle;
		position:absolute;
        top:55%;
        left:5%; 
		}
		#table1 {
		height: auto;
    	width: 30%;
    	vertical-align: middle;
		position:absolute;
        top:55%;
        left:60%;
        } 
		#f1{
		position:absolute;
        top:10%;
        left:37%; 
		}
		#f2{
		position:absolute;
        top:33%;
        left:60%; 
		}
		form{
		position:absolute;
        top:33%;
        left:6%; 
		}
		
		#r {
		width: 10%;
		}
		#n {
		width: 25%;
		}
		#t {
		width: 30%;
		}
</style>
</head>
<body>
	<h3 style="text-align: center;">Room Scheduler</h3>
	<c:choose>
		<c:when test="${user != null}">
			<p>
				Welcome ${user.email} <br /> <a href="${logout_url}">SignOut</a> <br />
			</p>
			<!-- Form for Schedular -->
			<form id="f1" action="/" method="post" onsubmit="putDate(this);">
				<p>Enter Room Number(100 to 150): <input type="number" name="room_num" min="100" max="150" required /></p>
				<p>Name: <input type="text" name="name" required /></p>
				<input type="radio" value="booked" name="allocate">Allocate Room<br/><br/>
				<input name="date" type="hidden" /> 
				<input type="submit" value="Book"/>
			</form>
			
			<form action="/deleteRoom" method="post">
				<p>Enter Room Number(100 to 150):</p>
				<input type="number" name="deleteRoom" min="100" max="150" /><br />
				<input type="submit" value="Delete" />
			</form>
			
			<form id="f2" action="/" method="get">
				<p>Enter Room Number(100 to 150):</p>
				<input type="number" name="getRoom" min="100" max="150" /><br />
				<input type="submit" value="Get Deatils" />
			</form>
		</c:when>
		<c:otherwise>
			<p>
				Welcome!<a href="${login_url}">Sign in</a>
			</p>

		</c:otherwise>
	</c:choose>

	<script>
		var putDate = function(form) {
			form.date.value = new Date().toString();
		};
	</script>
</body>
</html>