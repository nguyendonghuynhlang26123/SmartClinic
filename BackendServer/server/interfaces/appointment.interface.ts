import { BaseInterface } from "./base/base.interface";

export class AppointmentInterface extends BaseInterface {
  doctor: string;
  patient: string;
  time: number;
  is_expired: boolean;
}
