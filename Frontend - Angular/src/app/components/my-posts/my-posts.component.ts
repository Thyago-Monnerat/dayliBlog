import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { ArticleType } from '../../../interfaces/articlePattern';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { DefaultCardComponent } from '../cards-content/default-card/default-card.component';
import { MyPostsCardComponent } from './my-posts-card/my-posts-card.component';

@Component({
	selector: 'app-my-posts',
	standalone: true,
	imports: [CommonModule, DefaultCardComponent, MyPostsCardComponent],
	templateUrl: './my-posts.component.html',
	styleUrl: './my-posts.component.css',
})
export class MyPostsComponent implements OnInit {
	myPosts: ArticleType[] = [];
	article: any;

	constructor(@Inject(PLATFORM_ID) private platformId: object) {}

	ngOnInit(): void {
		if (isPlatformBrowser(this.platformId)) {
			fetch('http://localhost:8080/user/getMyAccount', {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer ' + localStorage.getItem('token'),
				},
			})
				.then((res) => {
					return res.json();
				})
				.then((data) => {
					this.myPosts = data.posts;
				});
		}
	}
}
