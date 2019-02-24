(function signInIIFE() {
    const usernameField = document.getElementsByName('username')[0];

    document.getElementsByClassName('sign-in-form')[0].onsubmit = onSignInFormSubmit;

    function onSignInFormSubmit() {
        window.sessionStorage.setItem('username', usernameField.value);

        console.log(window.sessionStorage.getItem('username'));
    }
})();
