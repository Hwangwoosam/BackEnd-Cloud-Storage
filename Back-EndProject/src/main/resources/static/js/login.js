function login() {
    var userName = document.getElementById('userName').value;

    // 간단한 유효성 검사 (실제로는 더 강화된 검사가 필요)
    if (userName.trim() === "") {
        alert("사용자 이름을 입력하세요.");
        return;
    }

    // AJAX를 사용하여 서버에 사용자 이름을 전송
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/login', true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("로그인 성공!");
            } else {
                alert("로그인 실패!");
            }
        }
    };

    var data = JSON.stringify({
        userName: userName
    });

    xhr.send(data);
}
