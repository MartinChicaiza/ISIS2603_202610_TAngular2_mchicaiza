import { Component, Input, OnChanges, SimpleChanges, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { City } from '../../models/city.model';
import { WeatherRecord } from '../../models/weather-record.model';
import { WeatherRecordService } from '../../services/weather-record.service';
import { WeatherDetail } from '../../models/weather.model';
import { WeatherService } from '../../services/weather.service';

/*
 * Implementar:
 * HU-03 — Detalle de Ciudad con Clima (Ver TALLER.md Parte B)
 * HU-04 — Historial de Registros de Clima (Ver TALLER.md Parte D)
 */

@Component({
  selector: 'app-city-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './city-detail.component.html'
})
export class CityDetailComponent implements OnChanges {
  private weatherRecordService = inject(WeatherRecordService);
  private weatherService = inject(WeatherService);

  @Input() city!: City;

  public weatherRecords: WeatherRecord[] = [];
  
  // HU-03: Propiedades para el clima actual
  public weatherDetail: WeatherDetail | null = null;
  public loading: boolean = false;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['city'] && this.city) {
      // Cargar historial de registros
      this.weatherRecordService.getRecords(this.city.id)
        .subscribe(records => this.weatherRecords = records);

      // HU-03: Obtener el clima actual de la ciudad
      this.loading = true;
      this.weatherDetail = null;
      
      this.weatherService.getWeather(this.city.name).subscribe({
        next: (data) => {
          this.weatherDetail = data;
          this.loading = false;
        },
        error: (error) => {
          console.error('Error al obtener clima:', error);
          this.weatherDetail = null;
          this.loading = false;
        }
      });
    }
  }

  saveWeather(): void {
    // HU-04: Guardar un nuevo registro de clima
    if (!this.weatherDetail) {
      console.error('No hay datos de clima para guardar');
      return;
    }

    const newRecord = {
      tempC: this.weatherDetail.temp_c,
      condition: this.weatherDetail.condition,
      humidity: this.weatherDetail.humidity
    };

    this.weatherRecordService.saveRecord(this.city.id, newRecord).subscribe({
      next: () => {
        // Al completar, recarga la lista
        this.weatherRecordService.getRecords(this.city.id)
          .subscribe(records => this.weatherRecords = records);
      },
      error: (error) => {
        console.error('Error al guardar registro:', error);
      }
    });
  }
}