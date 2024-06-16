function validatePwdCheck() {
    var password = document.getElementsByName("password")[0].value;
    var passwordCheck = document.getElementsByName("passwordCheck")[0].value;

    if (password !== passwordCheck) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return false;
    }
    return true;
}