import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { InputComponent } from '../../components/input/input.component';
import { PagesSelectorComponent } from '../../components/pages-selector/pages-selector.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CardsContentComponent } from '../../components/cards-content/cards-content.component';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

@Component({
	selector: 'app-home',
	standalone: true,
	imports: [
		HeaderComponent,
		InputComponent,
		PagesSelectorComponent,
		FooterComponent,
		CardsContentComponent,
	],
	templateUrl: './home.component.html',
	styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
	constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

	ngOnInit(): void {
		if (isPlatformBrowser(this.platformId)) {
			window.scrollTo(0, 0);
		}
	}
}
