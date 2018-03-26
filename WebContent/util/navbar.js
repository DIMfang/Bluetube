LogOut = () => {
    let path = document.location.pathname;
    let url = (path == '/BlueTube/') ? './Logout' : '../Logout';
    let configs = {
        method: 'GET',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        credentials: 'same-origin'
    }
    fetch(url, configs)
        .then(response => response.json())
        .then(data => {
            if (data.status == 200) {
                if (localStorage.getItem('session')) {
                    localStorage.clear();
                }
                if (path == '/BlueTube/') {
                    window.location.reload();
                } else {
                    window.location.replace("../");
                }
            }
        }).catch(error => {
            console.log('ERROR en el catch');
            console.log(error.message);
        })
}

class NavBar extends HTMLElement {
    constructor() {
        super();
        ['inref', 'upref', 'href'].forEach(type => {
            if (!this.hasAttribute(type)) {
                this.setAttribute(type, '#');
            }
        })
        this.urls = {
            brand: this.getAttribute('href'),
            login: this.getAttribute('inref'),
            signup: this.getAttribute('upref'),
        }
    } // #001c37 #0d3a60 #03275A
    connectedCallback() {
        this.innerHTML = `
        <nav style="background-color:#0D397F; height:60px;" 
        class="navbar navbar-expand navbar-dark bg-dark sticky-top justify-content-between">
            <a class="navbar-brand">Bluetube</a>
            <form class="form-inline">
                <input id="input-search" class="form-control mr-2 h-75" type="text" placeholder="Search">
                <button class="btn btn-outline-primary btn-md" type="button">Search</button>
            </form>
            <div class="navbar-nav">
                <a class="nav-item nav-link"></a>
                <span class="nav-item navbar-text">or</span>
                <a id="id1" class="nav-item nav-link">SIGN UP</a>
            </div>
            </nav>
        `
        this.brand.setAttribute('href', this.urls.brand);
        this.userState();
    }
    userState() {
        let userData = this.userData;
        if (userData) {
            this.isIn();
        } else {
            this.isOut();
        }
    }
    isIn() {
        let state = this.state,
            sign = this.signup,
            span = this.span;
        state.textContent = 'LOGOUT';
        state.setAttribute('href', '');
        state.onclick = LogOut;
        sign.style.display = 'none';
        span.style.display = 'none';
    }
    isOut() {
        let state = this.state,
            sign = this.signup;
        state.textContent = 'LOGIN';
        state.setAttribute('href', this.urls.login);
        sign.setAttribute('href', this.urls.signup);
    }
    get brand() {
        return this.querySelectorAll('a')[0];
    }
    get state() {
        return this.querySelectorAll('a')[1];
    }
    get signup() {
        return this.querySelectorAll('a')[2];
    }
    get span() {
        return this.querySelector('span');
    }
    get userData() {
        return JSON.parse(localStorage.getItem('session'));
    }
}
customElements.define('nav-bar', NavBar);