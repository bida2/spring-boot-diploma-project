window.onload = () => {
//	let editButton = document.getElementById("edit");
	let delButton = document.getElementById("delete");
	
	delButton.addEventListener("click",function(){
		if(confirm("Are you sure you want to delete this stock?")) {
			window.location.href = "/deleteStock";
		} else {
			event.preventDefault();
		}
	});
}