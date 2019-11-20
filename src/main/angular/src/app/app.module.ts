import { ErrorHandler, NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CoreModule } from './core/core.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { GlobalErrorHandler } from './core/interceptors/global-error-handler';
import { HomeModule } from './home/home.module';
import { LoginModule } from './login/login.module';
import { ManageModule } from './manage/manage.module';
import { RegisterModule } from './register/register.module';
import { SearchModule } from './search/search.module';
import { StudyGuideModule } from './study-guide/study-guide.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,    
    CoreModule,
    HomeModule,
    SearchModule,
    ManageModule,
    RegisterModule,
    LoginModule,
    DashboardModule, 
    StudyGuideModule, // must be last based on routing path
  ],
  providers: [
    { provide: ErrorHandler, useClass: GlobalErrorHandler }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
