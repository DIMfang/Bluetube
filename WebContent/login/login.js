function $(id) {
    return document.getElementById(id);
}

function getValues() {
    return {
        username: $('username').value,
        password: $('password').value
    }
}

function login() {
    let inputs = getValues();
    let configs = {
        method: 'POST',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        credentials: 'same-origin',
        body: JSON.stringify(inputs),
    }

    fetch('../Login', configs)
        .then(response => response.json())
        .then(data => {
            let userData = data.userData;
            if (data.status == 200) {
                delete userData['type_des'];
                delete userData['id_user']
                localStorage.setItem('session', JSON.stringify(userData));
                document.location.href = "../";
            }
        }).catch(error => {
            console.log(error);
        });
}