import { CommonModule } from '@angular/common';
import { CoreRoutingModule } from './core-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { httpInterceptorProviders } from './interceptors/index';

@NgModule({
  declarations: [NotFoundComponent],
  imports: [
    CommonModule,
    CoreRoutingModule,
    HttpClientModule
  ],
  providers: [
    httpInterceptorProviders
  ]
})
export class CoreModule { }
