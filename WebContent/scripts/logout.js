
function doFetch(url, configs, callBack) {
    
}

function logout() {
  	let configs = {
  		method: 'GET',
  		headers: {
  			'Content-type':'application/x-www-form-urlencoded'
  		},
  		credentials: 'same-origin'
  	}
  	fetch('./Logout', configs)
  	.then(response => response.json())
  	.then(data => {
  		// window.location.replace("./index.html");
  		console.log(data); 
  	}).catch(error => {
  		console.log(error.message);
  	})

}

function setVideo(path) {
	document.getElementById("video").src = "./Streaming?path=" + path;
}

function download(){
	let configs = {
			method: 'GET',
			credentials: 'same-origin',
			
	}
	fetch('./Download?search=' + document.getElementById("search_input").value, configs)
	.then(response => response.json())
	.then(data => {
		console.log(data);
		setVideo(data.path);
	})
	
}
function upload() { 
	const fd = new FormData();
	fd.append('file', document.getElementById('file').files[0]);
	fd.append('media_name', document.getElementById('media_name').value);
	fd.append('media_des', document.getElementById('media_des').value);
	let configs = {
		method: 'POST',
//        headers: {
//  			'Content-type':'multipart/form-data'
//  		},
		credentials: 'same-origin',
		body: fd	
	}
	fetch('./Upload', configs)
	.then(response => response.json())
	.then(data => {
		console.log(data);
		document.getElementById('uploadStatus').textContent = data + '\nFile Uploaded';
	}).catch(error => {
		console.log(error.message);
	})
}



