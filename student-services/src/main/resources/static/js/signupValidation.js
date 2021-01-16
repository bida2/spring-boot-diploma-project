// Get all input fields
var emailInput = document.getElementById("email");
var passInput = document.getElementById("password");
var compNameInput = document.getElementById("companyName");
var bulstatInput = document.getElementById("bulstat");
var phoneInput = document.getElementById("phoneNumber");
var locationInput = document.getElementById("location");
// Get form submit button
var subSignup = document.getElementById("subSignup");
// Get all message fields
var emailMess = document.getElementById("emailMessage");
var passMess = document.getElementById("passMessage");
var compNameMess = document.getElementById("compNameMessage");
var bulstatMess = document.getElementById("bulstatMess");
var phoneMess = document.getElementById("phoneMess");
var locationMess = document.getElementById("locationMess");
// Write the patterns for all the fields
var emPatt = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;
var passPatt = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/;
var compNamePatt = /^[a-zA-Z0-9\u0400-\u04FF]+(([',. -][a-zA-Z\u0400-\u04FF ])?[a-zA-Z0-9\u0400-\u04FF]*)*$/;
var bulstatPatt = /^([A-Z\u0400-\u04FF]{2,})?[0-9]{9,9}$/;
var phonePatt = /^(\+[0-9]{0,3})?([0-9]{10})$/;
var locPatt = /^[a-zA-Z\u0400-\u04FF]+\,+[a-zA-Z\u0400-\u04FF]+\,+[a-zA-Z\u0400-\u04FF]+\,+[a-zA-Z0-9\u0400-\u04FF ]{2,}$/;
// Add event listeners to fields - on character enter, validate field
emailInput.addEventListener('input',function(evt){
	if (emPatt.test(emailInput.value)) {
		emailMess.innerHTML = "Valid e-mail!";
		emailMess.classList.remove("text-danger");
		emailMess.classList.add("text-success");
	} else {
		emailMess.innerHTML = "Invalid e-mail!";
		emailMess.classList.add("text-danger");
		emailMess.classList.remove("text-success");
	}
});
passInput.addEventListener('input',function(evt){
	if (passPatt.test(passInput.value)) {
		passMess.innerHTML = "Password strength is sufficient!";
		passMess.classList.remove("text-danger");
		passMess.classList.add("text-success");
	} else {
		passMess.innerHTML = "Password must contain 8 characters and at least 1 digit, 1 lowercase character and 1 uppercase character!";
		passMess.classList.add("text-danger");
		passMess.classList.remove("text-success");
	}
});
compNameInput.addEventListener('input',function(evt){
	if (compNamePatt.test(compNameInput.value)) {
		compNameMess.innerHTML = "Valid company name!";
		compNameMess.classList.remove("text-danger");
		compNameMess.classList.add("text-success");
	} else {
		compNameMess.innerHTML = "Invalid company name! Company names may not include special characters!";
		compNameMess.classList.add("text-danger");
		compNameMess.classList.remove("text-success");
	}
});
bulstatInput.addEventListener('input',function(evt){
	if (bulstatPatt.test(bulstatInput.value)) {
		bulstatMess.innerHTML = "Valid Bulstat!";
		bulstatMess.classList.remove("text-danger");
		bulstatMess.classList.add("text-success");
	} else {
		bulstatMess.innerHTML = "Invalid Bulstat!";
		bulstatMess.classList.add("text-danger");
		bulstatMess.classList.remove("text-success");
	}
});
phoneInput.addEventListener('input',function(evt){
	if (phonePatt.test(phoneInput.value)) {
		phoneMess.innerHTML = "Valid phone number!";
		phoneMess.classList.remove("text-danger");
		phoneMess.classList.add("text-success");
	} else {
		phoneMess.innerHTML = "Invalid Phone Number! Must be written in the format +(country code)(phone number) !";
		phoneMess.classList.add("text-danger");
		phoneMess.classList.remove("text-success");
	}
});
locationInput.addEventListener('input',function(evt){
	if (locPatt.test(locationInput.value)) {
		locationMess.innerHTML = "Valid company location!";
		locationMess.classList.remove("text-danger");
		locationMess.classList.add("text-success");
	} else {
		locationMess.innerHTML = "Invalid Company Location! Must be written in the format Country/Area/City!";
		locationMess.classList.add("text-danger");
		locationMess.classList.remove("text-success");
	}
});
/* // On submit, if any field has failed its validation, do not allow the form to submit
subSignup.addEventListener('submit',function(evt){
	if (!emPatt.test(emailInput.value) || !passPatt.test(passInput.value) || !compNamePatt.test(compNameInput.value) || !bulstatPatt.test(bulstatInput.value) 
		|| !phonePatt.test(phoneInput.value) || !locPatt.test(locationInput.value)) {
		evt.preventDefault();
		document.getElementById("validation").innerHTML = "Incorrect data! Check fields and try again!";
	}
}); */


