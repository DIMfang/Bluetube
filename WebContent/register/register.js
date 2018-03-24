// @import {  } from "../bootstrap/";
function $(id) {
    return document.getElementById(id);
} 

class NavBar extends HTMLElement {
    constructor() {
        super();
    }
    connectedCallback() {
        this.innerHTML = `
        <nav style="background-color: #001c37" class="navbar navbar-dark border-bot-1 border-primary navbar-expand-sm|md|lg|-xl navbar-dark|light bar">
            <a class="navbar-brand" href="">Bluetube</a>
            <a class="navbar-brand" style="font-size:15px; color:white"> LOGIN </a>
        </nav> `
        this.userStatus();
    }
    userStatus() {
        console.log(this.getStatus.textContent);
        this.getStatus.textContent = 'SIGN UP';
        this.getStatus.setAttribute('href', '#');
    }
    get getStatus() {
        return this.querySelectorAll('a')[1];
    }
}
customElements.define('nav-bar', NavBar);

function getValues() {
    return {
        username: $('username').value,
        name: $('firstname').value,
        lastname: $('lastname').value,
        email: $('email').value,
        password: $('password').value
    }

}

function register() {
    let inputs = getValues();
    let configs = {
        method: 'POST',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        body: JSON.stringify(inputs)
    }
    fetch('../Register', configs)
        .then(response => response.json()).then(data => {
            console.log(data)
            if (data.status == 200) {
                document.location.href = "../login/";
            }
        }).catch(error => {
            console.log(error.message);
        });
}