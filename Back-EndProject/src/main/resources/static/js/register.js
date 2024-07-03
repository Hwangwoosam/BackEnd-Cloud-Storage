let elInputUserId = document.getElementById("userId");
let elFailureMessage = document.querySelector('.failure-message');
let elFailureMessageTwo = document.querySelector('.failure-message2');

let elInputPassword = document.getElementById("password");
let elInputPasswordRetype = document.querySelector('#password-retype');
let elMismatchMessage = document.querySelector('.mismatch-message');
let elStrongPasswordMessage = document.querySelector('.strongPassword-message');

document.addEventListener('DOMContentLoaded',function(){
    const checkDuplicateButton = document.getElementById('checkDuplicate');
    checkDuplicateButton.addEventListener('click',checkDuplicateId);

    const inputForm = document.getElementById('inputForm');
    inputForm.addEventListener('submit', function(event) {
        event.preventDefault();
        submitForm();
    });
});

function idLength(value){
    return value.length >= 4 && value.length <= 12
}

function onlyNumberAndEnglish(str){
    return /^[A-Za-z0-9]+$/.test(str);
}

function strongPassword(str){
    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(str);
}

function isMatch (password1, password2) {
  return password1 === password2;
}

elInputUserId.onkeyup = function(){
    if (elInputUserId.value.length !== 0) {
        if(onlyNumberAndEnglish(elInputUserId.value) === false){
            elFailureMessage.classList.add('hide');
            elFailureMessageTwo.classList.remove('hide');
        }else if(idLength(elInputUserId.value) === false){
            elFailureMessage.classList.remove('hide');
            elFailureMessageTwo.classList.add('hide');
        }else if(idLength(elInputUserId.value) || onlyNumberAndEnglish(elInputUserId.value)){
            elFailureMessage.classList.add('hide');
            elFailureMessageTwo.classList.add('hide');
        }
    }else {
         elFailureMessage.classList.add('hide');
         elFailureMessageTwo.classList.add('hide');
    }
}

elInputPassword.onkeyup = function(){
    if(elInputPassword.value.length !== 0){
        if(strongPassword(elInputPassword.value)){
            elStrongPasswordMessage.classList.add('hide');
        }else{
            elStrongPasswordMessage.classList.remove('hide');
        }
    }else{
        elStrongPasswordMessage.classList.add('hide');
    }
}

elInputPasswordRetype.onkeyup = function(){
    if(elInputPasswordRetype.value.length !== 0){
        if(isMatch(elInputPassword.value, elInputPasswordRetype.value)){
            elMismatchMessage.classList.add('hide');
        }else{
            elMismatchMessage.classList.remove('hide');
        }
    }else{
        elMismatchMessage.classList.add('hide');
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

function submitForm() {
    const form = document.getElementById('inputForm');
    const formData = new FormData(form);
    const userData = {};
    formData.forEach((value, key) => { userData[key] = value });;

    fetch('/register', {
        method: 'POST',
        headers: {
               "Content-Type":"application/json; charset=utf-8"
        },
        body: JSON.stringify(userData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('회원가입이 완료되었습니다.');
            window.location.href = '/login'; // 로그인 페이지로 리다이렉트
        } else {
            alert('회원가입 중 오류가 발생했습니다: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('회원가입 중 오류가 발생했습니다.');
    });
}