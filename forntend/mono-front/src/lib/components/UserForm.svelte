<script>
  export let user = { name: "" };
  export let onSubmit;
</script>

<input bind:value={user.name} placeholder="名前" />

<button on:click={() => onSubmit(user)}>
  保存
</button>