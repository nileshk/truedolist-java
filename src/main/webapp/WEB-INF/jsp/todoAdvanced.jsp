<%@ include file="include/taglib.jsp" %>
<app:top>
	<script src="js/prototype.js" type="text/javascript"></script>
	<script src="js/scriptaculous.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="css/todo.css">
	<script type='text/javascript' src='dwr/interface/TodoAjax.js'></script>
	<script type='text/javascript' src='dwr/engine.js'></script>
	<script type='text/javascript' src='dwr/util.js'></script>
	<script type="text/javascript" language="javascript">
    // <![CDATA[
    	var currentTodoListId = null;
    	var currentTodoListTitle = null;
    	var loadListCallback = function(listitems) {
			var text = '<h3>' + currentTodoListTitle + '</h3><ul id="todoItems">';			
			for(i = 0; i < listitems.length; i++) {
				text += '<li id="';
				text += listitems[i].id;
				text += '" class="todoItem">';
				text += listitems[i].title;
				text += '</li>';
			}
			text += '</ul>';
			text += '<form name="form" action="javascript:saveTodoItem();" method="POST">';
			text += '<table>';
			text += '<input type="text" id="todo.title" value="" size="60"/>';
			text += '<tr><td></td><td><input type="submit" name="submit.submit" value="Submit"/></td></tr>';
			text += '</table></form>';
			
			document.getElementById('todoItemsSpan').innerHTML = text;    		
			for(i = 0; i < listitems.length; i++) {
				new Draggable(listitems[i].id,{revert:true});
				
				Droppables.add(listitems[i].id, {
				   accept: 'todoItem',  
				   onDrop: function(element, dropon, event) 
				     {	//var x = window.confirm('Reposition this todo item?' + element.id + '/' + '${item.id}'); 
				     	//if (x) {
				     		debug('dropped ' + element.innerHTML + '(' + element.id + ') on ' + dropon.innerHTML + '(' + dropon.id + ')\n');
				     		TodoAjax.repositionBefore(element.id, dropon.id, moveCallback)
				     	//}
				     }});
				
			}
			document.getElementById('todo.title').focus();
    	}
    	var moveCallback = function(data) {
    		loadList(currentTodoListId, currentTodoListTitle);
    	}	
		function loadList(todoListId, todoListTitle) {
		// Todo remove Draggables?
			DWRUtil.removeAllOptions('todoItems');
			currentTodoListId = todoListId;
			currentTodoListTitle = todoListTitle;
			TodoAjax.getList(todoListId, loadListCallback);
		}
		
		var saveTodoItemCallback = function() {
			loadList(currentTodoListId, currentTodoListTitle);
		}
		
		function saveTodoItem() {
			TodoAjax.save(document.getElementById("todo.title").value, currentTodoListId, saveTodoItemCallback);
		}
		
		function debug(text)
		{
		   document.getElementById('debug').innerHTML
		       = "<pre>" + text + "</pre>";
		
		} 
    // ]]>
	</script>
</app:top>
<div id="lists">
<ul id="todoLists">
<!-- 	<div id="list-selected"><li>Short term</li></div>-->
	<c:forEach items="${list}" var="item">
	<li id="${item.id}" class="todoList">
		<a href="#" onclick="loadList('${item.id}','${item.title}')">${item.title}</a>
	</li>
	</c:forEach>	
</ul>
</div>
<div id="items">
<span id="todoItemsSpan"/>
</div>
<div id="debug"></div> 
<%--
<script type="text/javascript" language="javascript">
  Sortable.create('todoLists',{ghosting:true,constraint:false})
  Sortable.create('todoItems',{ghosting:true,constraint:false})
</script>
--%>
<script type="text/javascript" language="javascript">
<c:forEach items="${list}" var="item">	
Droppables.add('${item.id}', {
   accept: 'todoItem',  
   onDrop: function(element, dropon, event) 
     {	//var x = window.confirm('Move this todo item?' + element.id + '/' + '${item.id}'); 
     	//if (x) {
     		debug('dropped ' + element.innerHTML + '(' + element.id + ') on ' + dropon.innerHTML + '(' + dropon.id + ')\n');
     		TodoAjax.move(element.id, '${item.id}',moveCallback)
     	//}
     }});
</c:forEach>
</script>