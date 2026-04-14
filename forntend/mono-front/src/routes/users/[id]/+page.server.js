import { getUser } from '$lib/api/users';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
    // /users/new から来た場合
    if (params.id === 'new') {
        return { 
            user: { id: 0, name: '', email: '' } 
        };
    }
    
    // 既存データの読み込み
    const user = await getUser(params.id);
    return { user };
}