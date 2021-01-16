window.onload = () => {
	// Sets the value of the field with name pricePerItem in addStock.jsp to be empty
	// bypassing the default settings of the type="number" input field
	function setNumberFieldsEmpty() {
		let ppiField = document.getElementById("pricePerItem");
		let woneField = document.getElementById("weightOne");
		let quantField = document.getElementById("quantity");
		quantField.value="";
		woneField.value = "";
		ppiField.value= "";
		ppiField.removeAttribute("value");
		woneField.removeAttribute("value");
		quantField.removeAttribute("value");
	}
	
	// Set the number fields to be empty
	setNumberFieldsEmpty();
}