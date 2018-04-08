function LogOut() {
    let path = document.location.pathname;
    let url = (path == '/BlueTube/') ? './Logout' : '../Logout';
    let configs = {
        method: 'GET',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        credentials: 'same-origin'
    }
    // TODO: No se ejecuta tras cambio del contexto, y a veces en otros momentos
    fetch(url, configs)
        .then(response => response.json())
        .then(data => {        
        	localStorage.clear();
        	if (data.status == 200) {            	
                window.location.reload();
            }
        }).catch(error => {        	
            console.log(error.message);
        })
}

class NavBar extends HTMLElement {
    constructor() {
        super();
        ['inref', 'upref', 'href', 'pref'].forEach(type => {
            if (!this.hasAttribute(type)) {
                this.setAttribute(type, '#');
            }
        })
        if(!this.hasAttribute('profile-img')) {
            this.setAttribute('profile-img', '../img/ic_person_white_48dp_1x.png');
        }
        this.urls = {
            brand: this.getAttribute('href'),
            login: this.getAttribute('inref'),
            signup: this.getAttribute('upref'),
            profile: this.getAttribute('pref'),
            profileImg: this.getAttribute('profile-img') 
        }
    } 
    connectedCallback() {
        this.innerHTML = `
        <nav style="background-color:#4286f4; height:60px;"
        class="navbar navbar-expand navbar-dark justify-content-between">
            <a class="navbar-brand">Bluetube</a>            
            <div class="navbar-nav">  
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
        this.stateContainer.innerHTML = `
            <div class="btn-group">    
                <a style="border:0; box-shadow: none; background-color:#4286f4; border-color:#4286f4;" class="btn dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <img class="img-fluid" src="${this.urls.profileImg}" width="30" height="30" alt="">    
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${this.urls.profile}">Profile</a>
                    <a class="dropdown-item" onclick="LogOut()" href="">Logout</a>
                </div>
            </div>
        `
    }
    isOut() {
        this.stateContainer.innerHTML = `
            <a class="nav-item nav-link" href="${this.urls.login}">LOGIN</a>
            <span class="nav-item navbar-text">or</span>
            <a id="id1" class="nav-item nav-link" href="${this.urls.signup}">SIGN UP</a>
        `
    }
    get stateContainer() {
        return this.querySelector('div');
    }
    get brand() {
        return this.querySelectorAll('a')[0];
    }
    get userData() {
        return JSON.parse(localStorage.getItem('session'));
    }
}
customElements.define('nav-bar', NavBar);