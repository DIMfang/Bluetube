var xhr = new myXHR();

function logout() {
  xhr.preparedXHR('GET', './Logout', (data) => {
    console.log(data);
  });
  xhr.execute();
}