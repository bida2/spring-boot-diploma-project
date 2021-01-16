window.onload = () => {
	let elementArr = document.getElementsByClassName("reserve");
	[...elementArr].forEach(element => {
		let hrefAtt = element.getAttribute("href");
		element.addEventListener("click", function () {
			let stockId = hrefAtt.substring(13);
			event.preventDefault();
			return fetch('/reserve?sId=' + stockId, {
				method: "GET",
				headers: {
					'Content-Type': 'application/json',
					'Accept' : 'application/json'
			        }
			})
			.then(res => res.json())
			.then(res => JSON.stringify(res))
			.then(function() {
				element.insertAdjacentHTML('afterend', "<p>Stock is reserved!</p>");
				element.parentNode.removeChild(element);
			});
		});
	});
}

