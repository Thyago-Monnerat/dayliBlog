import { Component, Input } from '@angular/core';

import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
	selector: 'app-pages-selector',
	standalone: true,
	imports: [CommonModule],
	templateUrl: './pages-selector.component.html',
	styleUrl: './pages-selector.component.css',
})
export class PagesSelectorComponent {
	@Input()
	currentPage: number = 0;
	@Input()
	totalPages: number = 0;

	constructor(private router: Router, private route: ActivatedRoute) {}

	changePage(page: number): void {
		if (page <= 0) {
			page = 1;
		}

		if (page > this.totalPages) {
			page = this.totalPages;
		}

		this.currentPage = page;

		this.router.navigate([], {
			relativeTo: this.route,
			queryParams: { page: this.currentPage },
			queryParamsHandling: 'merge',
		});
	}
}
