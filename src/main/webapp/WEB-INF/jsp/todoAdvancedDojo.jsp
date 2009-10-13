<%@ include file="include/taglib.jsp" %>
<app:top>
	<link rel="stylesheet" type="text/css" href="css/todo.css">
	<script type='text/javascript' src='dwr/interface/TodoAjax.js'></script>
	<script type='text/javascript' src='dwr/engine.js'></script>
	<script type='text/javascript' src='dwr/util.js'></script>
	<script type="text/javascript">
    var djConfig = {
	isDebug:true, parseOnLoad:true
    };
	</script>	
	<script type="text/javascript" src="http://o.aolcdn.com/dojo/1.1.1/dojo/dojo.xd.js"></script> 
	<script type="text/javascript" language="javascript">
    // <![CDATA[
    	dojo.require("dojo.dnd.Container");
  		dojo.require("dojo.dnd.Manager");
  		dojo.require("dojo.dnd.Source");
    
    	function initDND(){
    		<c:forEach items="${list}" var="item">
    			dndTarget${item.id} = new dojo.dnd.Target("${item.id}");
    		</c:forEach>
    	
			dojo.subscribe("/dnd/drop", function(source, nodes, copy, target){
			<c:forEach items="${list}" var="item">
			if (target == dndTarget${item.id}) {
				console.debug("Moving item", nodes[0].id, "to list ${item.id}");			
				TodoAjax.move(element.id, '${item.id}',moveCallback)				
			}				
			</c:forEach>
			});
			
    	};
    	
    	dojo.addOnLoad(initDND);
    
    	var currentTodoListId = null;
    	var currentTodoListTitle = null;
    	var loadListCallback = function(listitems) {
			var text = '<h3>' + currentTodoListTitle + '</h3><ul id="todoItems" class="todoItems">';			
			for(i = 0; i < listitems.length; i++) {
				text += '<li id="';
				text += listitems[i].id;
				text += '" class="dojoDndItem">';
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
			
			var containerTodoItems = new dojo.dnd.Source("todoItems");    		
			for(i = 0; i < listitems.length; i++) {
//				new Draggable(listitems[i].id,{revert:true});
				
//				Droppables.add(listitems[i].id, {
//				   accept: 'todoItem',  
//				   onDrop: function(element) 
//				     {	//var x = window.confirm('Reposition this todo item?' + element.id + '/' + '${item.id}'); 
//				     	//if (x) {
//				     		TodoAjax.repositionBefore(element.id, listitems[i].id, moveCallback)
//				     	//}
//				     }});
				
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
    // ]]>
	</script>
</app:top>
<div id="lists">
<ul id="todoLists">
<!-- 	<div id="list-selected"><li>Short term</li></div>-->
	<c:forEach items="${list}" var="item">
	<li id="${item.id}" class="todoList">
		<div jsId="t${item.id} dojoType="dojo.dnd.Target" class="dndContainer">	
		<a href="#" onclick="loadList('${item.id}','${item.title}')">${item.title}</a>
		</div>
	</li>
	</c:forEach>	
</ul>
</div>
<div id="items">
<span id="todoItemsSpan"/>
</div>
<%--
<script type="text/javascript" language="javascript">
  Sortable.create('todoLists',{ghosting:true,constraint:false})
  Sortable.create('todoItems',{ghosting:true,constraint:false})
</script>
--%>
<script type="text/javascript" language="javascript">

</script>