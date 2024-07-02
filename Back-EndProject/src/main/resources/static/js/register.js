document.addEventListener('DOMContentLoaded',function(){
    const checkDuplicateButton = document.getElementById('checkDuplicate');
    checkDuplicateButton.addEventListener('click',checkDuplicateId);

    const registerForm = document.getElementById('inputForm');
    registerForm.addEventListener('submit', function(event) {
        event.preventDefault();
        submitForm();
    });
});

function clearResult() {
    const resultDiv = document.getElementById('idCheckResult');
    resultDiv.textContent = '';
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
    const user = Object.fromEntries(formData);

    fetch('/register', {
        method: 'POST',
        headers: {
              'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
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