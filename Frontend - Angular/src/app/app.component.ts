import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './components/header/header.component';
import { UserAuthService } from './services/user-auth.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
	selector: 'app-root',
	standalone: true,
	imports: [RouterOutlet, HeaderComponent],
	templateUrl: './app.component.html',
	styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
	title = 'personal-blog';

	constructor(
		private authService: UserAuthService,
		@Inject(PLATFORM_ID) private platformId: Object
	) {}

	ngOnInit(): void {
		let auth: boolean = this.authService.checkAuth();
		if (!auth && isPlatformBrowser(this.platformId)) {
			localStorage.clear();
		}
	}
}
