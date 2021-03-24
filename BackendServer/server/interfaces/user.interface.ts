import { BaseInterface } from './base/base.interface';

export class UserInterface extends BaseInterface {
  phone: string;
  password: string;
  user_type: 'DOCTOR' | 'PATIENT';
  user_infor: string;
}
