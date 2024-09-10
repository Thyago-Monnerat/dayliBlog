import { Component } from '@angular/core';
import { ProfileComponent } from '../../components/profile/profile.component';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';

@Component({
	selector: 'app-profile-page',
	standalone: true,
	imports: [ProfileComponent, HeaderComponent, FooterComponent],
	templateUrl: './profile-page.component.html',
	styleUrl: './profile-page.component.css',
})
export class ProfilePageComponent {
	
}
