document.addEventListener("DOMContentLoaded", () => {
  const btnLogin = document.getElementById("btnLogin");
  const loginStatus = document.getElementById("loginStatus");
  const btnTestCheckout = document.getElementById("btnTestCheckout");

  const current = localStorage.getItem("consign_user_id");
  if (current) {
    loginStatus.textContent = `目前使用者 ID：${current}`;
  }

  btnLogin.addEventListener("click", () => {
    localStorage.setItem("consign_user_id", "1");
    loginStatus.textContent = "已登入測試帳號（userId=1）";
  });

  btnTestCheckout.addEventListener("click", () => {
    window.location.href = "http://localhost:8080/demo/test-checkout";
  });
});
