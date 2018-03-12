// import myXHR from '/util/XHR.js';

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

function register() {
    let inputs = getValues();
    xhr.preparedXHR('POST', './Register', (data) => {
        console.log(data);
    });
    xhr.execute(inputs);
}

