import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ReadPageComponent } from './pages/read-page/read-page.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ProfilePageComponent } from './pages/profile-page/profile-page.component';
import { CreatePostComponent } from './pages/create-post/create-post.component';

export const routes: Routes = [
	{
		path: '',
		pathMatch: 'full',
		component: HomeComponent,
	},
	{
		path: 'create-post',
		pathMatch: 'full',
		component: CreatePostComponent,
	},
	{
		path: 'profile',
		pathMatch: 'full',
		component: ProfilePageComponent,
	},
	{
		path: 'login',
		component: LoginComponent,
	},
	{
		path: 'register',
		component: RegisterComponent,
	},
	{
		path: 'content',
		children: [
			{
				path: ':id',
				component: ReadPageComponent,
			},
		],
		component: ReadPageComponent,
	},
	{
		path: '**',
		component: NotFoundComponent,
	},
];
