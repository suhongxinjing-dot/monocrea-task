<script lang="ts">
  import { createUser, updateUser, deleteUser } from "$lib/api/users";
  import { goto } from "$app/navigation";

  let { data } = $props();

  const isNew = $derived(!data.user?.id);

  // 名前だけ管理（ID state廃止）
  let name = $state(data.user?.name || "");

  // IDは常にデータから参照（state禁止）
  const userId = () => data.user?.id;

  // 新規登録
  async function handleRegister() {

    if (!name) return alert("名前を入力してください");

    const res = await createUser({ name });

    if (res.ok) {
      await goto("/users");
    } else {
      const text = await res.text();
      console.error(text);
      alert("登録失敗");
    }
  }

 // 更新
async function handleUpdate() {

  try {
    await updateUser(userId(), { name });
    await goto("/users");
  } catch (e) {
    console.error(e);
    alert("更新失敗");
  }
}


  // 削除
async function handleDelete() {
  if (!confirm("削除しますか？")) return;

  try {
    await deleteUser(userId());
    await goto("/users");
  } catch (e) {
    console.error(e);
    alert("削除失敗");
  }
}
</script>

<h1>{isNew ? "ユーザー新規登録" : "ユーザー詳細・編集"}</h1>

<div class="form-container">
  <div class="field">
    <label for="uname">名前</label>
    <input id="uname" type="text" bind:value={name} />
  </div>

  <div class="button-group">
    {#if isNew}
      <button type="button" on:click={handleRegister} class="btn-primary">
        新規登録
      </button>
    {:else}
      <button type="button" on:click={handleUpdate} class="btn-success">
        更新
      </button>
      <button type="button" on:click={handleDelete} class="btn-danger">
        削除
      </button>
    {/if}
  </div>
</div>

<div class="footer">
  <a href="/users">← 一覧に戻る</a>
</div>

<style>
  .form-container { border: 1px solid #ccc; padding: 20px; background: #fff; max-width: 450px; border-radius: 8px; }
  .field { margin-bottom: 15px; }
  .field label { display: block; font-weight: bold; margin-bottom: 5px; }
  input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }

  .button-group { display: flex; gap: 10px; margin-top: 20px; }
  button { padding: 12px; cursor: pointer; flex: 1; border: none; color: white; font-weight: bold; border-radius: 4px; }

  .btn-primary { background: #007bff; }
  .btn-success { background: #28a745; }
  .btn-danger { background: #dc3545; }
</style>