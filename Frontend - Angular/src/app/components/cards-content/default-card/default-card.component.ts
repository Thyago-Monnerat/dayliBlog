import { Component, Inject, Input, OnInit, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Card } from '../../../../interfaces/cardPattern';
import { jwtDecode } from 'jwt-decode';
import { PostService } from '../../../services/post.service';

@Component({
	selector: 'app-default-card',
	standalone: true,
	imports: [CommonModule, RouterLink],
	templateUrl: './default-card.component.html',
	styleUrl: './default-card.component.css',
})
export class DefaultCardComponent implements Card, OnInit {
	@Input()
	id: string = '';
	@Input()
	owner: string = '';
	@Input()
	img: string = '';
	@Input()
	title: string = '';
	@Input()
	content: string = '';
	@Input()
	date: string = '';

	isOwner: boolean = false;

	constructor(
		@Inject(PLATFORM_ID) private platformId: Object,
		private postService: PostService
	) {}

	ngOnInit(): void {
		if (isPlatformBrowser(this.platformId)) {
			const token = localStorage.getItem('token');
			if (token) {
				const userId: any = jwtDecode(token);
				if (userId.name == this.owner) {
					this.isOwner = true;
				}
			}
		}
	}

	deletePost() {
		this.postService.deletePost(this.id);
	}
}
