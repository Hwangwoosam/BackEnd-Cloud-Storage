function idLength(value){
    return value.length >= 4 && value.length <= 12
}

function nameLength(value){
    return value.length >= 2 && value.length <= 10
}

function onlyNumberAndEnglish(str){
    return /^[A-Za-z0-9]+$/.test(str);
}

function onlyKoreanAndEnglish(str){
    return /^[가-힣a-zA-Z]+$/.test(str);
}

function strongPassword(str){
    return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/.test(str);
}

function isMatch (password1, password2) {
  return password1 === password2;
}

function checkPhoneNumber(str){
    return /^(0\d{1,3})-(\d{3,4})-(\d{4})$/.test(str);
}

export{
    idLength,
    nameLength,
    onlyNumberAndEnglish,
    onlyKoreanAndEnglish,
    strongPassword,
    isMatch,
    checkPhoneNumber
};