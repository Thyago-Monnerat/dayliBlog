import { Component, OnInit } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Inject, PLATFORM_ID } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { Router } from '@angular/router';

@Component({
	selector: 'app-not-found',
	standalone: true,
	imports: [HeaderComponent, FooterComponent],
	templateUrl: './not-found.component.html',
	styleUrls: ['./not-found.component.css'],
})
export class NotFoundComponent implements OnInit {
	timer: number = 10;

	constructor(
		@Inject(PLATFORM_ID) private platformId: Object,
		private route: Router
	) {}

	ngOnInit(): void {
		if (isPlatformBrowser(this.platformId)) {
			this.startCountdown();
		}
	}

	startCountdown() {
		if (this.timer > 0) {
			setTimeout(() => {
				this.timer--;
				this.startCountdown();
			}, 1000);
		} else {
			this.route.navigate(['/']);
		}
	}
}
