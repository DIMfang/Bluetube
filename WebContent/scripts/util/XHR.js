// Function XmlHttpRequest
function myXHR () {

    // Variables privadas
    var xhr = new XMLHttpRequest();
    var fd = new FormData();
    
    // Properties
    var properties = {
        url: '',
        method: '',
        mode: true
    }
    
    this.preparedXHR = (method, url, callBack, mode) => {
        
        this.setCallback(callBack);
        this.setUrl(url);
        this.setMethod(method);
        this.setMode(mode);

    }

    this.addFormData = (p1,p2) => fd.append(p1,p2);
    this.setJSON = (params) => Object.keys(params).forEach(key => { fd.append(key,params[key])});
    this.setMethod = (method) => properties.method = method;
    this.setUrl = (url) => properties.url = url;
    this.setMode = (mode) => properties.mode = (mode != null) ? mode : true;
    this.setCallback = (callBack) => {
        xhr.onreadystatechange = () => {
            // Get method
            if (xhr.readyState == 4 && xhr.status == 200) {
                try {
                	let data = JSON.parse(xhr.responseText);
                    callBack(data);
                } catch (e) {
                    console.error(e);
                }
            // Post method
            } else if (xhr.readyState == 4 && xhr.status == 201) {
                try {
                	let data = JSON.parse(xhr.responseText);
                    callBack(data);
                } catch (e) {
                    console.error(e);
                }
            }
        }
    }
    
    this.execute = (params) => {
        if (properties.method == 'GET') {
            xhr.open(properties.method, properties.url, properties.mode);
            xhr.send();
        } else if (properties.method == 'POST') {
            xhr.open(properties.method, properties.url, properties.mode);
            if(params == undefined) {
            	xhr.send(fd);
            } else {
            	xhr.send(JSON.stringify(params));
            }
            // Reinicio el formData
            fd = new FormData();
        }
    }
    
    
}


