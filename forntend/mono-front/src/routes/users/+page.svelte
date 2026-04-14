<script>
  import { onMount } from "svelte";
  import { getUsers } from "$lib/api/users";

  let { data } = $props();

  // 1. 状態管理
  let users = $state(data.users || []);
  let inputId = $state("");
  let inputName = $state("");
  let filterId = $state("");
  let filterName = $state("");
  let sortKey = $state("id");
  let currentPage = $state(1);
  const pageSize = 5;

  function executeSearch() {
    filterId = inputId;
    filterName = inputName;
    currentPage = 1; 
  }

  // 2. フィルタ・ソート（[...users] でコピーを作って破壊的変更を避ける）
  let processedUsers = $derived(
    [...users]
      .filter(u => String(u.id).includes(filterId))
      .filter(u => u.name.toLowerCase().includes(filterName.toLowerCase()))
      .sort((a, b) => {
        const aVal = a[sortKey];
        const bVal = b[sortKey];
        if (typeof aVal === 'number' && typeof bVal === 'number') return aVal - bVal;
        return String(aVal).localeCompare(String(bVal));
      })
  );

  let pagedUsers = $derived(
    processedUsers.slice((currentPage - 1) * pageSize, currentPage * pageSize)
  );

  let totalPages = $derived(Math.ceil(processedUsers.length / pageSize) || 1);
</script>

<h1>ユーザー検索システム</h1>

<div class="controls">
  <div class="search-group">
    <input bind:value={inputId} placeholder="IDで検索" onkeydown={(e) => e.key === 'Enter' && executeSearch()} />
    <input bind:value={inputName} placeholder="名前で検索" onkeydown={(e) => e.key === 'Enter' && executeSearch()} />
    <button type="button" onclick={executeSearch}>検索</button>
  </div>
  <div class="sort-group">
    <button type="button" class:active={sortKey === 'id'} onclick={() => sortKey = 'id'}>ID順</button>
    <button type="button" class:active={sortKey === 'name'} onclick={() => sortKey = 'name'}>名前順</button>
  </div>
  <a href="/users/new" class="btn-new">新規登録へ</a>
</div>

<table>
  <thead>
    <tr><th>ID</th><th>名前</th><th>詳細</th></tr>
  </thead>
  <tbody>
    {#each pagedUsers as user}
      <tr>
        <td>{user.id}</td>
        <td>{user.name}</td>
        <td><a href="/users/{user.id}">詳細表示</a></td>
      </tr>
    {:else}
      <tr><td colspan="3" style="text-align: center;">一致なし</td></tr>
    {/each}
  </tbody>
</table>

<div class="pagination">
  <button type="button" disabled={currentPage === 1} onclick={() => currentPage--}>前へ</button>
  <span>{currentPage} / {totalPages} ページ</span>
  <button type="button" disabled={currentPage >= totalPages} onclick={() => currentPage++}>次へ</button>
</div>

<style>
  .controls { display: flex; flex-direction: column; gap: 10px; margin-bottom: 20px; }
  .search-group, .sort-group { display: flex; gap: 10px; }
  table { width: 100%; border-collapse: collapse; }
  th, td { border: 1px solid #ddd; padding: 12px; }
  button.active { background: #007bff; color: white; border: none; }
  .btn-new { display: inline-block; padding: 8px; background: #28a745; color: white; text-decoration: none; border-radius: 4px; width: fit-content; }
</style>