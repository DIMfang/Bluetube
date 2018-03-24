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
            if (data.status == 200) {
                localStorage.setItem('session', JSON.stringify(data.userData));
                document.location.href = "../";
            }
        }).catch(error => {
            console.log(error);
        });
}