window.onload = () => {
	// Obtain contents of the article text paragraph
	let artText = document.getElementById("articleText");
	// Split contents of article text paragraph on every '.'
	let sentenceArray = artText.innerHTML.split(".");
	let trimArray = sentenceArray.filter(el => el.trim());
	let trimLength = trimArray.length;
	// Insert the next 3 elements of sentenceArray in a new paragraph
	// and append the paragraph to the <div class="post-preview"> element
	console.log(trimArray);
	// Utility variables that help us keep track of number of sentences
	// and store sentences temporarily
	let numSentences = 0;
	let sentBuffer = "";
	for (let sentence of trimArray) {
		if (numSentences == 3) {
			sentBuffer += sentence + ".";
			// Placing sentences before the element allows us to remove the original at the end
			// and to place the separated senteces into the paragraphs into the appropriate order
			artText.insertAdjacentHTML('beforebegin', "<p>" + sentBuffer + "</p>");
			numSentences = 0;
			sentBuffer="";
		} else if (trimArray.indexOf(sentence) == trimLength - 1) {
			// Check if the current item is the last item of the array
			sentBuffer += sentence + ".";
			artText.insertAdjacentHTML('beforebegin', "<p>" + sentBuffer + "</p>");
			artText.parentNode.removeChild(artText);
		} else {
			// Check if a dot needs to be added to the end of the current sentence
			if (sentence.includes(".")) {
				sentBuffer += sentence;
				numSentences++;
			} else {
				sentBuffer += sentence + ".";
				numSentences++;
			}
		}
		
		
	}
}