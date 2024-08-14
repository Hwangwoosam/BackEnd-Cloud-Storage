import { submitForm } from './submit.js';
import { idLength, onlyNumberAndEnglish } from './validate.js';

document.addEventListener('DOMContentLoaded', function(){
    const inputForm = document.getElementById('inputForm');
    if (inputForm) {
        inputForm.addEventListener('submit', function(event) {
            event.preventDefault();
            loginSubmit();
        });
    } else {
        console.error("Element with ID 'inputForm' not found");
    }
});

function loginSubmit(){
    const form = document.getElementById('inputForm');
    const formData = new FormData(form);

    fetch('/login', {
        method: 'POST',
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams(formData)
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        } else if (!response.ok) {
            throw new Error('Login failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('로그인 중 오류가 발생했습니다.');
    });
}
