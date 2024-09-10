import { Component, OnInit } from '@angular/core';
import { ArticleType } from '../../../interfaces/articlePattern';
import { CommonModule } from '@angular/common';
import { DefaultCardComponent } from './default-card/default-card.component';
import { fetchArticles } from '../../../resources/articles';
import { jwtDecode } from 'jwt-decode';
import { ActivatedRoute, Router } from '@angular/router';
import { PagesSelectorComponent } from '../pages-selector/pages-selector.component';
import { FormsModule } from '@angular/forms';
import { InputComponent } from '../input/input.component';

@Component({
	selector: 'app-cards-content',
	standalone: true,
	imports: [
		CommonModule,
		DefaultCardComponent,
		PagesSelectorComponent,
		FormsModule,
		InputComponent,
	],
	templateUrl: './cards-content.component.html',
	styleUrl: './cards-content.component.css',
})
export class CardsContentComponent implements OnInit {
	articles: any[] = [];
	postsMatrix: any[] = [];
	numPerPage: number = 6;
	numPages: number = 0;
	currentPage: number = 0;
	filteredPosts: any[] = [];

	item: string = '';

	constructor(private router: Router, private route: ActivatedRoute) {}

	ngOnInit(): void {
		this.route.queryParams.subscribe((params) => {
			this.currentPage = params['page'] ? +params['page'] : 1;
		});

		fetchArticles().then((data) => {
			data.forEach((post) => {
				this.articles.push(jwtDecode(post.toString()));
			});

			for (let i = 0; i < this.articles.length; i += this.numPerPage) {
				this.postsMatrix.push(this.articles.slice(i, i + this.numPerPage));
				this.numPages++;
			}

			this.input();
		});
	}

	input() {
		if (!this.item || this.item.trim() === '') {
			this.filteredPosts = [...this.postsMatrix.flat()];
		} else {
			const term = this.item.trim().toLowerCase();
			this.filteredPosts = this.postsMatrix
				.flat()
				.filter(
					(post) => post.title && post.title.toLowerCase().includes(term)
				);
		}
	}

	updateValue(value: string) {
		console.log(123)
		this.item = value;
		this.input();
	}
}
