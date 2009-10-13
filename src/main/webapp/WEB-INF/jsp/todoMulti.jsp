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
			var text = '<ul id="todoItems' + currentTodoListId + '">';			
			for(i = 0; i < listitems.length; i++) {
				text += '<li id="';
				text += listitems[i].id;
				text += '" class="todoItem">';
				text += listitems[i].title;
				text += '</li>';
			}
			text += '</ul>';
//			text += '<form name="form" action="javascript:saveTodoItem();" method="POST">';
//			text += '<table>';
//			text += '<input type="text" id="todo.title" value="" size="60"/>';
//			text += '<tr><td></td><td><input type="submit" name="submit.submit" value="Submit"/></td></tr>';
//			text += '</table></form>';
			
			document.getElementById('todoItemsSpan' + currentTodoListId).innerHTML = text;
			for(i = 0; i < listitems.length; i++) {
				new Draggable(listitems[i].id,{revert:true});
			}
//			document.getElementById('todo.title').focus();
    	}
    	var moveCallback = function(data) {
    		window.location.reload();
//    		loadList(currentTodoListId, currentTodoListTitle);
    	}	
		function loadList(todoListId, todoListTitle) {
		// Todo remove Draggables?
			DWRUtil.removeAllOptions('todoItems');
			currentTodoListId = todoListId;
			currentTodoListTitle = todoListTitle;
			TodoAjax.getList(todoListId, loadListCallback);
		}
		
		var saveTodoItemCallback = function() {
    		window.location.reload();
//			loadList(currentTodoListId, currentTodoListTitle);
		}
		
		function saveTodoItem() {
			TodoAjax.save(document.getElementById("todo.title").value, currentTodoListId, saveTodoItemCallback);
		}

		function saveTodoItemWithId(listid) {
			TodoAjax.save(document.getElementById("todo.title" + listid).value, listid, saveTodoItemCallback);
		}
			
		function openAddTextBox(itemid) {
			var text = '<form name="form" action="javascript:saveTodoItemWithId(\'' + itemid + '\');" method="POST">';
			text += '<table>';
			text += '<input type="text" id="todo.title' + itemid + '" value="" size="60"/>';
			text += '<tr><td></td><td><input type="submit" name="submit.submit" value="Submit"/></td></tr>';
			text += '</table></form>';
			document.getElementById('addItemSpan' + itemid).innerHTML = text;
		}
    // ]]>
	</script>
</app:top>

<c:forEach items="${list}" var="item" varStatus="status">
	<div id="items${item.id}" class="listsMulti">
		<h3><a href="#" onclick="loadList('${item.id}','${item.title}')">${item.title}</a></h3>
		<span id="todoItemsSpan${item.id}">
		<ul id="todoItems{item.id}">
		<c:forEach items="${item.todoItems}" var="todoItem">
			<li id="${todoItem.id}" class="todoItem${item.id}">${todoItem.title}</li>
		</c:forEach>
		</ul>
		</span>
		<a href="#" onclick="openAddTextBox('${item.id}')">Add</a>
		<span id="addItemSpan${item.id}"></span>		
	</div>
</c:forEach>	

<div id="items">
<span id="todoItemsSpan"></span>
</div>
<%--
<script type="text/javascript" language="javascript">
  Sortable.create('todoLists',{ghosting:true,constraint:false})
  Sortable.create('todoItems',{ghosting:true,constraint:false})
</script>
--%>
<script type="text/javascript" language="javascript">
<c:forEach items="${list}" var="item">	
Droppables.add('items${item.id}', {
   hoverclass: 'listDroppableHoverActive',
	<c:set var="valuePrinted" value='false'/>
   accept: new Array(<c:forEach items="${list}" var="item2" varStatus="status">
   						<c:if test="${item.id != item2.id}">
   							<c:if test="${valuePrinted}">,</c:if>
   							'todoItem${item2.id}'
   							<c:set var="valuePrinted" value='true'/>
   						</c:if>   					 	
   					 </c:forEach>),
   onDrop: function(element) 
     {	//var x = window.confirm('Move this todo item?' + element.id + '/' + '${item.id}'); 
     	var x = true;
     	if (x) {
     		TodoAjax.move(element.id, '${item.id}',moveCallback);
     		element.innerHTML = '';
     		//element.endDrag();
     	}
     }});
	<c:forEach items="${item.todoItems}" var="todoItem">
	new Draggable('${todoItem.id}',{revert:true});
	
   new Ajax.InPlaceEditor(
      '${todoItem.id}',
      'javascript: void(0)',
      {
         formId: 'whatever',
         okText: 'Save',
         cancelText: 'Cancel',
         highlightcolor: "#FFFFFF",
         rows: 3,
         cols: 50,
         callback: function(form, text) {
         	TodoAjax.update(text, '${todoItem.id}', saveTodoItemCallback);
         }
      }
   );	
	</c:forEach>     
</c:forEach>
</script>