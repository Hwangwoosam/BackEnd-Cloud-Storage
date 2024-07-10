import { submitForm } from './submit.js';
import { idLength, nameLength, onlyNumberAndEnglish, onlyKoreanAndEnglish, strongPassword, isMatch, checkPhoneNumber} from './validate.js';

var userIdElement = {
    userId: document.getElementById("userId"),
    lengthValid: document.querySelector('.failure-message'),
    componentValid: document.querySelector('.failure-message2')
};

var passwordElement = {
    password: document.getElementById("password"),
    strongPassword: document.querySelector('.strongPassword-message')
};

var passwordRetypeElement = {
    passwordRetype: document.querySelector('#password-retype'),
    mismatch: document.querySelector('.mismatch-message')
};

var userNameElement = {
    userName: document.getElementById("userName"),
    componentValid: document.querySelector('.name-failure-message'),
    lengthValid: document.querySelector('.name-failure-message2')
};

var phoneNumberElement = {
    phoneNumber: document.getElementById("phoneNumber"),
    formatValid: document.querySelector('.phoneNumber-failure-message')
};

document.addEventListener('DOMContentLoaded',function(){
    const checkDuplicateButton = document.getElementById('checkDuplicate');
    checkDuplicateButton.addEventListener('click',checkDuplicateId);

    const inputForm = document.getElementById('inputForm');
    inputForm.addEventListener('submit', function(event) {
        event.preventDefault();
        registerSubmit();
    });
});

userIdElement.userId.onkeyup = function(){
    if (userIdElement.userId.value.length !== 0)
    {
        if(onlyNumberAndEnglish(userIdElement.userId.value) === false)
        {
            userIdElement.lengthValid.classList.add('hide');
            userIdElement.componentValid.classList.remove('hide');
        }
        else if(idLength(userIdElement.userId.value) === false)
        {
            userIdElement.lengthValid.classList.remove('hide');
            userIdElement.componentValid.classList.add('hide');
        }else
        {
            userIdElement.lengthValid.classList.add('hide');
            userIdElement.componentValid.classList.add('hide');
        }
    }
    else
    {
         userIdElement.lengthValid.classList.add('hide');
         userIdElement.componentValid.classList.add('hide');
    }
}

passwordElement.password.onkeyup = function(){
    if(passwordElement.password.value.length !== 0)
    {
        if(strongPassword(passwordElement.password.value))
        {
            passwordElement.strongPassword.classList.add('hide');
        }
        else
        {
            passwordElement.strongPassword.classList.remove('hide');
        }
    }
    else
    {
        passwordElement.strongPassword.classList.add('hide');
    }
}

passwordRetypeElement.passwordRetype.onkeyup = function(){
    if(passwordRetypeElement.passwordRetype.value.length !== 0)
    {
        if(isMatch(passwordElement.password.value, passwordRetypeElement.passwordRetype.value))
        {
            passwordRetypeElement.mismatch.classList.add('hide');
        }
        else
        {
            passwordRetypeElement.mismatch.classList.remove('hide');
        }
    }
    else
    {
        passwordRetypeElement.mismatch.classList.add('hide');
    }
}

userNameElement.userName.onkeyup = function(){
    if(userNameElement.userName.value.length !== 0)
    {
        if(onlyKoreanAndEnglish(userNameElement.userName.value) === false)
        {
            userNameElement.componentValid.classList.remove('hide');
            userNameElement.lengthValid.classList.add('hide');
        }
        else if(nameLength(userNameElement.userName.value) === false)
        {
            userNameElement.componentValid.classList.add('hide');
            userNameElement.lengthValid.classList.remove('hide');
        }
        else
        {
            userNameElement.componentValid.classList.add('hide');
            userNameElement.lengthValid.classList.add('hide');
        }
    }else
    {
        userNameElement.componentValid.classList.add('hide');
        userNameElement.lengthValid.classList.add('hide');
    }
}

phoneNumberElement.phoneNumber.onkeyup = function(){
    if(phoneNumberElement.phoneNumber.value.length !== 0)
    {
        if(checkPhoneNumber(phoneNumberElement.phoneNumber.value) === false)
        {
            phoneNumberElement.formatValid.classList.remove('hide');
        }
        else
        {
            phoneNumberElement.formatValid.classList.add('hide');
        }
    }
    else
    {
        phoneNumberElement.formatValid.classList.add('hide');
    }
}

function checkDuplicateId(){
    const id = document.getElementById('userId').value;
    const resultDiv = document.getElementById('idCheckResult');

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json',
         'Cache-Control': 'no-cache'},
        body: JSON.stringify({ userId: id })
    };

    fetch('/checkDuplicateId', requestOptions)
        .then(response => response.json())
        .then(isDuplicate => {
            if(isDuplicate){
                resultDiv.style.color = 'red';
                resultDiv.textContent = '이미 존재하는 아이디입니다.';
            }else{
                resultDiv.style.color = 'blue';
                resultDiv.textContent = '생성 가능한 아이디입니다.';
            }
        })
        .catch(error => {
            console.error('DuplicatedError',error);
            resultDiv.style.color = 'red';
            resultDiv.textContent = '중복 체크 중 오류가 발생헀습니다.';
        });
}

function registerSubmit() {
    submitForm(
        '/register',
        '회원가입',
        '/login'
    );
}