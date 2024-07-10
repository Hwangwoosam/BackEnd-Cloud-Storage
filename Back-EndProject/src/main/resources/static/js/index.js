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
    submitForm(
        '/login',
        '로그인',
        '/fileListPage'
    );
}
