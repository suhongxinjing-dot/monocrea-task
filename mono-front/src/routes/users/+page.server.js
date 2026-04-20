import { getUsers } from '$lib/api/users';

export async function load() {
    const users = await getUsers();
    return {
        users: users || []
    };
}