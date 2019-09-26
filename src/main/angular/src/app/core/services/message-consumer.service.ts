import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class MessageConsumerService {

    private messages = new Map();

    postMessage(key: string, message: string) {
        this.messages.set(key, message);
    }

    consumeMessage(key: string): string {
        let message = this.messages.get(key);
        this.messages.delete(key);
        return message;
    }

}