import { Component } from '@angular/core';
import { CityListComponent } from './components/city-list/city-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CityListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'WeatherAndes';
}
