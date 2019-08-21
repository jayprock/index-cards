import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { CoreModule } from './core/core.module';
import { CreateModule } from './create/create.module';
import { HomeModule } from './home/home.module';
import { NgModule } from '@angular/core';
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
    CreateModule,
    RegisterModule,
    StudyGuideModule, // must be last
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
