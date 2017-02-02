$(document).ready(function() {
	$('.lightbox_trigger').click(function() {
		document.getElementById('gruppenname').value = "";
		$('#lightbox').show();
	});

	$('#close-lightbox').live('click', function() {
		$('#lightbox').hide();
	});

	$('.group-click').click(function() {
		var groupid = $(this).attr("id");
		groupid = groupid.substring(6);
		post("/de.bs14/taskboard", groupid);
	});

});

function post(path, parameters) {
	var form = $('<form></form>');

	form.attr("method", "post");
	form.attr("action", path);

	var field = $('<input></input>');
	var key = "groupid";
	field.attr("type", "hidden");
	field.attr("name", key);
	field.attr("value", parameters);

	form.append(field);

	// The form needs to be a part of the document in
	// order for us to be able to submit it.
	$(document.body).append(form);
	form.submit();
}