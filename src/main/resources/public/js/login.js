(function loginIIFE() {
    const usernameField = document.getElementsByName('username')[0];

    document.getElementById('login-form').onsubmit = onLoginFormSubmit;

    function onLoginFormSubmit() {
        window.sessionStorage.setItem('username', usernameField.value);

        console.log(window.sessionStorage.getItem('username'));
    }
})();
