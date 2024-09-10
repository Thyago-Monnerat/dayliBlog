import { ArticleType } from '../interfaces/articlePattern';

export async function fetchArticles(): Promise<ArticleType[]> {
	const data = await fetch('http://localhost:8080/api/v1/get', {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json',
		},
	});
	return await data.json();
}
