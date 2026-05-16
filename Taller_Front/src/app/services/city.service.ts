import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { City } from '../models/city.model';

@Injectable({ providedIn: 'root' })
export class CityService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  getCities(): Observable<City[]> {
    return this.http.get<City[]>(`${this.apiUrl}/cities`);
  }

  createCity(countryId: number, city: Partial<City>): Observable<City> {
    return this.http.post<City>(`${this.apiUrl}/countries/${countryId}/cities`, city);
  }
}
