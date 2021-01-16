window.onload = () => {
	function truncateString(idStr, num) {
		const strElement = document.getElementById(idStr);
		const strValue = strElement.innerHTML;
		if (strValue.length > num) {
			const resStr = strValue.split(" ").splice(0,num).join(" ") + " &rarr;";
			strElement.innerHTML = resStr;
		}
	}
		
	// Truncate article text string after the first 50 words
	truncateString("articleText",50);
}