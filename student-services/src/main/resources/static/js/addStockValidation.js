// Get all input fields
var weightItemInput = document.getElementById("weightOne");
var quantityInput = document.getElementById("quantity");
var priceInput = document.getElementById("pricePerItem");
// Get all message fields
var weightItemMessage = document.getElementById("weightOneMessage");
var quantityMessage = document.getElementById("quantMessage");
var priceMessage = document.getElementById("priceMessage");
// Write the patterns for the fields
var weightItemPatt = /\d+(\.\d{1,2})/;
var quantPatt = /^[0-9]*$/;
var pricePatt = /\d+(\.\d{1,2})?/;
// Add event listeners to fields - on input, validate field
weightItemInput.addEventListener('input',function(evt) {
	if (weightItemPatt.test(weightItemInput.value)) {
		weightItemMessage.innerHTML = "Valid weight format!";
		weightItemMessage.classList.remove("text-danger");
		weightItemMessage.classList.add("text-success");
	} else {
		weightItemMessage.innerHTML = "Invalid weight format!";
		weightItemMessage.classList.add("text-danger");
		weightItemMessage.classList.remove("text-success");
	}
});
quantityInput.addEventListener('input',function(evt) {
	if (quantPatt.test(quantityInput.value)) {
		quantityMessage.innerHTML = "Valid stock quantity format!";
		quantityMessage.classList.remove("text-danger");
		quantityMessage.classList.add("text-success");
	} else {
		quantityMessage.innerHTML = "Invalid stock quantity format!";
		quantityMessage.classList.add("text-danger");
		quantityMessage.classList.remove("text-success");
	}
});
priceInput.addEventListener('input',function(evt) {
	if (pricePatt.test(priceInput.value)) {
		priceMessage.innerHTML = "Valid price per item format!";
		priceMessage.classList.remove("text-danger");
		priceMessage.classList.add("text-success");
	} else {
		priceMessage.innerHTML = "Invalid price per item format!";
		priceMessage.classList.add("text-danger");
		priceMessage.classList.remove("text-success");
	}
});