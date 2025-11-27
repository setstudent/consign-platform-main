const API_BASE = "http://localhost:8080";

async function apiRequest(path, options = {}) {
  const url = API_BASE + path;

  const defaultHeaders = {
    "Content-Type": "application/json",
  };

  const finalOptions = {
    method: "GET",
    credentials: "include",
    ...options,
    headers: {
      ...defaultHeaders,
      ...(options.headers || {}),
    },
  };

  const resp = await fetch(url, finalOptions);

  if (!resp.ok) {
    const text = await resp.text();
    throw new Error(`API ${resp.status}: ${text}`);
  }

  const contentType = resp.headers.get("Content-Type") || "";
  if (contentType.includes("application/json")) {
    return resp.json();
  }
  return resp.text();
}

function getCurrentUserId() {
  let id = localStorage.getItem("consign_user_id");
  if (!id) {
    id = "1";
    localStorage.setItem("consign_user_id", id);
  }
  return parseInt(id, 10);
}
