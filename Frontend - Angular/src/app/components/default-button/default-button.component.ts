import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
	selector: 'app-default-button',
	standalone: true,
	imports: [CommonModule],
	templateUrl: './default-button.component.html',
	styleUrl: './default-button.component.css',
})
export class DefaultButtonComponent {
	@Input()
	content: string = '';
	@Input()
	type: string = '';

	constructor() {}
}
