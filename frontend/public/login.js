const API_BASE_URL = "http://localhost:8081/api/members";

async function handleRegister() {
    const userId = document.getElementById("regUserId").value.trim();
    const password = document.getElementById("regPassword").value;
    const name = document.getElementById("regName").value.trim();

    if (!userId || !password || !name) {
        alert("아이디, 비밀번호, 이름을 모두 입력하세요.");
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, password, name })
        });

        if (response.ok) {
            alert("회원가입 성공!");
        } else {
            alert("회원가입 실패: " + await response.text());
        }
    } catch (error) {
        console.error(error);
        alert("서버에 연결할 수 없습니다. 백엔드 실행 상태를 확인하세요.");
    }
}

async function handleLogin() {
    const userId = document.getElementById("userId").value.trim();
    const password = document.getElementById("password").value;

    if (!userId || !password) {
        alert("아이디와 비밀번호를 입력하세요.");
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId, password })
        });

        if (response.ok) {
            const user = await response.json();
            alert(`${user.name}님, 환영합니다!`);
        } else {
            alert("로그인 실패: " + await response.text());
        }
    } catch (error) {
        console.error(error);
        alert("서버에 연결할 수 없습니다. 백엔드 실행 상태를 확인하세요.");
    }
}