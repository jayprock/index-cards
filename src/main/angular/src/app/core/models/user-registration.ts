import { User } from './user';

export interface UserRegistration {
    user: User;
    recaptchaResponse: string;
}