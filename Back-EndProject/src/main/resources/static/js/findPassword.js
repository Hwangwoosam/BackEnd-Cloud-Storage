import { idLength, onlyNumberAndEnglish, strongPassword, isMatch, checkPhoneNumber} from './validate.js';

var userIdElement = {
    userId: document.getElementById("userId"),
    lengthValid: document.querySelector('.failure-message'),
    componentValid: document.querySelector('.failure-message2')
};

var phoneNumberElement = {
    phoneNumber: document.getElementById("phoneNumber"),
    formatValid: document.querySelector('.phoneNumber-failure-message')
};

var nextPasswordElement = {
    nextPassword: document.getElementById("nextPassword"),
    strongPassword: document.querySelector('.strongPassword-message')
}

var nextPasswordRetypeElement = {
    nextPasswordRetype: document.getElementById("nextPasswordRetype"),
    mismatch: document.querySelector('.mismatch-message')
}

var isFindFormValid = false;
var isChangeFormValid = false;
var findData = null;

document.addEventListener('DOMContentLoaded', function() {
    const inputForm = document.getElementById('inputForm');
    const changePasswordForm = document.getElementById('changePasswordForm');
    const changePasswordBtn = document.getElementById('changePasswordButton');
    const findPasswordBtn = document.getElementById('findPasswordButton');

    userIdElement.userId.addEventListener('input',validateFindForm);
    phoneNumberElement.phoneNumber.addEventListener('input',validateFindForm);

    nextPasswordElement.nextPassword.addEventListener('input',validateChangeForm);
    nextPasswordRetypeElement.nextPasswordRetype.addEventListener('input',validateChangeForm);

    validateFindForm();
    validateChangeForm();

    if(inputForm){
        inputForm.addEventListener('submit',async function(e){
            e.preventDefault();

            try{
                const result = await findPasswordFormSubmit(this);
                findData = result;
                inputForm.style.display = 'none';
                changePasswordForm.style.display = 'block';
            }catch(error){
                console.error('Error:', error);
                alert('비밀번호 찾기 중 오류가 발생했습니다.');
            }
        });
    }else{
        console.error("Element with ID 'inputForm' not found");
    }

    if(changePasswordForm){
         changePasswordForm.addEventListener('submit', function(e) {
                e.preventDefault();

                const formData = new FormData(this);
                const userData = {};
                if(findData && findData.id){
                    userData["id"] = findData.id;
                }else{
                     console.error("ID is missing in findData");
                     alert("사용자 ID를 찾을 수 없습니다.");
                     return;
                }

                formData.forEach((value, key) => { userData[key] = value });

                console.log(userData);
                // 서버에 비밀번호 변경 요청
                fetch('/changePassword', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(userData)
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('비밀번호가 성공적으로 변경되었습니다.');
                        window.location.href = '/login'; // 로그인 페이지로 리다이렉트
                    } else {
                        alert('비밀번호 변경에 실패했습니다. 다시 시도해주세요.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('오류가 발생했습니다. 다시 시도해주세요.');
                });
         });
    }

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

nextPasswordElement.nextPassword.onkeyup = function(){
    if(nextPasswordElement.nextPassword.value.length !== 0){
        if(strongPassword(nextPasswordElement.nextPassword.value)){
            nextPasswordElement.strongPassword.classList.add('hide');
        }else{
            nextPasswordElement.strongPassword.classList.remove('hide');
        }
    }else{
        nextPasswordElement.strongPassword.classList.add('hide');
    }
}

nextPasswordRetypeElement.nextPasswordRetype.onkeyup = function(){
    if(nextPasswordRetypeElement.nextPasswordRetype.value.length !== 0){
        if(isMatch(nextPasswordRetypeElement.nextPasswordRetype.value,nextPasswordElement.nextPassword.value)){
            nextPasswordRetypeElement.mismatch.classList.add('hide');
        }else{
            nextPasswordRetypeElement.mismatch.classList.remove('hide');
        }
    }else{
        nextPasswordRetypeElement.mismatch.classList.add('hide');
    }
}

async function findPasswordFormSubmit(form){
        const formData = new FormData(form);
        const userData = {};
        formData.forEach((value, key) => { userData[key] = value });

         try {
                const response = await fetch("/findPassword", {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(userData)
                });

                if (!response.ok) {
                     const errorText = await response.text();
                     console.error('Response not OK:', response.status, errorText);
                     throw new Error(`서버 응답 오류: ${response.status}`);
                }
                const contentType = response.headers.get("content-type");

                if (!contentType || !contentType.includes("application/json")) {
                    const errorText = await response.text();
                    console.error('Invalid content type:', contentType, errorText);
                    throw new Error("서버가 JSON이 아닌 응답을 반환했습니다.");
                }
                const data = await response.json();
                if (data.success) {
                    return data;
                }
            } catch (error) {
                console.error('Error:', error);
                throw error;
            }
}

function validateFindForm() {
    const userId = userIdElement.userId.value;
    const phoneNumber = phoneNumberElement.phoneNumber.value;

    isFindFormValid =
        idLength(userId) &&
        onlyNumberAndEnglish(userId) &&
        checkPhoneNumber(phoneNumber);

    updateFindIdButton();
}

function validateChangeForm() {
    const nextPassword = nextPasswordElement.nextPassword.value;
    const nextPasswordRetype = nextPasswordRetypeElement.nextPasswordRetype.value;

    isChangeFormValid =
        strongPassword(nextPassword) &&
        isMatch(nextPassword,nextPasswordRetype);

    updateChangeIdButton();
}

function updateFindIdButton() {
    const findPasswordBtn = document.getElementById('findPasswordButton');
    findPasswordBtn.disabled = !isFindFormValid;
}

function updateChangeIdButton() {
    const changePasswordBtn = document.getElementById('changePasswordButton');
    changePasswordBtn.disabled = !isChangeFormValid;
}