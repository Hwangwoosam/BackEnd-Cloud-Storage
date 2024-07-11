export function submitForm(apiEndpoint, funcMsg, redirect) {
    const form = document.getElementById('inputForm');
    const formData = new FormData(form);
    const userData = {};
    formData.forEach((value, key) => { userData[key] = value });;
    console.log(userData);
    fetch(apiEndpoint, {
        method: 'POST',
        headers: {
               "Content-Type":"application/json;"
        },
        body: JSON.stringify(userData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert( funcMsg + '완료되었습니다.');
            window.location.href = redirect; // 로그인 페이지로 리다이렉트
        } else {
            alert(funcMsg + '중 오류가 발생했습니다: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert(funcMsg + '중 오류가 발생했습니다.');
    });
}