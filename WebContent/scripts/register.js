var xhr = new myXHR();

function $(id) {
    return document.getElementById(id);
}

function getValues() {
	if($("username").value && $("firstname").value && $("lastname").value && $("email").value && $("password").value != null){
		return {
			username: $("username").value,
			name: $("firstname").value,
			lastname: $("lastname").value,
			email: $("email").value,
			password: $("password").value
		}
	}else{
		alert("Hay campos vacios mardito");
		return null;
	}
}

function post() {
    let inputs = getValues();
    if(inputs!=null){
	    xhr.preparedXHR('POST', './Register', (data) => {
	        console.log(data);
	    });
	    xhr.execute(inputs);
    }
}

function login(){
	let inputs = getValues();
	if(inputs!=null){
		xhr.preparedXHR('POST', './Login', (data)=> {
			console.log(data);
		});
		xhr.execute(inputs);
	}
}