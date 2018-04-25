
function setVideo() {
	document.getElementById("video").src = "./Streaming";
}

function download(){
	var url = "../Download?search=" + document.getElementById("search_input").value;
	var downloadWindow = window.open(url);
//	let configs = {
//		method: 'GET',
//		credentials: 'same-origin',			
//	}
//	fetch('./Download?search=' + document.getElementById("search_input").value, configs)
//	.then(response => {
//		
//	});
}

function stream(){
	document.getElementById("video").src = "../Streaming?search=" + document.getElementById("search_input").value;
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
	fetch('../Upload', configs)
	.then(response => response.json())
	.then(data => {
		console.log(data);
		let status = document.getElementById('uploadStatus');
		status.textContent = 'Video uploaded success';
		status.style.color = '#1A7DD7'
	}).catch(error => {
		console.log(error.message);
	})
}


