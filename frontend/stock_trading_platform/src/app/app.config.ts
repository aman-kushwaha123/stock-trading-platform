import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHotToastConfig } from '@ngneat/hot-toast';

import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { tokenHttpInterceptor } from '../core/token-httpInterceptors';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors([tokenHttpInterceptor])),
    provideHotToastConfig({
      position: 'top-right',
      duration: 3000,
      dismissible: true,
      style: {
        width: '450px',
        maxWidth: '90vw',
        minHeight: '40px',
        padding: '16px 20px',
        fontSize: '15px'
      }
    })

  ]
};
