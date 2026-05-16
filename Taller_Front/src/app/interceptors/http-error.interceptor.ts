import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';

/*
 * Implementar: HU-04 — Interceptor de Errores HTTP
 */

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const toastr = inject(ToastrService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      
      if (req.url.includes('weatherapi.com')) {
        toastr.error('Error al conectar con WeatherAPI. Intente más tarde.', 'Error de Clima');
      } 
      else {
        const errorMessage = error.error?.message || error.message || 'Error desconocido';
        toastr.error(`Error ${error.status}: ${errorMessage}`, 'Error del Servidor');
      }

      return throwError(() => error);
    })
  );
};