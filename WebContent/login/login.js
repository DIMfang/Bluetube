function $(id) {
  return document.getElementById(id);
}

function getValues() {
  return {
    username: $('username').value,
    password: $('password').value
  }	 
}

function login(){
	let inputs = getValues();
	let configs = {
		method: 'POST',
		headers: {
		  'Content-type':'application/x-www-form-urlencoded'
		},
		body: JSON.stringify(inputs),
		credentials: 'same-origin',
	}

	fetch('../Login', configs)
	.then(response => response.json())
	.then(data => {
		console.log(data);
		if(data.status == 200) {
			document.location.href= "../logout/";
		}
	}).catch(error => {
		console.log(error);
	})
}



