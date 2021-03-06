package com.codeosseum.ares.security.signup.controller;

import com.codeosseum.ares.security.signup.service.RegistrationDetails;
import com.codeosseum.ares.security.signup.service.RegistrationFailedException;
import com.codeosseum.ares.security.signup.service.UserRegistrationService;
import com.codeosseum.ares.security.util.AuthenticationService;
import com.codeosseum.ares.web.Paths;
import com.codeosseum.ares.web.Views;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SignUpController {
    private static final String REDIRECT_TO_GAME_HOME = "redirect:" + Paths.Game.HOME;

    private final AuthenticationService authenticationService;

    private final UserRegistrationService userRegistrationService;

    public SignUpController(final AuthenticationService authenticationService, final UserRegistrationService userRegistrationService) {
        this.authenticationService = authenticationService;
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping(Paths.SIGN_UP)
    public String getRegistrationView() {
        return authenticationService.hasAuthenticatedUser() ? REDIRECT_TO_GAME_HOME : Views.SIGN_UP;
    }

    @PostMapping(Paths.Api.REGISTRATION)
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody @Valid final SignUpRequest signUpRequest, final BindingResult bindingResult) {
        if (isFormValid(bindingResult)) {
            try {
                performRegistration(signUpRequest);

                return new ResponseEntity(HttpStatus.CREATED);
            } catch (RegistrationFailedException e) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            final RegistrationFailureResponse responseBody = RegistrationFailureResponse.fromFieldErrors(bindingResult.getFieldErrors());

            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isFormValid(final BindingResult bindingResult) {
        return !bindingResult.hasErrors();
    }

    private void performRegistration(final SignUpRequest signUpRequest) throws RegistrationFailedException  {
        final RegistrationDetails registrationDetails = RegistrationDetails.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .build();

        userRegistrationService.register(registrationDetails);
    }

    private static final class RegistrationFailureResponse {
        private final List<FieldErrorResponse> errors;

        private static RegistrationFailureResponse fromFieldErrors(final List<FieldError> fieldErrors) {
            final List<FieldErrorResponse> fieldErrorResponses = fieldErrors.stream()
                    .map(FieldErrorResponse::fromFieldError)
                    .collect(Collectors.toList());

            return new RegistrationFailureResponse(fieldErrorResponses);
        }

        private RegistrationFailureResponse(List<FieldErrorResponse> errors) {
            this.errors = errors;
        }

        public List<FieldErrorResponse> getErrors() {
            return errors;
        }

        private static final class FieldErrorResponse {
            private final String field;

            private final String message;

            private static FieldErrorResponse fromFieldError(final FieldError fieldError) {
                return new FieldErrorResponse(fieldError.getField(), fieldError.getDefaultMessage());
            }

            private FieldErrorResponse(String field, String message) {
                this.field = field;
                this.message = message;
            }

            public String getField() {
                return field;
            }

            public String getMessage() {
                return message;
            }
        }
    }
}
