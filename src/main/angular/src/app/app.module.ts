import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { CoreModule } from './core/core.module';
import { CreateModule } from './create/create.module';
import { HomeModule } from './home/home.module';
import { NgModule } from '@angular/core';
import { SearchModule } from './search/search.module';
import { StudyGuideModule } from './study-guide/study-guide.module';

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
    StudyGuideModule,
    CoreModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
