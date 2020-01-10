import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, ViewChild } from '@angular/core';

@Component({
  selector: 'idx-recaptcha',
  templateUrl: './recaptcha.component.html',
  styleUrls: ['./recaptcha.component.css']
})
export class RecaptchaComponent implements OnInit, OnChanges {

  @Input() hasError = false;
  @Output() ready = new EventEmitter<string>();

  @ViewChild('recaptcha') recaptchaElement: ElementRef;

  private responseCaptured = false;

  constructor() { }

  ngOnInit() {
    this.addRecaptchaScript();
  }

  ngOnChanges() {
    if (this.hasError && this.responseCaptured) {
      this.responseCaptured = false;
      window['grecaptcha'].reset();
    }
  }

  addRecaptchaScript() {
 
    window['grecaptchaCallback'] = () => {
      this.renderReCaptcha();
    }
   
    (function(d, s, id, obj){
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) { obj.renderReCaptcha(); return;}
      js = d.createElement(s); js.id = id;
      js.src = "https://www.google.com/recaptcha/api.js?onload=grecaptchaCallback&amp;render=explicit";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'recaptcha-jssdk', this));
   
  }

  renderReCaptcha() {
    window['grecaptcha'].render(this.recaptchaElement.nativeElement, {
      'sitekey' : '6Le2_8YUAAAAAJQm7w7UBt5ZmuPVMj8MUUIe8pf2',
      'callback': (response) => {
          this.responseCaptured = true;
          this.ready.emit(response);
      }
    });
  }
}
