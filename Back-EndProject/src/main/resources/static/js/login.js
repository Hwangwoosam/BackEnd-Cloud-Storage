function login() {
    var userName = document.getElementById('userName').value;

    if (userName.trim() === "") {
        alert("사용자 이름을 입력하세요.");
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/login', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var response = JSON.parse(xhr.responseText);

                if (response.result === 'success') {
                    alert("로그인 성공!");
                    postToGetList(userName);
                    
                } else {
                    alert("로그인 실패!");
                }
            } else {
                alert("서버 오류: " + xhr.status);
            }
        }
    };

    var data = JSON.stringify({
        userName: userName
    });

    xhr.send(data);
}

function postToGetList(userName) {
    var form = document.createElement('form');
    form.method = 'POST';
    form.action = '/file/getList';

    var userNameInput = document.createElement('input');
    userNameInput.type = 'hidden';
    userNameInput.name = 'userName';
    userNameInput.value = userName;

    form.appendChild(userNameInput);
    document.body.appendChild(form);
    form.submit();
}