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

function upload() { 
    xhr.addFormData('file', document.getElementById('file').files[0]);
    xhr.preparedXHR('POST', './Upload', (data) => {
    	console.log(data);
        document.getElementById('uploadStatus').textContent = data + '\nFile Uploaded';
    })
    xhr.execute();
}