<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
table, th, td {
    border: 1px solid black;
}
</style>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" ></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PKL Schedule Maker</title>
</head>
<body>
	<div  class="panel-body table-responsive" >
		<table class="table table-striped">
			<thead>
			<tr>			
			<th>Venue</th>
			<th>Date</th>
			<th>Day</th>
			<th colspan = "2">Match 1</th>
			<th colspan="2">Match 2</th>
			</tr>
			
			<tr>			
			<th></th>
			<th></th>
			<th></th>
			<th>Team A</th>
			<th>Team B</th>
			<th>Team A</th>
			<th>Team B</th>
			</tr>
			</thead>
			<tbody id="schedule">
			</tbody>
		</table>				
	</div>	
</body>
<script type="text/javascript">
function showSchedule()
{
	$.get('processSchedule', function(response) {
			var result = jQuery.parseJSON(response)
			var scheduleHTML="";
		for (var i = 0; i < result.length; i++) {
			var scheduleRow = result[i];
			scheduleHTML += "<tr><td>" + scheduleRow.venue + "</td><td>" + scheduleRow.date + "</td><td>" + scheduleRow.day + "</td>"+
			"<td>" + scheduleRow.m1ta + "</td><td>" + scheduleRow.m1tb + "</td><td>" + scheduleRow.m2ta + "</td><td>" + scheduleRow.m2tb + "</td><tr>";
		}
			$("#schedule").html(scheduleHTML);
	});
}
showSchedule();
</script>
</html>