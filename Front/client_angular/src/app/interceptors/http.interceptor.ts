import { HttpInterceptorFn } from '@angular/common/http';
import { count, delay, retry, shareReplay } from 'rxjs';

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    shareReplay(), 
  );
};
