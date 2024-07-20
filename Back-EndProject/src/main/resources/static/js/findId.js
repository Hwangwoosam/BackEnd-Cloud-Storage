import { nameLength, onlyKoreanAndEnglish, checkPhoneNumber} from './validate.js';

var userNameElement = {
    userName: document.getElementById("userName"),
    componentValid: document.querySelector('.name-failure-message'),
    lengthValid: document.querySelector('.name-failure-message2')
};

var phoneNumberElement = {
    phoneNumber: document.getElementById("phoneNumber"),
    formatValid: document.querySelector('.phoneNumber-failure-message')
};

var isFormValid = false;

document.addEventListener('DOMContentLoaded',function(){
    const findIdForm = document.getElementById('findIdForm');
    const resultDiv = document.getElementById('result');
    const resultMessage = document.getElementById('resultMessage');
    const goToLoginButton = document.getElementById('goToLogin');

    userNameElement.userName.addEventListener('input',validateForm);
    phoneNumberElement.phoneNumber.addEventListener('input',validateForm);

    validateForm();

    if(findIdForm){
        findIdForm.addEventListener('submit',async function(event){
            event.preventDefault();

            try{
                const foundId = await findIdFormSubmit(this);
                findIdForm.style.display = 'none';
                resultDiv.style.display = 'block';

                if (foundId) {
                   resultMessage.textContent = `ID: ${foundId}`;
                   goToLoginButton.style.display = 'block';
                } else {
                   alert('일치하는 정보가 없습니다.');
                   findIdForm.style.display = 'block';
                   resultDiv.style.display = 'none';
                }
            }catch(error){
                console.error('Error:', error);
                alert('아이디 찾기 중 오류가 발생했습니다.');
            }
        });
    }else{
        console.error("Element with ID 'findIDForm' not found");
    }

    goToLoginButton.addEventListener('click', function() {
            window.location.href = '/loginPage';  // 로그인 페이지 URL로 변경하세요
    });
});

async function findIdFormSubmit(form){
        const formData = new FormData(form);
        const userData = {};
        formData.forEach((value, key) => { userData[key] = value });

         try {
                const response = await fetch("/findId", {
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
                    return data.foundId;
                }
            } catch (error) {
                console.error('Error:', error);
                throw error;
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

function validateForm() {
    const userName = userNameElement.userName.value;
    const phoneNumber = phoneNumberElement.phoneNumber.value;

    isFormValid =
        nameLength(userName) &&
        onlyKoreanAndEnglish(userName) &&
        checkPhoneNumber(phoneNumber);

    updateFindIdButton();
}

function updateFindIdButton() {
    const registerButton = document.getElementById('findIdButton');
    registerButton.disabled = !isFormValid;
}