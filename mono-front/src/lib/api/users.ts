const BASE_URL = "http://localhost:3000/users";

// ユーザー型（取得・更新用）
export interface User {
  id: number;
  name: string;
}

// 新規作成用（IDなし）
export interface CreateUser {
  name: string;
  email: string;
}

/**
 * ユーザー全件取得
 */
export async function getUsers(): Promise<User[]> {
  const res = await fetch(BASE_URL);
  return res.json();
}

/**
 * ユーザー詳細取得
 */
export async function getUser(id: number | string): Promise<User | null> {
  const numericId = Number(id);
  const res = await fetch(`${BASE_URL}/${numericId}`);
  return res.ok ? res.json() : null;
}

/**
 * ユーザー新規登録（IDは送らない！）
 */
export async function createUser(userData: CreateUser) {
  return fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData) // 👈 idなし
  });
}

/**
 * ユーザー更新
 */
export async function updateUser(
  id: number | string,
  userData: Omit<User, "id">
) {
  const numericId = Number(id);

  const payload = {
    ...userData,
    id: numericId
  };

  return fetch(`${BASE_URL}/${numericId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload)
  });
}

/**
 * ユーザー削除
 */
export async function deleteUser(id: number | string) {
  const numericId = Number(id);
  return fetch(`${BASE_URL}/${numericId}`, {
    method: "DELETE"
  });
}