import { basePath } from './customFetch.util';

export class AuthService {
  static getPassword = (): string | null => localStorage.getItem('password');

  static authorize = async (newPassword: string) => {
    const isPassValid = await this.validatePassword(newPassword);
    if (isPassValid) this.setPassword(newPassword);
  };

  static setPassword = (newPassword: string) => localStorage.setItem('password', newPassword);

  static validatePassword = async (password: string) => fetch(`${basePath}/Authorize/${password}`).then((data) => data.ok);
}
