import { Component, EventEmitter, Output, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Country } from '../../models/country.model';
import { City } from '../../models/city.model';
import { CountryService } from '../../services/country.service';
import { CityService } from '../../services/city.service';

/*
 * Implementar: HU-02 — Crear Ciudad
 */

@Component({
  selector: 'app-city-create',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './city-create.component.html'
})
export class CityCreateComponent implements OnInit {
  private countryService = inject(CountryService);
  private cityService = inject(CityService);

  @Output() cityCreated = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();


  public cityName: string = '';
  public selectedCountryId: number | null = null;
  public countries: Country[] = [];

  ngOnInit(): void {

    this.countryService.getCountries().subscribe({
      next: (data) => {
        this.countries = data;
      },
      error: (error) => {
        console.error('Error al cargar países:', error);
      }
    });
  }

  onSave(): void {

    if (!this.cityName || !this.selectedCountryId) {
      console.error('Faltan datos para crear la ciudad');
      return;
    }

  
    const newCity: Partial<City> = {
      name: this.cityName
    };

    
    this.cityService.createCity(this.selectedCountryId, newCity).subscribe({
      next: () => {
       
        this.cityCreated.emit();
     
        this.cityName = '';
        this.selectedCountryId = null;
      },
      error: (error) => {
        console.error('Error al crear ciudad:', error);
      }
    });
  }

  onCancel(): void {
   
    this.cancel.emit();
  }
}