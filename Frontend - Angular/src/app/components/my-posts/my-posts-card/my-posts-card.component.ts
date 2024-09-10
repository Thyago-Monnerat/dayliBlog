import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { PostService } from '../../../services/post.service';

@Component({
	selector: 'app-my-posts-card',
	standalone: true,
	imports: [CommonModule],
	templateUrl: './my-posts-card.component.html',
	styleUrl: './my-posts-card.component.css',
})
export class MyPostsCardComponent {
	@Input()
	id: string = '';
	@Input()
	title: string = '';
	@Input()
	content: string = '';
	@Input()
	date: string = '';

	constructor(private postService: PostService) {}

	deletePost() {
		this.postService.deletePost(this.id);
	}
}
