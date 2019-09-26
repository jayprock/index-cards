import { Component, OnDestroy, OnInit } from '@angular/core';

import { MESSAGE_KEYS } from '../../core/services/message-keys';
import { MessageConsumerService } from '../../core/services/message-consumer.service';

@Component({
  selector: 'idx-password-reset-email',
  templateUrl: './password-reset-email.component.html',
  styleUrls: ['./password-reset-email.component.css']
})
export class PasswordResetEmailComponent implements OnInit {

  email: string;

  constructor(
    private messageService: MessageConsumerService
  ) { }

  ngOnInit() {
    this.email = this.messageService.consumeMessage(MESSAGE_KEYS.email);
  }

}
