(function registrationIIFE() {
    const usernameField = document.getElementsByName('username')[0];
    const passwordField = document.getElementsByName('password')[0];
    const emailField = document.getElementsByName('email')[0];

    document.getElementsByClassName('register-button')[0].addEventListener('click', initiateRegistration)

    async function initiateRegistration() {
        const registrationRequest = {
            username: usernameField.value,
            password: passwordField.value,
            email: emailField.value,
        };
        
        const response = await fetch('/api/registration', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(registrationRequest)
        });
        
        if (response.ok) {
            window.location = '/login';
        } else {
            const failure = await response.json();

            console.log(failure);
        }
    }
})();
