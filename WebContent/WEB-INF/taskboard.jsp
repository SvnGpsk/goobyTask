<%@ page language="java" contentType="text/html; charset=windows-1252"
	pageEncoding="windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>GoobyTask Board</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="task-page">
		<a href="/de.bs14/groups" id="backlink">&#8592;</a>
		<h1>GoobyTask!</h1>
		<h2 id="gruppe-ueberschrift">${groupobject.groupname}</h2>
		<div class="task-list">
			<div class="col-md-12">
				<div class="lightbox_trigger" id="add-task">Neue Aufgabe</div>
				<div class="lightbox_trigger" id="add-user">Neuer Mitarbeiter
				</div>
			</div>
			<div class="col-xs-12 col-md-3 col-lg-3" id="to-do-div">
				<h1 class="taskboardueberschrift">To Do</h1>
				<ul class="to-do">
					<c:forEach items="${todoTaskList}" var="todotask">
						<li id="task-${todotask.taskname}" class="task">
							<div class="delete-task-x" id="deletetask-${todotask.taskname}">x</div>
							<p class="task-name">${todotask.taskname}</p>
							<p class="worker-name">${todotask.taskworker}</p>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="col-xs-12 col-md-3 col-lg-3" id="in-progress-div">
				<h1 class="taskboardueberschrift">In Arbeit</h1>
				<ul class="in-progress">
					<c:forEach items="${inprogressTaskList}" var="inprogresstask">
						<li id="task-${inprogresstask.taskname}" class="task">
							<div class="delete-task-x" id="deletetask-${inprogresstask.taskname}">x</div>
							<p class="task-name">${inprogresstask.taskname}</p>
							<p class="worker-name">${inprogresstask.taskworker}</p>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="col-xs-12 col-md-3 col-lg-3" id="check-div">
				<h1 class="taskboardueberschrift">Kontrolle</h1>
				<ul class="check">
					<c:forEach items="${checkTaskList}" var="checktask">
						<li id="task-${checktask.taskname}" class="task">
							<div class="delete-task-x" id="deletetask-${checktask.taskname}">x</div>
							<p class="task-name">${checktask.taskname}</p>
							<p class="worker-name">${checktask.taskworker}</p>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="col-xs-12 col-md-3 col-lg-3" id="done-div">
				<h1 class="taskboardueberschrift">Erledigt</h1>
				<ul class="done">
					<c:forEach items="${doneTaskList}" var="donetask">
						<li id="task-${donetask.taskname}" class="task">
							<div class="delete-task-x" id="deletetask-${donetask.taskname}">x</div>
							<p class="task-name">${donetask.taskname}</p>
							<p class="worker-name">${donetask.taskworker}</p>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>

	</div>

	<div id="lightbox">
		<div class="form" id="box-task">
			<div id="close-lightbox">X</div>
			<form class="create-task-form" action="../de.bs14/createtask"
				method="post">
				<input type="text" placeholder="Aufgabe" id="aufgabe"
					name="taskname" /> <input type="text"
					placeholder="Wer bearbeitet diese Aufgabe?" id="worker"
					name="taskworker" />
				<p>
					<input class="radio" type="radio" name="taskstate" value="todo"
						checked />To Do
				</p>
				<p>
					<input class="radio" type="radio" name="taskstate"
						value="inprogress" />In Arbeit
				</p>
				<p>
					<input class="radio" type="radio" name="taskstate" value="check" />Kontrolle
				</p>
				<p>
					<input class="radio" type="radio" name="taskstate" value="done" />Fertig
				</p>
				<p>
					<button id="create-task">Aufgabe erstellen</button>
			</form>

		</div>
	</div>

	<div id="lightbox-task">
		<div class="form" id="box-task">
			<div id="close-lightbox-task">X</div>
			<h3>Aufgabenstatus ändern</h3>
			<form class="update-task-form" action="../de.bs14/modifytask"
				method="post">
				<div class="btn-group btn-group-justified" role="group"
					aria-label="...">
					<div class="btn-group" role="group">
						<button type="button" class="btn btn-default" id="btn-todo"
							name="todoModify">To Do</button>
					</div>
					<div class="btn-group" role="group">
						<button type="button" class="btn btn-default" id="btn-inprogress"
							name="inprogressModify">In Arbeit</button>
					</div>
					<div class="btn-group" role="group">
						<button type="button" class="btn btn-default" id="btn-check"
							name="checkModify">Kontrolle</button>
					</div>
					<div class="btn-group" role="group">
						<button type="button" class="btn btn-default" id="btn-done"
							name="doneModify">Fertig</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<div id="lightbox-user">
		<div class="form" id="box-task">
			<div id="close-lightbox-user">X</div>
			<form class="add-user-form" action="../de.bs14/adduser" method="post">
				<input type="text" placeholder="Username des Mitarbeiters"
					id="user-add" name="userToAdd" />
				<button id="create-task">User hinzufügen</button>
			</form>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-1.6.2.min.js"></script>
	<script src="js/task.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>
