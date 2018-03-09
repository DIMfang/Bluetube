var xhr = new myXHR();

function logout() {
  xhr.preparedXHR('GET', './Logout', (data) => {
    console.log(data);
    if(data.status == 200){
    	window.location.replace("./index.html");
    }
  });
  xhr.execute();
}