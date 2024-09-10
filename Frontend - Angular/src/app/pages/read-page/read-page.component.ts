import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { fetchArticles } from '../../../resources/articles';
import { jwtDecode } from 'jwt-decode';
import { CommonModule, Location } from '@angular/common';

@Component({
	selector: 'app-read-page',
	standalone: true,
	imports: [RouterLink, HeaderComponent, FooterComponent, CommonModule],
	templateUrl: './read-page.component.html',
	styleUrl: './read-page.component.css',
})
export class ReadPageComponent implements OnInit {
	id: string = '';
	posts: any[] = [];
	mainPost: any = '';

	constructor(private route: ActivatedRoute, private location: Location) {}

	ngOnInit(): void {
		this.route.firstChild?.params.subscribe((param) => {
			this.id = param['id'];
		});

		fetchArticles().then((posts) => {
			posts.forEach((post) => {
				this.posts.push(jwtDecode(post.toString()));
			});

			this.mainPost = this.posts.find((post) => post.sub === this.id);
		});
	}

	return() {
		this.location.back();
	}
}
