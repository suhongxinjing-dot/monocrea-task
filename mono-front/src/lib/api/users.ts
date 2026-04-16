//const BASE_URL = "http://localhost:3000/users";
const BASE_URL = "http://localhost:8080/users";

export interface User {
  id: number;
  name: string;
}

export interface CreateUser {
  name: string;
}

/**
 * 全件取得
 */
export async function getUsers(): Promise<User[]> {
  const res = await fetch(BASE_URL);

  if (!res.ok) throw new Error(await res.text());

  return res.json();
}

/**
 * 詳細取得
 */
export async function getUser(id: number | string): Promise<User | null> {
  const res = await fetch(`${BASE_URL}/${Number(id)}`);

  if (!res.ok) return null;

  return res.json();
}

/**
 * 新規作成
 */
export async function createUser(userData: CreateUser): Promise<Response> {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData)
  });

  if (!res.ok) {
    throw new Error(await res.text());
  }

  return res;
}

/**
 * 更新
 */
export async function updateUser(
  id: number | string,
  userData: CreateUser
): Promise<Response> {
  const res = await fetch(`${BASE_URL}/${Number(id)}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData)
  });

  if (!res.ok) {
    throw new Error(await res.text());
  }

  return res;
}

/**
 * 削除
 */
export async function deleteUser(id: number | string): Promise<Response> {
  const res = await fetch(`${BASE_URL}/${Number(id)}`, {
    method: "DELETE"
  });

  if (!res.ok) {
    throw new Error(await res.text());
  }

  return res;
}