import { Component, inject, OnInit } from '@angular/core';

import { City } from '../../models/city.model';
import { CityService } from '../../services/city.service';
import { CityCreateComponent } from '../city-create/city-create.component';
import { CityDetailComponent } from '../city-detail/city-detail.component';

@Component({
  selector: 'app-city-list',
  standalone: true,
  imports: [CityCreateComponent, CityDetailComponent],
  templateUrl: './city-list.component.html'
})
export class CityListComponent implements OnInit {
  private cityService = inject(CityService);

  cities: City[] = [];
  selectedCity: City | null = null;
  showCreateForm = false;

  ngOnInit(): void {
    this.loadCities();
  }

  loadCities(): void {
    this.cityService.getCities().subscribe(cities => this.cities = cities);
  }

  onSelectCity(city: City): void {
    this.selectedCity = city;
    this.showCreateForm = false;
  }

  onCityCreated(): void {
    this.loadCities();
    this.showCreateForm = false;
  }

  toggleCreateForm(): void {
    this.showCreateForm = !this.showCreateForm;
  }
}
