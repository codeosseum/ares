(function signUpIIFE() {
    const usernameField = document.getElementsByName('username')[0];
    const passwordField = document.getElementsByName('password')[0];
    const emailField = document.getElementsByName('email')[0];

    document.getElementsByClassName('sign-up-button')[0].addEventListener('click', initiateSignUp);

    document.querySelectorAll('input').forEach(input => input.addEventListener('input', clearInputFieldError))
    
    function clearInputFieldError(event) {
        const fieldError = selectFieldError(event.target.name);

        if (fieldError) {
            fieldError.textContent = '';
        }
    };

    async function initiateSignUp() {
        const signUpRequest = {
            username: usernameField.value,
            password: passwordField.value,
            email: emailField.value,
        };

        const response = await fetch('/api/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(signUpRequest)
        });
        
        if (response.ok) {
            window.location = '/signin';
        } else {
            const failure = await response.json();

            displayFieldErrors(failure.errors);
        }
    };

    function displayFieldErrors(errors) {
        errors.forEach(error => selectFieldError(error.field).textContent = error.message);
    }

    function selectFieldError(fieldName) {
        return document.querySelector(`.field-error.${fieldName}`);
    }
})();
