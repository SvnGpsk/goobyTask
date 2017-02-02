<%@ page language="java" contentType="text/html; charset=windows-1252"
	pageEncoding="windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>GoobyTask Gruppen</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="https://code.jquery.com/jquery-1.6.2.min.js"></script>
    <script src="js/groups.js"></script>
</head>

<body>

    <div class="group-page">
        <h1 id="groupsheader">GoobyTask!</h1>
        <h2> Wähle eine Gruppe oder erstelle deine eigene.</h2>
        <div class="group-list">
            <ul class="groups">
                <li class="group" id="add-group-li">
                    <p class="group-plus lightbox_trigger" id="add-group">+</p>
                </li>
                
                <c:forEach items="${groupList}" var="group">
	                <li class="group group-click" id="group-${group.groupid}">
	                    <a href="#" class="group-link">
	                        <p class="group-name">${group.groupname}</p>
	                    </a>
	                </li>
			</c:forEach>
            </ul>
        </div>

    </div>

    <div id="lightbox">
        <div class="form" id="box">
        <div id="close-lightbox">X</div>
            <form class="create-group-form" action="../de.bs14/creategroup" method="post">
                <input type="text" placeholder="Gruppenname" id="gruppenname" name="gruppenname" />
                <button id="create-group">Gruppe erstellen</button>
            </form>
        </div>
    </div>
</body>
</html>
