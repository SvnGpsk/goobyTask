$(document).ready(function () {
	var taskid;
    /*Lightbox neue Task*/
    $('#add-task').click(function () {
        document.getElementById('aufgabe').value = "";
        document.getElementById('worker').value = "";
        $('#lightbox').show();
    });

    $('#close-lightbox').live('click', function () {
        $('#lightbox').hide();
    });

    /*Lightbox neuer Mitarbeiter*/
    $('#add-user').click(function () {
        document.getElementById('user-add').value = "";
        $('#lightbox-user').show();
    });

    $('#close-lightbox-user').live('click', function () {
        $('#lightbox-user').hide();
    });

    $('#create-task').click(function () {
        if (document.getElementById('aufgabe').value != "" && document.getElementById('worker').value != "") {
            $('#lightbox').hide();
            $("." + zustand).append("<li class='task'><p class='task-name'>Taskname</p><p class='worker-name'>Workername</p></li>");
        }
    });
    /*Delete Task*/
    $('.delete-task-x').click(function () {
        taskid = $(this).attr("id");
        taskid = taskid.substring(11);
        postDelete(taskid);
    });
    
    function postDelete(parameters) {
    	var form = $('<form></form>');

    	form.attr("method", "post");
    	form.attr("action", "/de.bs14/deletetask");

    	var field = $('<input></input>');
    	var key = "taskidDelete";
    	field.attr("type", "hidden");
    	field.attr("name", key);
    	field.attr("value", parameters);
    	
    	form.append(field);
    	// The form needs to be a part of the document in
    	// order for us to be able to submit it.
    	$(document.body).append(form);
    	form.submit();
    }

    

    /*Lightbox Update Task*/
    $('.task-name').click(function () {
        $('#lightbox-task').show();
    });

    $('#close-lightbox-task').live('click', function () {
        $('#lightbox-task').hide();
    });

    $('.task').click(function (e) {
        e.preventDefault();
        taskid = $(this).attr("id");
        taskid = taskid.substring(5);
        $('#lightbox').hide();
    });
    
    $('#btn-todo').click(function (e) {
        e.preventDefault();
        var newState = $(this).attr("name");
        newState = newState.substring(0,(newState.length-6));
        postModify(taskid,newState);
        $('#lightbox').hide();
    });
    
    $('#btn-inprogress').click(function (e) {
        e.preventDefault();
        var newState = $(this).attr("name");
        newState = newState.substring(0,(newState.length-6));
        alert
        postModify(taskid,newState);
        $('#lightbox').hide();
    });
    $('#btn-check').click(function (e) {
        e.preventDefault();
        var newState = $(this).attr("name");
        newState = newState.substring(0,(newState.length-6));
        postModify(taskid, newState);
        $('#lightbox').hide();
    });
    $('#btn-done').click(function (e) {
        e.preventDefault();
        var newState = $(this).attr("name");
        newState = newState.substring(0,(newState.length-6));
        postModify(taskid, newState);
        $('#lightbox').hide();
    });
    
});

function postModify(parameters1, parameters2) {
	var form = $('<form></form>');

	form.attr("method", "post");
	form.attr("action", "/de.bs14/modifytask");

	var field1 = $('<input></input>');
	var key1 = "taskidModified";
	field1.attr("type", "hidden");
	field1.attr("name", key1);
	field1.attr("value", parameters1);
	
	var field2 = $('<input></input>');
	var key2 = "taskstateModified";
	field2.attr("type", "hidden");
	field2.attr("name", key2);
	field2.attr("value", parameters2);
	
	form.append(field1);
	form.append(field2);
	// The form needs to be a part of the document in
	// order for us to be able to submit it.
	$(document.body).append(form);
	form.submit();
}
