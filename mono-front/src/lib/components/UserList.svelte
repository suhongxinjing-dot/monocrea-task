<script>
  export let users = [];
</script>

{#each users as user}
  <div>
    <a href={`/users/${user.id}`}>
      {user.name}
    </a>
  </div>
{/each}