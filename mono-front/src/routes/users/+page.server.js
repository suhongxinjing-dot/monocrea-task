import { getUsers } from '$lib/api/users';

/** @type {import('./$types').PageServerLoad} */
export async function load() {
    const users = await getUsers();
    return {
        users: users || []
    };
}