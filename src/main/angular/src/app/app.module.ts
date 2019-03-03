import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { HomeModule } from './home/home.module';
import { NgModule } from '@angular/core';
import { SearchModule } from './search/search.module';
import { CreateModule } from './create/create.module';
import { StudyModule } from './study/study.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HomeModule,
    SearchModule,
    CreateModule,
    StudyModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
