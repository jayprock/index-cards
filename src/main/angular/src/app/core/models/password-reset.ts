
export interface PasswordReset {
    userId?: number;
    token?: string;
    oldPassword?: string;
    newPassword: string;
}