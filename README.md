# monocrea-task

## 起動方法

---

### ■ フロントのみで動かす（簡易確認）

**① json-server起動**

```bash
cd frontend
npx json-server db.json
```

**② フロント起動**

```bash
cd frontend
npm run dev
```

---

### ■ フロント + バックエンド

**① PostgreSQL起動（Docker）**

```bash
docker run -d --name postgres \
  -e POSTGRES_USER=mono \
  -e POSTGRES_PASSWORD=mono \
  -e POSTGRES_DB=mono \
  -p 5433:5432 postgres:15
```

**② バックエンド起動**

```bash
cd backend
mvn quarkus:dev
```

**③ フロント起動**

```bash
cd frontend
npm run dev
```

---

## API切り替え

`frontend/src/lib/api/users.ts`

用途に応じてどちらかを使用：

```ts
// フロントのみ（json-server）
const BASE_URL = "http://localhost:3000/users";

// バックエンド使用時
const BASE_URL = "http://localhost:8080/users";
```

---

以上
