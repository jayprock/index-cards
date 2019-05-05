import { CommonModule } from '@angular/common';
import { CoreRoutingModule } from './core-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { SharedModule } from '../shared/shared.module';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { httpInterceptorProviders } from './interceptors/index';

@NgModule({
  declarations: [
    NotFoundComponent, 
    ToolbarComponent
  ],
  imports: [
    CommonModule,
    CoreRoutingModule,
    HttpClientModule,
    SharedModule
  ],
  exports: [
    ToolbarComponent
  ],
  providers: [
    httpInterceptorProviders
  ]
})
export class CoreModule { }
