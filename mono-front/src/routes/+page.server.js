// src/routes/+page.server.js
import { redirect } from '@sveltejs/kit';

export function load() {
    // ルート (/) に来たら、即座に /users へリダイレクト
    throw redirect(302, '/users');
}