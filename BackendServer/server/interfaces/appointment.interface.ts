import { BaseInterface } from './base/base.interface';

export class AppointmentInterface extends BaseInterface {
  doctor: string;
  patient: string;
  service: string;
  time: string;
  date: string;
  note: string;
  is_expired?: boolean;
}
