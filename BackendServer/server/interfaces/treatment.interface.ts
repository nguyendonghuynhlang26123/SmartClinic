import { BaseInterface } from './base/base.interface';

export class TreatmentInterface extends BaseInterface {
  doctor: string;
  patient: string;
  service: string;
  time: string;
  date: string;
  note: string;
  prescription: string;
}
