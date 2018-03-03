// Funcion XmlHttpRequest
function myXHR () {

    // Variables privadas
    var xhr = new XMLHttpRequest();
    var fd = new FormData();

    // Variables publicas
    this.properties = {
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

    // this.addToFormData = (p1,p2) => fd.append(p1,p2);
    // this.setFormData = (formData) => fd = formData;
    this.setMethod = (method) => this.properties.method = method;
    this.setUrl = (url) => this.properties.url = url;
    this.setMode = (mode) => this.properties.mode = (mode != null) ? mode : true;
    this.setCallback = (callBack) => {
        xhr.onreadystatechange = () => {
            // Get method
            if (xhr.readyState == 4 && xhr.status >= 200 && xhr.status < 300) {
                try {
                	let data = JSON.parse(xhr.responseText);
                    callBack(data);
                } catch (e) {
                    console.error('Error');
                }
            // Post method
            } else if (xhr.readyState == 4 && xhr.status == 201) {
                try {
                	let data = JSON.parse(xhr.responseText);
                    callBack(data);
                } catch (e) {
                    console.error('Error');
                }
            }
        }
    }
    
    this.execute = (params) => {
        if (this.properties.method == 'GET') {
            xhr.open(this.properties.method, this.properties.url, this.properties.mode);
            xhr.send();
        } else if (this.properties.method == 'POST') {
            xhr.open(this.properties.method, this.properties.url, this.properties.mode);
            xhr.send(JSON.stringify(params));
        }
    
    }

}

