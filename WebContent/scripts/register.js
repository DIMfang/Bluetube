var xhr = new myXHR();

function $(id) {
    return document.getElementById(id);
}

function getValues() {
  return {
    username: $("username").value,
    name: $("firstname").value,
    lastname: $("lastname").value,
    email: $("email").value,
    password: $("password").value
  }
}

function post() {
    let inputs = getValues();
    if(inputs != null){
	    xhr.preparedXHR('POST', './Register', (data) => {
	        console.log(data);
	    });
	    xhr.execute(inputs);
    }
}

