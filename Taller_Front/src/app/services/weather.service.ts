import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { WeatherDetail } from '../models/weather.model';

@Injectable({
  providedIn: 'root',
})
export class WeatherService {
  private API_KEY = environment.weatherApiKey;
  private BASE_URL = 'https://api.weatherapi.com/v1';

  constructor(private http: HttpClient) { }

  getWeather(cityName: string): Observable<WeatherDetail> {
    // Construir la URL de WeatherAPI
    const url = `${this.BASE_URL}/current.json?key=${this.API_KEY}&q=${cityName}`;
    
    // Hacer la petición HTTP y transformar la respuesta
    return this.http.get<any>(url).pipe(
      map(res => ({
        temp_c: res.current.temp_c,
        condition: res.current.condition.text,
        humidity: res.current.humidity
      }))
    );
  }
}