import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserAuthService } from '../../services/user-auth.service';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';

@Component({
	selector: 'app-register',
	standalone: true,
	imports: [FormsModule, HeaderComponent, FooterComponent],
	templateUrl: './register.component.html',
	styleUrl: './register.component.css',
})
export class RegisterComponent {
	username: string = '';
	password: string = '';

	constructor(private userService: UserAuthService) {}
	register() {
		this.userService.register(this.username, this.password);
	}
}
