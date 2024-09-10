import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
	selector: 'app-input',
	standalone: true,
	imports: [FormsModule],
	templateUrl: './input.component.html',
	styleUrl: './input.component.css',
})
export class InputComponent {
	@Output() valueChange = new EventEmitter<string>();
	value: string = '';

	emitValue() {
		this.valueChange.emit(this.value);
	}
}
