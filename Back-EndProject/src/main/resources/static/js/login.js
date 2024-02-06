function login() {
    var userName = document.getElementById('userName').value;
    var includeDir = 0;

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
                    window.location.href = '/file/getList?userName=' + encodeURIComponent(userName);
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