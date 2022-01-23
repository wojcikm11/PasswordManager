function copyToClipBoard() {
    const password = document.getElementById("password").innerHTML;
    navigator.clipboard.writeText(password.value);
}