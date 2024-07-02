document.addEventListener('DOMContentLoaded',function(){
    const checkDuplicateButton = document.getElemenetById('checkDuplicate');
    checkDuplicateButton.addEventListener('click',checkDuplicateId);
});

function checkDuplicateId(){
    const id = document.getElementById('ID').value;
    const resultDiv = document.getElementById('idCheckResult');

    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId: id })
    };

    fetch('/checkDuplicatedI', requestOptions)
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