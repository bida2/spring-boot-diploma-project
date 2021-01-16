// Get all input fields
var groupNameInput = document.getElementById("stockGroupName");
// Get message paragraphs
var groupNameMessage = document.getElementById("stockGroupMessage");
// Set RegEx patterns
var groupNamePatt = /^[a-zA-Z\\& ]+$/;
// Add event listeners - on input, validate field
groupNameInput.addEventListener('input',function(evt){
	if (groupNamePatt.test(groupNameInput.value)) {
		groupNameMessage.innerHTML = "Valid stock group name!";
		groupNameMessage.classList.remove("text-danger");
		groupNameMessage.classList.add("text-success");
	} else {
		groupNameMessage.innerHTML = "Invalid stock group name! Only letters are allowed!";
		groupNameMessage.classList.add("text-danger");
		groupNameMessage.classList.remove("text-success");
	}
});