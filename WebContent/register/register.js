
function $(id) {
    return document.getElementById(id);
}

function getValues() {
    return {
        username: $('username').value,
        name: $('firstname').value,
        lastname: $('lastname').value,
        email: $('email').value,
        password: $('password').value
    }

}

function register() {
	let inputs = getValues();
    let configs = {
        method: 'POST',
        headers: {
            'Content-type':'application/x-www-form-urlencoded'
        },
        body: JSON.stringify(inputs)
    }
    fetch('../Register', configs)
    .then(response => response.json()).then(data => {
    	console.log(data)
    	if(data.status == 200) {
    		document.location.href = "../login/";
    	}
    }).catch(error => {
    	console.log(error.message);
    });    
}

