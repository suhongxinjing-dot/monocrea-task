import { redirect } from '@sveltejs/kit';

export function load() {
    // throw redirect(ステータスコード, URL)
    throw redirect(302, '/users');
}