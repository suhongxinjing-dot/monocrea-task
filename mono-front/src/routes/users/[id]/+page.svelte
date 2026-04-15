<script lang="ts">
  import { createUser, updateUser, deleteUser } from "$lib/api/users";
  import { goto } from "$app/navigation";

  let { data } = $props();

  const isNew = $derived(!data.user?.id);

  // 2. 状態管理（初期値を「数値」で固定。これが文字化け防止の鍵）
  // data.user.id が文字列で来ても Number() で数値化して保持する
  let id = $state(Number(data.user?.id ?? 0));
  let name = $state(data.user?.name || "");
  let email = $state(data.user?.email || "");

  // 新規登録の実行
  async function handleRegister() {
    const numericId = Number(id); // 👈 ここで確実に数値化
    if (!numericId || numericId === 0) return alert("正しいIDを入力してください");
    
    // API送信用にDTOを構築
    const userData = { id: numericId, name, email };
    
    const res = await createUser(userData);
    if (res.ok) {
      goto("/users");
    } else {
      alert("登録失敗：IDが既に使われている可能性があります");
    }
  }

  // 更新の実行
  async function handleUpdate() {
    const numericId = Number(id); // 👈 ここで確実に数値化
    const res = await updateUser(numericId, { name, email });
    
    if (res.ok) {
      goto("/users");
    } else {
      alert("更新に失敗しました");
    }
  }

  // 削除の実行
  async function handleDelete() {
    if (!confirm("このユーザーを削除してもよろしいですか？")) return;
    
    const numericId = Number(id); // 👈 ここで確実に数値化
    const res = await deleteUser(numericId);
    if (res.ok) {
      goto("/users");
    } else {
      alert("削除に失敗しました");
    }
  }
</script>

<h1>{isNew ? "ユーザー新規登録" : "ユーザー詳細・編集"}</h1>

<div class="form-container">
  <div class="field">
    <label for="uid">ユーザーID（数値）</label>
    <input 
      id="uid" 
      type="number" 
      bind:value={id} 
      disabled={!isNew} 
      placeholder="101"
    />
    {#if !isNew}<small>※IDは変更できません</small>{/if}
  </div>

  <div class="field">
    <label for="uname">名前</label>
    <input id="uname" type="text" bind:value={name} />
  </div>

  <div class="button-group">
    {#if isNew}
      <button type="button" onclick={handleRegister} class="btn-primary">新規登録して保存</button>
    {:else}
      <button type="button" onclick={handleUpdate} class="btn-success">変更を保存</button>
      <button type="button" onclick={handleDelete} class="btn-danger">このユーザーを削除</button>
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
  .field small { color: #666; font-size: 0.8em; }
  input { width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ddd; border-radius: 4px; }
  input:disabled { background: #eee; cursor: not-allowed; }
  
  .button-group { display: flex; gap: 10px; margin-top: 20px; }
  button { padding: 12px; cursor: pointer; flex: 1; border: none; color: white; font-weight: bold; border-radius: 4px; }
  
  .btn-primary { background: #007bff; }
  .btn-success { background: #28a745; }
  .btn-danger { background: #dc3545; }
  
  .footer { margin-top: 20px; }
</style>