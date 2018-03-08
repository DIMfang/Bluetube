var xhr = new myXHR();

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
  xhr.preparedXHR('POST', './Login', (data)=> {
    console.log(data);
    if(data.status == 13) {
      // Redireccionar a otra pagina
      window.location.replace("./logout.html");
    }
  });
  xhr.execute(inputs);
}