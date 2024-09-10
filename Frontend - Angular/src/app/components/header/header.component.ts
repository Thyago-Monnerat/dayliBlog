import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DefaultButtonComponent } from '../default-button/default-button.component';
import { ProfileButtonComponent } from '../profile-button/profile-button.component';
import { UserAuthService } from '../../services/user-auth.service';

@Component({
	selector: 'app-header',
	standalone: true,
	imports: [
		CommonModule,
		RouterLink,
		DefaultButtonComponent,
		ProfileButtonComponent,
	],
	templateUrl: './header.component.html',
	styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
	logo: string = 'DailyBlog';
	authenticated: boolean = false;

	constructor(
		@Inject(PLATFORM_ID) private platformId: Object,
		private authService: UserAuthService
	) {}

	ngOnInit(): void {
		if (isPlatformBrowser(this.platformId)) {
			this.authenticated = this.authService.checkAuth();
		}
	}
}
